package com.weicoder.redis.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.redis.impl.RedisJedis;

/**
 * JedisPool 工厂
 * 
 * @author wudi
 */
final class JedisPoolFactory extends FactoryKey<String, RedisJedis> {
	final static JedisPoolFactory FACTORY = new JedisPoolFactory();

	@Override
	public RedisJedis newInstance(String key) { 
		return new RedisJedis(key);
	}

	private JedisPoolFactory() {
	}
}
