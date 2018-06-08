package com.weicoder.nosql.kafka.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.nosql.kafka.Producers;

/**
 * kafka消费者工厂
 * @author WD
 */
final class KafkaProducerFactory extends FactoryKey<String, Producers> {
	/** 工厂 */
	final static KafkaProducerFactory FACTORY = new KafkaProducerFactory();

	@Override
	public Producers newInstance(String key) {
		return new Producers(key);
	}

	private KafkaProducerFactory() {}
}
