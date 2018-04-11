package com.weicoder.nosql.redis.factory;

import com.weicoder.nosql.redis.RedisPool;
import com.weicoder.nosql.redis.Subscribe;

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
	public static RedisPool getRedis(String key) {
		return RedisJedisFactory.FACTORY.getInstance(key);
	}

	/**
	 * 获得JedisCluster
	 * @param key 键
	 * @return JedisCluster
	 */
	public static JedisCluster getCluster(String key) {
		return JedisClusterFactory.FACTORY.getInstance(key);
	}

	/**
	 * 获得JedisPool
	 * @param key 键
	 * @return Redis
	 */
	public static JedisPool getPool(String key) {
		return JedisPoolFactory.FACTORY.getInstance(key);
	}

	/**
	 * 获得RedisSubscribe
	 * @param key 键
	 * @return RedisSubscribe
	 */
	public static Subscribe getSubscribe(String key) {
		return RedisSubscribeFactory.FACTORY.getInstance(key);
	}

	private RedisFactory() {}
}
