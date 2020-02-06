package com.weicoder.redis.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.redis.impl.RedisCluster;

/**
 * RedisCluster 工厂
 * @author wudi
 *
 */
final class JedisClusterFactory extends FactoryKey<String, RedisCluster> {
	final static JedisClusterFactory FACTORY = new JedisClusterFactory();
	@Override
	public RedisCluster newInstance(String key) { 
		return new RedisCluster(key);
	}
	
	private JedisClusterFactory() {}
}
