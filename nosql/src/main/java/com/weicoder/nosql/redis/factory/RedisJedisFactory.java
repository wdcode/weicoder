package com.weicoder.nosql.redis.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.nosql.redis.RedisPool;
import com.weicoder.nosql.redis.impl.RedisJedis;

/**
 * Redis工厂
 * @author WD
 */
final class RedisJedisFactory extends FactoryKey<String, RedisPool> {
	/** JedisPool工厂 */
	final static RedisJedisFactory FACTORY = new RedisJedisFactory();

	@Override
	public RedisPool newInstance(String name) {
		return new RedisJedis(name);
	}

	private RedisJedisFactory() {}
}
