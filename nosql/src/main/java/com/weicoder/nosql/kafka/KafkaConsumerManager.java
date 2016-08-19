package com.weicoder.nosql.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

/**
 * kafka消费者
 * @author WD
 */
public class KafkaConsumerManager {
	// 消费者
	private KafkaConsumer<String, String> consumer;

	public KafkaConsumerManager(String connectString) {
		final Properties props = new Properties();
		props.put("bootstrap.servers", connectString);
		props.put("group.id", UUID.randomUUID().toString());
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(props);

	}

	public void run() {
		try {
			// 即时房间广播消息
			consumer.subscribe(Arrays.asList("room_broadcast"));
			ConsumerRecords<String, String> records = consumer.poll(100);

			for (ConsumerRecord<String, String> record : records) {

				String content = record.value();
				if (content == null || "".equals(content)) {
					continue;
				}
			}

			// 用户权重 weightChange key=uidStr value=weight
			consumer.subscribe(Arrays.asList("weightChange"));
			records = consumer.poll(100);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}