package com.weicoder.nosql.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.weicoder.common.log.Logs;
import com.weicoder.nosql.params.KafkaParams;

/**
 * 生产者
 * @author WD
 */
public final class KafkaProducers {
	// 生产者
	private final static Producer<String, String> producer;

	static {
		Properties props = new Properties();
		props.put("bootstrap.servers", KafkaParams.SERVERS);
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);
	}

	/**
	 * 发送数据
	 * @param topic 节点
	 * @param value 值
	 */
	public static void send(String topic, String value) {
		producer.send(new ProducerRecord<>(topic, value));
		Logs.debug("kafka send producer topic={},value={}", topic, value);
	}

	/**
	 * 发送数据
	 * @param topic 节点
	 * @param key 键
	 * @param value 值
	 */
	public static void send(String topic, String key, String value) {
		producer.send(new ProducerRecord<>(topic, key, value));
		Logs.debug("kafka send producer topic={},key={},value={}", topic, key, value);
	}

	private KafkaProducers() {}
}
