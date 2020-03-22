package com.weicoder.kafka.init;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer; 

import com.weicoder.common.concurrent.ScheduledUtil;
import com.weicoder.common.init.Init;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.U; 
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.json.JsonEngine;
import com.weicoder.kafka.annotation.AllTopic;
import com.weicoder.kafka.annotation.Consumer;
import com.weicoder.kafka.annotation.Topic;
import com.weicoder.kafka.consumer.Record;
import com.weicoder.kafka.factory.KafkaFactory;
import com.weicoder.kafka.params.KafkaParams;
import com.weicoder.protobuf.Protobuf;
import com.weicoder.protobuf.ProtobufEngine;

/**
 * kafka生成器
 * 
 * @author WD
 */
public class KafkaInit implements Init {
	// 日志
	private Log LOG = LogFactory.getLog(KafkaInit.class);
	// 保存Topic对应对象
	private Map<String, Object> CONSUMERS = Maps.newMap();
	// 保存Topic对应方法
	private Map<String, Method> METHODS = Maps.newMap();
	// 保存所有topic对应方法
	private Map<String, List<Method>> ALL_TOPICS = Maps.newMap();
	// 保存kafka消费
	private Map<String, KafkaConsumer<byte[], byte[]>> KAFKA_CONSUMERS = Maps.newMap();
	// 保存kafka对应消费的topic
	private Map<String, List<String>> TOPICS = Maps.newMap();
	// 保存Topic队列
	private Map<String, Map<String, Queue<ConsumerRecord<byte[], byte[]>>>> TOPIC_RECORDS = Maps.newConcurrentMap();

	@Override
	public void init() {
		// 获得所有kafka消费者
		List<Class<Consumer>> consumers = ClassUtil.getAnnotationClass(CommonParams.getPackages("kafka"),
				Consumer.class);
		if (U.E.isNotEmpty(consumers)) {
			// 循环处理kafka类
			consumers.forEach(c -> {
				// 执行对象
				Object consumer = ClassUtil.newInstance(c);
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
					if (topic == null) {
						// 获得所有topic标记
						AllTopic all = m.getAnnotation(AllTopic.class);
						if (all != null) {
							// 声明列表
							List<Method> list = ALL_TOPICS.get(name);
							if (list == null) {
								ALL_TOPICS.put(name, list = Lists.newList());
							}
							// 添加到列表
							list.add(m);
						}
					} else {
						String val = topic.value();
						METHODS.put(val, m);
						CONSUMERS.put(val, consumer);
						topics.add(val);
						map.put(val, new ConcurrentLinkedQueue<>());// LinkedBlockingQueue
						LOG.info("add kafka consumer={} topic={}", c.getSimpleName(), val);
					}
				}
			});
			LOG.info("add kafka Consumers size={}", consumers.size());
			// 订阅相关消费数据
			TOPICS.keySet().forEach(key -> {
				List<String> topics = TOPICS.get(key);
				KAFKA_CONSUMERS.get(key).subscribe(topics);
				LOG.info("Kafkas init Consumer={} subscribe topic={}", key, topics);
			});
			// 读取topic定时 启动定时读取kafka消息
			ScheduledUtil.newDelay(() -> {
				KAFKA_CONSUMERS.forEach((name, consumer) -> {
					// 线程池id
					long tid = Thread.currentThread().getId();
					// 日志使用
					long time = System.currentTimeMillis();
					int n = 0;
					// 根据name获得kafka消费者列表
					Map<String, Queue<ConsumerRecord<byte[], byte[]>>> map = TOPIC_RECORDS.get(name);
					// 获得消费数据
					for (ConsumerRecord<byte[], byte[]> record : consumer.poll(Duration.ofSeconds(1))) {
						// 获得消费对象类和方法
						LOG.debug("kafka read consumer thread={} record={}", tid, record);
						map.get(record.topic()).add(record);
						n++;
					}
					// 数量不为空
					if (n > 0) {
						LOG.info("kafka read consumer end name={} size={} time={} thread={}", name, n,
								System.currentTimeMillis() - time, tid);
					}
				});
			}, 0L, 10L);
			// 消费队列
			TOPIC_RECORDS.forEach((name, map) -> {
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
							if (U.E.isEmpty(params))
								// 参数为空直接执行方法
								BeanUtil.invoke(obj, method);
							else {
								// 参数
								objs = new Object[params.length];
								// 有参数 现在只支持 1-2位的参数，1个参数表示value,2个参数表示key,value
								Parameter param = params[0];
								Class<?> t = param.getType();
								if (params.length == 1) {
									if (ConsumerRecord.class.equals(t))
										objs[0] = record;
									else if (Record.class.equals(t)) {
										Type type = param.getParameterizedType();
										Class<?>[] gc = ClassUtil.getGenericClass(type);
										objs[0] = new Record<>(record.topic(), toParam(record.key(), gc[0]),
												toParam(record.value(), gc[1]), record.offset(), record.timestamp());
										// 执行所有topic方法
										List<Method> all = ALL_TOPICS.get(name);
										if (U.E.isEmpty(all)) {
											for (Method m : all) {
												BeanUtil.invoke(obj, m, objs);
											}
										}
									} else
										objs[0] = toParam(record.value(), t);
								} else {
									objs[0] = toParam(record.key(), t);
									objs[1] = toParam(record.value(), params[1].getType());
								}
								// 执行方法
								BeanUtil.invoke(obj, method, objs);
							}
							LOG.debug("kafka consumer topic={} offset={} method={} args={} params={} thread={}", topic,
									offset, method.getName(), objs, params, tid);
							n++;
						}
						// 数量不为空
						if (n > 0) {
							LOG.info("kafka consumer end topic={} offset={} size={} time={} thread={}", topic, offset,
									n, System.currentTimeMillis() - time, tid);
						}
					}, 10L);
				});
			});
		}

	}

	/**
	 * 转换成参数
	 * 
	 * @param  b 字节数组
	 * @param  c 类型
	 * @return   参数
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
		// Protobuf
		else if (c.isAnnotationPresent(Protobuf.class))
			return ProtobufEngine.toBean(b, c);
		// 序列化
		return Bytes.to(b, c);
	}
}
