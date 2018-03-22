package com.weicoder.nosql.kafka;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.weicoder.common.concurrent.ScheduledUtil;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.nosql.kafka.annotation.Consumer;
import com.weicoder.nosql.kafka.annotation.Topic;
import com.weicoder.nosql.kafka.factory.KafkaFactory;

/**
 * kafka生成器
 * @author WD
 */
public final class Kafkas {
	// 保存Topic对应对象
	private final static Map<String, Object>						CONSUMERS		= Maps.newMap();
	// 保存Topic对应方法
	private final static Map<String, Method>						METHODS			= Maps.newMap();
	// 保存kafka消费
	private final static Map<String, KafkaConsumer<byte[], byte[]>>	KAFKA_CONSUMERS	= Maps.newMap();
	// 保存kafka对应消费的topic
	private final static Map<String, List<String>>					TOPICS			= Maps.newMap();

	/**
	 * 初始化
	 */
	public static void init() {
		List<Class<Consumer>> consumers = ClassUtil.getAnnotationClass(CommonParams.getPackages("kafka"),
				Consumer.class);
		if (!EmptyUtil.isEmpty(consumers)) {
			// 循环处理kafka类
			for (Class<Consumer> c : consumers) {
				// 执行对象
				Object consumer = BeanUtil.newInstance(c);
				String name = consumer.getClass().getAnnotation(Consumer.class).value();
				// 如果KafkaConsumer列表里没有相对应的消费者 创建
				if (!KAFKA_CONSUMERS.containsKey(name)) {
					KAFKA_CONSUMERS.put(name, KafkaFactory.getConsumer(name));
				}
				// 获得topic列表
				List<String> topics = Maps.getList(TOPICS, name, String.class);
				// 处理所有方法
				for (Method m : c.getMethods()) {
					// 方法有执行时间注解
					Topic topic = m.getAnnotation(Topic.class);
					if (topic != null) {
						String val = topic.value();
						METHODS.put(val, m);
						CONSUMERS.put(val, consumer);
						topics.add(val);
						Logs.info("add kafka Consumer={} topic={}", c.getSimpleName(), val);
					}
				}
			}
			Logs.info("add kafka Consumers size={}", consumers.size());
			// 订阅相关消费数据
			for (String key : TOPICS.keySet()) {
				List<String> topics = TOPICS.get(key);
				KAFKA_CONSUMERS.get(key).subscribe(topics);
				Logs.info("Kafkas init Consumer={} subscribe topic={}", key, topics);
			}
			// 启动定时读取kafka消息
			for (final KafkaConsumer<byte[], byte[]> consumer : KAFKA_CONSUMERS.values()) {
				ScheduledUtil.delay(() -> {
					// 日志使用
					int time = DateUtil.getTime();
					int n = 0;
					// 获得消费数据
					for (ConsumerRecord<byte[], byte[]> record : consumer.poll(1000)) {
						// 获得消费对象类和方法
						Logs.debug("kafka read consumer record={}", record);
						String topic = record.topic();
						Object obj = CONSUMERS.get(topic);
						Method method = METHODS.get(topic);
						// 获得所有参数
						Parameter[] params = method.getParameters();
						Object[] objs = new Object[params.length];
						if (EmptyUtil.isEmpty(params)) {
							// 参数为空直接执行方法
							BeanUtil.invoke(obj, method);
						} else {
							// 有参数 现在只支持 1-2位的参数，1个参数表示value,2个参数表示key,value
							if (params.length == 1) {
								objs[0] = toParam(record.value(), params[0].getType());
							} else {
								objs[0] = toParam(record.key(), params[0].getType());
								objs[1] = toParam(record.value(), params[1].getType());
							}
							// 执行方法
							BeanUtil.invoke(obj, method, objs);
						}
						n++;
						Logs.debug("kafka consumer method={} params={} args={}", method.getName(), params, objs);
					}
					// 数量不为空
					if (n > 0) {
						Logs.debug("kafka consumer end size={}  time={}", n, DateUtil.getTime() - time);
					}
				}, 1);
			}
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
		return String.class.equals(c) ? StringUtil.toString(b) : Bytes.to(b, c);
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
