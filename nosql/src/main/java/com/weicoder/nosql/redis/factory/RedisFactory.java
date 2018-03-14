package com.weicoder.nosql.redis.factory;

import com.weicoder.nosql.redis.Redis;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * RedisPool工厂
 * @author WD
 */
public final class RedisFactory {
	/**
	 * 获得Redis
	 * @param key 键
	 * @return Redis
	 */
	public static Redis getRedis(String key) {
		return RedisJedisFactory.FACTORY.getInstance(key);
	}

	/**
	 * 获得JedisCluster
	 * @param key 键
	 * @return JedisCluster
	 */
	public static JedisCluster getJedisCluster(String key) {
		return JedisClusterFactory.FACTORY.getInstance(key);
	}

	/**
	 * 获得JedisPool
	 * @param key 键
	 * @return Redis
	 */
	public static JedisPool getJedisPool(String key) {
		return JedisPoolFactory.FACTORY.getInstance(key);
	}

	private RedisFactory() {}
}
