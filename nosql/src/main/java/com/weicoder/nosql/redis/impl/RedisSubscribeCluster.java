package com.weicoder.nosql.redis.impl;

import com.weicoder.nosql.redis.Subscribe;
import com.weicoder.nosql.redis.factory.RedisFactory;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPubSub;

/**
 * redis 订阅接口 JedisCluster实现
 * @author WD
 */
public class RedisSubscribeCluster implements Subscribe {
	// JedisCluster
	private JedisCluster cluster;

	/**
	 * 构造
	 * @param name 名称
	 */
	public RedisSubscribeCluster(String name) {
		this.cluster = RedisFactory.getCluster(name);
	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		cluster.subscribe(jedisPubSub, channels);
	}
}
