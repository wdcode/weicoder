package com.weicoder.nosql.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.weicoder.common.log.Logs;
import com.weicoder.nosql.params.KafkaParams;

import java.util.Properties;
import java.util.UUID;

/**
 * kafka消费者
 * @author WD
 */
public final class KafkaConsumers {
	// 消费者
	private final static KafkaConsumer<String, String> CONSUMER;

	static {
		// 设置属性
		Properties props = new Properties();
		// 组名
		props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
		// 单次最大取出量
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaParams.SERVERS);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		CONSUMER = new KafkaConsumer<String, String>(props);
		// 监听消息
		CONSUMER.subscribe(KafkaParams.TOPICS);
		Logs.info("KafkaConsumer init props={} subscribe topic={}", props, KafkaParams.TOPICS);
	}

	/**
	 * 从kafka队列里读取数据
	 * @return ConsumerRecords
	 */
	public static ConsumerRecords<String, String> poll() {
		return CONSUMER.poll(100000);
	}

	private KafkaConsumers() {}
}