package com.weicoder.nosql.redis.impl;

import com.weicoder.nosql.redis.Subscribe;
import com.weicoder.nosql.redis.factory.RedisFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * redis 订阅接口 JedisPool实现
 * @author WD
 */
public class RedisSubscribePool implements Subscribe {
	// redis pool
	private JedisPool pool;

	public RedisSubscribePool(String name) {
		this.pool = RedisFactory.getPool(name);
	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		try (Jedis jedis = pool.getResource()) {
			jedis.subscribe(jedisPubSub, channels);
		}
	}
}
