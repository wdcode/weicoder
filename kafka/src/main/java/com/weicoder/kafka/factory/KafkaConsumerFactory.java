package com.weicoder.kafka.factory;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.log.Logs;
import com.weicoder.kafka.params.KafkaParams;

/**
 * kafka消费者工厂
 * @author WD
 */
final class KafkaConsumerFactory extends FactoryKey<String, KafkaConsumer<byte[], byte[]>> {
	/** 工厂 */
	final static KafkaConsumerFactory FACTORY = new KafkaConsumerFactory();

	@Override
	public KafkaConsumer<byte[], byte[]> newInstance(String key) {
		// 设置属性
		Properties props = new Properties();
		// 组名
		props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaParams.getGroup(key));
		// 单次最大取出量
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, KafkaParams.getMaxPoll(key));
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, KafkaParams.getTimeout(key));
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaParams.getOffsetReset(key));
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaParams.getServers(key));
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, KafkaParams.getInterval(key));
		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		Logs.info("new KafkaConsumer key={} props={}", key, props);
		return new KafkaConsumer<byte[], byte[]>(props);
	}

	private KafkaConsumerFactory() {
	}
}
