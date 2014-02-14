package com.weicoder.core.nosql.memcache.factory;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.factory.FactoryKey;
import com.weicoder.core.nosql.memcache.Memcache;
import com.weicoder.core.nosql.memcache.impl.MemcacheArray;
import com.weicoder.core.nosql.memcache.impl.MemcacheSpy;
import com.weicoder.core.nosql.memcache.impl.MemcacheWhalin;
import com.weicoder.core.nosql.memcache.impl.MemcacheX;
import com.weicoder.core.params.MemcacheParams;

/**
 * MemCached的客户端调用工厂
 * @author WD
 * @since JDK7
 * @version 1.0 2011-05-26
 */
public final class MemcacheFactory extends FactoryKey<String, Memcache> {
	// 工厂
	private final static MemcacheFactory	FACTORY;
	static {
		FACTORY = new MemcacheFactory();
	}

	/**
	 * 获得工厂
	 * @return MemCache
	 */
	public static Memcache getMemcache() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得工厂
	 * @param name 缓存名
	 * @return MemCache
	 */
	public static Memcache getMemcache(String name) {
		return FACTORY.getInstance(name);
	}

	/**
	 * 实例化一个新对象
	 */
	public Memcache newInstance() {
		// 获得集群发送名称服务器
		String[] names = MemcacheParams.NAMES;
		// 判断集群是否为空
		if (EmptyUtil.isEmpty(names)) {
			return newInstance(StringConstants.EMPTY);
		} else {
			return new MemcacheArray(names);
		}
	}

	/**
	 * 实例化一个新对象
	 * @param name 缓存名
	 * @return MemCache
	 */
	public Memcache newInstance(String name) {
		switch (MemcacheParams.getParse(name)) {
			case "x":
				return new MemcacheX(name);
			case "spy":
				return new MemcacheSpy(name);
			default:
				return new MemcacheWhalin(name);
		}
	}

	private MemcacheFactory() {}
}
