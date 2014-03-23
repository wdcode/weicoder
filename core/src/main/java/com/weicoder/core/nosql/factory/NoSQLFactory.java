package com.weicoder.core.nosql.factory;

import com.weicoder.core.factory.FactoryKey;
import com.weicoder.core.nosql.NoSQL;
import com.weicoder.core.nosql.berkeley.factory.BerkeleyFactory;
import com.weicoder.core.nosql.hbase.factory.HBaseFactory;
import com.weicoder.core.nosql.memcache.factory.MemcacheFactory;
import com.weicoder.core.nosql.mongo.factory.MongoFactory;
import com.weicoder.core.nosql.redis.factory.RedisFactory;
import com.weicoder.core.params.NoSQLParams;

/**
 * NoSQL工厂
 * @author WD
 * @since JDK7
 * @version 1.0 2012-11-18
 */
public final class NoSQLFactory extends FactoryKey<String, NoSQL> {
	// 工厂
	private final static NoSQLFactory	FACTORY;
	static {
		FACTORY = new NoSQLFactory();
	}

	/**
	 * 获得NoSQL接口
	 * @param name 配置名
	 * @return NoSQL接口
	 */
	public static NoSQL getNoSQL(String name) {
		return FACTORY.getInstance(name);
	}

	@Override
	public NoSQL newInstance(String key) {
		switch (NoSQLParams.getParse(key)) {
			case "memcache":
				return MemcacheFactory.getMemcache(key);
			case "redis":
				return RedisFactory.getRedis(key);
			case "hbase":
				return HBaseFactory.getHBase(key);
			case "mongodb":
				return MongoFactory.getMongo(key);
			case "berkeleydb":
				return BerkeleyFactory.getBerkeley(key);
		}
		return null;
	}

	private NoSQLFactory() {}
}
