package com.weicoder.nosql.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * kafka生产者
 * @author WD
 */
public class KafkaProducerManager {
	// 生产者
	private Producer<String, String> producer;

	public KafkaProducerManager(String connectString) {

		Properties props = new Properties();
		props.put("bootstrap.servers", connectString);
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
	 * @param key 键
	 * @param value 值
	 */
	public void send(String topic, String key, String value) {
		producer.send(new ProducerRecord<>(topic, key, value));
	}
}