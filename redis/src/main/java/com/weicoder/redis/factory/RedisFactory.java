package com.weicoder.redis.factory;

import com.weicoder.redis.params.RedisParams;
import com.weicoder.common.util.U;
import com.weicoder.redis.Redis; 
import com.weicoder.redis.impl.RedisCluster;
import com.weicoder.redis.impl.RedisJedis;

/**
 * RedisPool工厂
 * 
 * @author WD
 */
public final class RedisFactory {
	/**
	 * 获得Redis
	 * 
	 * @param  name 键
	 * @return      Redis
	 */
	public static Redis getRedis(String name) {
		return U.E.isEmpty(RedisParams.getCluster(name)) ? getPool(name) : getCluster(name);
	}

	/**
	 * 获得JedisCluster
	 * 
	 * @param  key 键
	 * @return     JedisCluster
	 */
	public static RedisCluster getCluster(String key) {
		return JedisClusterFactory.FACTORY.getInstance(key);
	}

	/**
	 * 获得JedisPool
	 * 
	 * @param  key 键
	 * @return     Redis
	 */
	public static RedisJedis getPool(String key) {
		return JedisPoolFactory.FACTORY.getInstance(key);
	}

	private RedisFactory() {
	}
}
