package com.weicoder.nosql.kafka;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.weicoder.common.concurrent.ScheduledUtil;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.json.JsonEngine;
import com.weicoder.nosql.kafka.annotation.Consumer;
import com.weicoder.nosql.kafka.annotation.Topic;
import com.weicoder.nosql.kafka.consumer.Record;
import com.weicoder.nosql.kafka.factory.KafkaFactory;
import com.weicoder.nosql.params.KafkaParams;

/**
 * kafka生成器
 * @author WD
 */
public final class Kafkas {
	// 日志
	private final static Log																LOG				= LogFactory
			.getLog(Kafkas.class);
	// 保存Topic对应对象
	private final static Map<String, Object>												CONSUMERS		= Maps
			.newMap();
	// 保存Topic对应方法
	private final static Map<String, Method>												METHODS			= Maps
			.newMap();
	// 保存kafka消费
	private final static Map<String, KafkaConsumer<byte[], byte[]>>							KAFKA_CONSUMERS	= Maps
			.newMap();
	// 保存kafka对应消费的topic
	private final static Map<String, List<String>>											TOPICS			= Maps
			.newMap();
	// 保存Topic队列
	private final static Map<String, Map<String, Queue<ConsumerRecord<byte[], byte[]>>>>	TOPIC_RECORDS	= Maps
			.newConcurrentMap();

	/**
	 * 初始化消费者
	 */
	public static void consumers() {
		// 获得所有kafka消费者
		List<Class<Consumer>> consumers = ClassUtil
				.getAnnotationClass(CommonParams.getPackages("kafka"), Consumer.class);
		if (EmptyUtil.isNotEmpty(consumers)) {
			// 循环处理kafka类
			for (Class<Consumer> c : consumers) {
				// 执行对象
				Object consumer = BeanUtil.newInstance(c);
				String name = consumer.getClass().getAnnotation(Consumer.class).value();
				// 消费者队列
				Map<String, Queue<ConsumerRecord<byte[], byte[]>>> map = TOPIC_RECORDS.get(name);
				// 如果KafkaConsumer列表里没有相对应的消费者 创建
				if (!KAFKA_CONSUMERS.containsKey(name)) {
					KAFKA_CONSUMERS.put(name, KafkaFactory.getConsumer(name));
					TOPIC_RECORDS.put(name, map = Maps.newConcurrentMap());
				}
				// 获得topic列表
				List<String> topics = Maps.getList(TOPICS, name, String.class);
				// 处理所有方法
				for (Method m : c.getDeclaredMethods()) {
					// 方法有执行时间注解
					Topic topic = m.getAnnotation(Topic.class);
					if (topic != null) {
						String val = topic.value();
						METHODS.put(val, m);
						CONSUMERS.put(val, consumer);
						topics.add(val);
						map.put(val, new ConcurrentLinkedQueue<>());// LinkedBlockingQueue
						LOG.info("add kafka consumer={} topic={}", c.getSimpleName(), val);
					}
				}
			}
			LOG.info("add kafka Consumers size={}", consumers.size());
			// 订阅相关消费数据
			for (String key : TOPICS.keySet()) {
				List<String> topics = TOPICS.get(key);
				KAFKA_CONSUMERS.get(key).subscribe(topics);
				LOG.info("Kafkas init Consumer={} subscribe topic={}", key, topics);
			}
			// 读取topic定时
			ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
			// 启动定时读取kafka消息
			ses.scheduleWithFixedDelay(() -> {
				KAFKA_CONSUMERS.forEach((name, consumer) -> {
					// 线程池id
					long tid = Thread.currentThread().getId();
					// 日志使用
					long time = System.currentTimeMillis();
					int n = 0;
					// 根据name获得kafka消费者列表
					Map<String, Queue<ConsumerRecord<byte[], byte[]>>> map = TOPIC_RECORDS
							.get(name);
					// 获得消费数据
					for (ConsumerRecord<byte[], byte[]> record : consumer
							.poll(Duration.ofSeconds(1))) {// .poll(1000)
						// 获得消费对象类和方法
						LOG.debug("kafka read consumer thread={} record={}", tid, record);
						map.get(record.topic()).add(record);
						n++;
					}
					// 数量不为空
					if (n > 0) {
						LOG.info("kafka read consumer end name={} size={} time={} thread={}", name,
								n, System.currentTimeMillis() - time, tid);
					}
				});
			}, 0L, 100L, TimeUnit.MICROSECONDS);
			// 消费队列
			TOPIC_RECORDS.values().forEach(map -> {
				map.values().forEach((records) -> {
					ScheduledUtil.delay(KafkaParams.PREFIX, () -> {
						// 线程池id
						long tid = Thread.currentThread().getId();
						// 日志使用
						long time = System.currentTimeMillis();
						int n = 0;
						String topic = null;
						long offset = 0;
						// 处理队列信息
						ConsumerRecord<byte[], byte[]> record = null;
						while ((record = records.poll()) != null) {
							topic = record.topic();
							offset = record.offset();
							Object obj = CONSUMERS.get(topic);
							Method method = METHODS.get(topic);
							// 获得所有参数
							Parameter[] params = method.getParameters();
							Object[] objs = null;
							if (EmptyUtil.isEmpty(params)) {
								// 参数为空直接执行方法
								BeanUtil.invoke(obj, method);
							} else {
								// 参数
								objs = new Object[params.length];
								// 有参数 现在只支持 1-2位的参数，1个参数表示value,2个参数表示key,value
								Parameter param = params[0];
								Class<?> t = param.getType();
								if (params.length == 1) {
									if (ConsumerRecord.class.equals(t)) {
										objs[0] = record;
									} else if (Record.class.equals(t)) {
										Type type = param.getParameterizedType();
										Class<?>[] gc = ClassUtil.getGenericClass(type);
										objs[0] = new Record<>(record.topic(),
												toParam(record.key(), gc[0]),
												toParam(record.value(), gc[1]), record.offset(),
												record.timestamp());
									} else {
										objs[0] = toParam(record.value(), t);
									}
								} else {
									objs[0] = toParam(record.key(), t);
									objs[1] = toParam(record.value(), params[1].getType());
								}
								// 执行方法
								BeanUtil.invoke(obj, method, objs);
							}
							LOG.debug(
									"kafka consumer topic={} offset={} method={} args={} params={} thread={}",
									topic, offset, method.getName(), objs, params, tid);
							n++;
						}
						// 数量不为空
						if (n > 0) {
							LOG.info(
									"kafka consumer end topic={} offset={} size={} time={} thread={}",
									topic, offset, n, System.currentTimeMillis() - time, tid);
						}
					}, 100L);
				});
			});
		}

	}

	/**
	 * 实例化一个消费数据
	 * @param topic topic
	 * @param value value
	 * @return ProducerRecord
	 */
	public static ProducerRecord<byte[], byte[]> newRecord(String topic, Object value) {
		return new ProducerRecord<byte[], byte[]>(topic, toBytes(value));
	}

	/**
	 * 实例化一个消费数据
	 * @param topic topic
	 * @param key key
	 * @param value value
	 * @return ProducerRecord
	 */
	public static ProducerRecord<byte[], byte[]> newRecord(String topic, Object key, Object value) {
		return new ProducerRecord<byte[], byte[]>(topic, toBytes(key), toBytes(value));
	}

	/**
	 * 转换成参数
	 * @param b 字节数组
	 * @param c 类型
	 * @return 参数
	 */
	private static Object toParam(byte[] b, Class<?> c) {
		// 字符串
		if (String.class.equals(c))
			return StringUtil.toString(b);
		// 普通对象
		if (Object.class.equals(c))
			return b;
		// Map
		if (Map.class.equals(c))
			return JsonEngine.toMap(StringUtil.toString(b));
		// List
		if (List.class.equals(c))
			return JsonEngine.toList(StringUtil.toString(b));
		// 序列化
		return Bytes.to(b, c);
	}

	/**
	 * 序列号对象为自己数组
	 * @param obj 对象
	 * @return 自己数组
	 */
	private static byte[] toBytes(Object obj) {
		return obj instanceof String ? Conversion.toString(obj).getBytes() : Bytes.toBytes(obj);
	}

	private Kafkas() {}
}
