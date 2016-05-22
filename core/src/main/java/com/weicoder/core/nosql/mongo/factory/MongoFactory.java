package com.weicoder.core.nosql.mongo.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.core.nosql.mongo.Mongo;
import com.weicoder.core.nosql.mongo.impl.MongoImpl;

/**
 * MongoDB工厂
 * @author WD 
 * @version 1.0  
 */
public final class MongoFactory extends FactoryKey<String, Mongo> {
	// MongoDB工厂
	private final static MongoFactory FACTORY = new MongoFactory();

	/**
	 * 获得Mongo
	 * @return
	 */
	public static Mongo getMongo() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得Mongo
	 * @param name Mongo名称
	 * @return
	 */
	public static Mongo getMongo(String name) {
		return FACTORY.getInstance(name);
	}

	@Override
	public Mongo newInstance(String key) {
		return new MongoImpl(key);
	}
}
