package com.weicoder.memcache.factory;

import com.weicoder.common.constants.C;
import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.util.U;
import com.weicoder.memcache.params.MemcacheParams;
import com.weicoder.memcache.Memcache;
import com.weicoder.memcache.impl.MemcacheArray;
import com.weicoder.memcache.impl.MemcacheWhalin;

/**
 * MemCached的客户端调用工厂
 * @author WD  
 */
public final class MemcacheFactory extends FactoryKey<String, Memcache> {
	// 工厂
	private final static MemcacheFactory FACTORY = new MemcacheFactory();

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
		if (U.E.isEmpty(names)) {
			return newInstance(C.S.EMPTY);
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
		return new MemcacheWhalin(name);
	}

	private MemcacheFactory() {}
}
