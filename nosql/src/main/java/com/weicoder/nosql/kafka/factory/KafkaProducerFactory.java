package com.weicoder.nosql.kafka.factory;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.log.Logs;
import com.weicoder.nosql.params.KafkaParams;

/**
 * kafka消费者工厂
 * @author WD
 */
final class KafkaProducerFactory extends FactoryKey<String, Producer<byte[], byte[]>> {
	/** 工厂 */
	final static KafkaProducerFactory FACTORY = new KafkaProducerFactory();

	@Override
	public Producer<byte[], byte[]> newInstance(String key) {
		// 设置属性
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaParams.getServers(key));
		// if (!EmptyUtil.isEmpty(ZookeeperParams.CONNECT)) {
		// props.put("zookeeper.connect", ZookeeperParams.CONNECT);
		// }

		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		Logs.info("KafkaProducers init complete props={}", props);
		// 实例化生产者
		return new KafkaProducer<>(props);
	}

	private KafkaProducerFactory() {}
}
