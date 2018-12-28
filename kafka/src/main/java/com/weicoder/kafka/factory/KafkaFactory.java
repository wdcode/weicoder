package com.weicoder.kafka.factory;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.kafka.Producers;

/**
 * kafka工厂
 * @author WD
 */
public final class KafkaFactory {
	/**
	 * 获得kafka消费者
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
	 * @return 生产者
	 */
	public static Producers getProducer() {
		return getProducer(StringConstants.EMPTY);
	}

	/**
	 * 获得kafka消费者
	 * @param name 名称
	 * @return 生产者
	 */
	public static Producers getProducer(String name) {
		return KafkaProducerFactory.FACTORY.getInstance(name);
	}

	private KafkaFactory() {}
}
