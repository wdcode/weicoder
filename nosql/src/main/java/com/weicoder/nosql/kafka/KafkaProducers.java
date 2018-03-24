package com.weicoder.nosql.kafka;

import org.apache.kafka.clients.producer.Producer;

import com.weicoder.common.concurrent.ExecutorUtil;
import com.weicoder.common.log.Logs;
import com.weicoder.nosql.kafka.factory.KafkaFactory;

/**
 * 生产者
 * @author WD
 */
public final class KafkaProducers {
	// 生产者
	private final static Producer<byte[], byte[]> PRODUCER = KafkaFactory.getProducer();

	/**
	 * 发送数据
	 * @param topic 节点
	 * @param value 值
	 */
	public static void send(String topic, Object value) {
		PRODUCER.send(Kafkas.newRecord(topic, value));
		// PRODUCER.flush();
		Logs.debug("kafka send producer topic={},value={}", topic, value);
	}

	/**
	 * 发送数据
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 */
	public static void send(String topic, Object key, Object value) {
		PRODUCER.send(Kafkas.newRecord(topic, key, value));
		// PRODUCER.flush();
		Logs.debug("kafka send producer topic={},key={},value={}", topic, key, value);
	}

	/**
	 * 异步发送数据
	 * @param topic 节点
	 * @param value 值
	 */
	public static void asyn(String topic, Object value) {
		ExecutorUtil.pool().execute(() -> {
			send(topic, value);
		});
	}

	/**
	 * 异步发送数据
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 */
	public static void asyn(String topic, Object key, Object value) {
		ExecutorUtil.pool().execute(() -> {
			send(topic, key, value);
		});
	}

	/**
	 * 按kafka名称选择服务器 发送数据
	 * @param topic 节点
	 * @param value 值
	 */
	public static void sendN(String name, String topic, Object value) {
		KafkaFactory.getProducer(name).send(Kafkas.newRecord(topic, value));
		// PRODUCER.flush();
		Logs.debug("kafka send producer name={} topic={},value={}", name, topic, value);
	}

	/**
	 * 按kafka名称选择服务器 发送数据
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 */
	public static void sendN(String name, String topic, Object key, Object value) {
		KafkaFactory.getProducer(name).send(Kafkas.newRecord(topic, key, value));
		// PRODUCER.flush();
		Logs.debug("kafka send producer name={} topic={} key={} value={}", name, topic, key, value);
	}

	/**
	 * 按kafka名称选择服务器 异步发送数据
	 * @param topic 节点
	 * @param value 值
	 */
	public static void asynN(String name, String topic, Object value) {
		ExecutorUtil.pool().execute(() -> {
			sendN(name, topic, value);
		});
	}

	/**
	 * 按kafka名称选择服务器 异步发送数据
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 */
	public static void asynN(String name, String topic, Object key, Object value) {
		ExecutorUtil.pool().execute(() -> {
			sendN(name, topic, key, value);
		});
	}

	private KafkaProducers() {}
}
