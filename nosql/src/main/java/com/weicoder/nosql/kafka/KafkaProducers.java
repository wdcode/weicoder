package com.weicoder.nosql.kafka;

import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.weicoder.nosql.kafka.factory.KafkaFactory;

/**
 * 生产者
 * @author WD
 */
public final class KafkaProducers {
	// 生产者
	private final static Producers PRODUCER = KafkaFactory.getProducer();

	/**
	 * 获得生产者
	 * @return 生产者
	 */
	public static Producer<byte[], byte[]> getProducer() {
		return PRODUCER.getProducer();
	}

	/**
	 * 刷新缓存
	 */
	public static void flush() {
		PRODUCER.flush();
	}

	/**
	 * 发送数据
	 * @param topic 节点
	 * @param value 值
	 * @return 信息
	 */
	public static Future<RecordMetadata> send(String topic, Object value) {
		return PRODUCER.send(topic, value);
	}

	/**
	 * 发送数据
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 * @return 信息
	 */
	public static Future<RecordMetadata> send(String topic, Object key, Object value) {
		return PRODUCER.send(topic, key, value);
	}

	/**
	 * 异步发送数据
	 * @param topic 节点
	 * @param value 值
	 */
	public static void asyn(String topic, Object value) {
		PRODUCER.asyn(topic, value);
	}

	/**
	 * 异步发送数据
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 */
	public static void asyn(String topic, Object key, Object value) {
		PRODUCER.asyn(topic, key, value);
	}

	/**
	 * 刷新缓存
	 * @param name kafka名称
	 */
	public static void flush(String name) {
		KafkaFactory.getProducer(name).flush();
	}

	/**
	 * 按kafka名称选择服务器 发送数据
	 * @param name kafka名称
	 * @param topic 节点
	 * @param value 值
	 * @return 信息
	 */
	public static Future<RecordMetadata> sendN(String name, String topic, Object value) {
		return KafkaFactory.getProducer(name).send(topic, value);
	}

	/**
	 * 按kafka名称选择服务器 发送数据
	 * @param name kafka名称
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 * @return 信息
	 */
	public static Future<RecordMetadata> sendN(String name, String topic, Object key,
			Object value) {
		return KafkaFactory.getProducer(name).send(topic, key, value);
	}

	/**
	 * 按kafka名称选择服务器 异步发送数据
	 * @param name kafka名称
	 * @param topic 节点
	 * @param value 值
	 */
	public static void asynN(String name, String topic, Object value) {
		KafkaFactory.getProducer(name).asyn(topic, value);
	}

	/**
	 * 按kafka名称选择服务器 异步发送数据
	 * @param name kafka名称
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 */
	public static void asynN(String name, String topic, Object key, Object value) {
		KafkaFactory.getProducer(name).asyn(topic, key, value);
	}

	private KafkaProducers() {}
}
