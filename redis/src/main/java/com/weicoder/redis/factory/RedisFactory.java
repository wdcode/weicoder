package com.weicoder.redis.factory;

import com.weicoder.common.util.EmptyUtil;
import com.weicoder.redis.params.RedisParams;
import com.weicoder.redis.RedisPool;
import com.weicoder.redis.Subscribe;
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
	public static RedisPool getRedis(String name) {
		return EmptyUtil.isEmpty(RedisParams.getCluster(name)) ? getPool(name) : getCluster(name);
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

	/**
	 * 获得redis发布订阅对象
	 * 
	 * @param  name 名称
	 * @return      redis发布订阅对象
	 */
	public static Subscribe getSubscribe(String name) {
		// 判断类型
		switch (RedisParams.getType(name)) {
			case "cluster":
				return getCluster(name);
			default:
				return getPool(name);
		}
	}

	private RedisFactory() {
	}
}
