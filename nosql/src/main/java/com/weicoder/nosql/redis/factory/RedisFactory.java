package com.weicoder.nosql.redis.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.nosql.redis.Redis;
import com.weicoder.nosql.redis.impl.RedisJedis;

/**
 * RedisPool工厂
 * @author WD  
 */
public final class RedisFactory extends FactoryKey<String, Redis> {
	// 工厂
	private final static RedisFactory FACTORY = new RedisFactory();

	/**
	 * 获得Redis
	 * @return Redis
	 */
	public static Redis getRedis() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得Redis
	 * @param key 键
	 * @return Redis
	 */
	public static Redis getRedis(String key) {
		return FACTORY.getInstance(key);
	}

	/**
	 * 实例化一个新对象
	 * @param key 键
	 * @return Redis
	 */
	public Redis newInstance(String key) {
		return new RedisJedis(key);
	}

	private RedisFactory() {}
}
