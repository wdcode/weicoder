package com.weicoder.nosql.kafka.factory;

import org.apache.kafka.clients.consumer.KafkaConsumer;

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
	public static KafkaConsumer<byte[], byte[]> getConsumer(String name) {
		return KafkaConsumerFactory.FACTORY.getInstance(name);
	}

	private KafkaFactory() {}
}
