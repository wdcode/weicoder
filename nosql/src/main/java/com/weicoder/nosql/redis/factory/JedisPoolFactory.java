package com.weicoder.nosql.redis.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.log.Logs;
import com.weicoder.nosql.params.RedisParams;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * JedisPool工厂
 * @author WD
 */
final class JedisPoolFactory extends FactoryKey<String, JedisPool> {
	/** JedisPool工厂 */
	final static JedisPoolFactory FACTORY = new JedisPoolFactory();

	@Override
	public JedisPool newInstance(String name) {
		// 实例化Jedis配置
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置属性
		config.setMaxTotal(RedisParams.getMaxTotal(name));
		config.setMaxIdle(RedisParams.getMaxIdle(name));
		config.setMaxWaitMillis(RedisParams.getMaxWait(name));
		// 实例化连接池
		Logs.info("redis init pool config={}", config);
		return new JedisPool(config, RedisParams.getHost(name), RedisParams.getPort(name), Protocol.DEFAULT_TIMEOUT,
				RedisParams.getPassword(name), RedisParams.getDatabase(name), null);
	}

	private JedisPoolFactory() {}
}
