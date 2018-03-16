package com.weicoder.nosql.kafka.factory;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;

import com.weicoder.common.constants.StringConstants;

/**
 * kafka工厂
 * @author WD
 */
public final class KafkaFactory {
	/**
	 * 获得kafka消费者
	 * @param name 名称
	 * @return 消费者
	 */
	public static KafkaConsumer<byte[], byte[]> getConsumer() {
		return getConsumer(StringConstants.EMPTY);
	}

	/**
	 * 获得kafka消费者
	 * @param name 名称
	 * @return 消费者
	 */
	public static KafkaConsumer<byte[], byte[]> getConsumer(String name) {
		return KafkaConsumerFactory.FACTORY.getInstance(name);
	}

	/**
	 * 获得kafka生产者
	 * @param name 名称
	 * @return 生产者
	 */
	public static Producer<byte[], byte[]> getProducer() {
		return getProducer(StringConstants.EMPTY);
	}

	/**
	 * 获得kafka消费者
	 * @param name 名称
	 * @return 生产者
	 */
	public static Producer<byte[], byte[]> getProducer(String name) {
		return KafkaProducerFactory.FACTORY.getInstance(name);
	}

	private KafkaFactory() {}
}
