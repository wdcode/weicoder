package com.weicoder.kafka;

import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.weicoder.kafka.factory.KafkaFactory;
import com.weicoder.kafka.producer.Producers;

/**
 * kafka生成器
 * 
 * @author WD
 */
public final class Kafkas {
	// 生产者
	private final static Producers PRODUCER = KafkaFactory.getProducer();

	/**
	 * 获得生产者
	 * 
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
	 * 
	 * @param  topic 节点
	 * @param  value 值
	 * @return       信息
	 */
	public static Future<RecordMetadata> send(String topic, Object value) {
		return PRODUCER.send(topic, value);
	}

	/**
	 * 发送数据
	 * 
	 * @param  topic    节点
	 * @param  key      键
	 * @param  value    值
	 * @param  callback 回调
	 * @return          信息
	 */
	public static Future<RecordMetadata> send(String topic, Object key, Object value, Callback callback) {
		return PRODUCER.send(topic, key, value, callback);
	}

	/**
	 * 发送数据
	 * 
	 * @param  topic 节点
	 * @param  value 值
	 * @return       信息
	 */
	public static Future<RecordMetadata> send(String topic, Object value, Callback callback) {
		return PRODUCER.send(topic, value, callback);
	}

	/**
	 * 发送数据
	 * 
	 * @param  topic 节点
	 * @param  key   键
	 * @param  value 值
	 * @return       信息
	 */
	public static Future<RecordMetadata> send(String topic, Object key, Object value) {
		return PRODUCER.send(topic, key, value);
	}

	/**
	 * 刷新缓存
	 * 
	 * @param name kafka名称
	 */
	public static void flush(String name) {
		KafkaFactory.getProducer(name).flush();
	}

	/**
	 * 按kafka名称选择服务器 发送数据
	 * 
	 * @param  name  kafka名称
	 * @param  topic 节点
	 * @param  value 值
	 * @return       信息
	 */
	public static Future<RecordMetadata> sendN(String name, String topic, Object value) {
		return KafkaFactory.getProducer(name).send(topic, value);
	}

	/**
	 * 按kafka名称选择服务器 发送数据
	 * 
	 * @param  name  kafka名称
	 * @param  topic 节点
	 * @param  key   键
	 * @param  value 值
	 * @return       信息
	 */
	public static Future<RecordMetadata> sendN(String name, String topic, Object key, Object value) {
		return KafkaFactory.getProducer(name).send(topic, key, value);
	}

	private Kafkas() {
	}
}
