package com.weicoder.core.nosql.mongo.factory;

import com.weicoder.core.factory.FactoryKey;
import com.weicoder.core.nosql.mongo.Mongo;
import com.weicoder.core.nosql.mongo.impl.MongoImpl;

/**
 * MongoDB工厂
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-23
 */
public final class MongoFactory extends FactoryKey<String, Mongo> {
	// MongoDB工厂
	private final static MongoFactory	FACTORY;
	static {
		FACTORY = new MongoFactory();
	}

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
