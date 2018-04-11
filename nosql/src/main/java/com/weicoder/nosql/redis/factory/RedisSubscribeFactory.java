package com.weicoder.nosql.redis.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.nosql.params.RedisParams;
import com.weicoder.nosql.redis.Subscribe;
import com.weicoder.nosql.redis.impl.RedisSubscribeCluster;
import com.weicoder.nosql.redis.impl.RedisSubscribePool;

/**
 * JedisCluster工厂
 * @author WD
 */
final class RedisSubscribeFactory extends FactoryKey<String, Subscribe> {
	/** RedisSubscribe工厂 */
	final static RedisSubscribeFactory FACTORY = new RedisSubscribeFactory();

	@Override
	public Subscribe newInstance(String name) {
		// 判断类型
		switch (RedisParams.getType(name)) {
			case "pool":
				return new RedisSubscribePool(name);
			default:
				return new RedisSubscribeCluster(name);
		}
	}

	private RedisSubscribeFactory() {}
}
