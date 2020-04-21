package com.weicoder.nosql.redis;

import redis.clients.jedis.JedisPubSub;

/**
 * Redis订阅接口
 * @author WD
 */
public interface Subscribe {
	/**
	 * 订阅消息
	 * @param jedisPubSub 订阅类
	 * @param channels 通道
	 */
	void subscribe(JedisPubSub jedisPubSub, String... channels);
}
