package com.weicoder.core.cache;

import com.weicoder.core.cache.Cache.CacheLoad;
import com.weicoder.core.params.CacheParams;

/**
 * 缓存工厂
 * @author WD
 */
public final class CacheBuilder {
	/**
	 * 构建缓存系统
	 * @param load 加载缓存
	 * @return 缓存
	 */
	public static <K, V> Cache<K, V> builder(CacheLoad<K, V> load) {
		return builder(CacheParams.MAX, CacheParams.INIT, CacheParams.LEVEL, CacheParams.REFRESH, CacheParams.EXPIRE, load);
	}

	/**
	 * 构建缓存系统
	 * @param max 最大容量
	 * @param refresh 刷新时间
	 * @param expire 有效期
	 * @param load 加载缓存
	 * @return 缓存
	 */
	public static <K, V> Cache<K, V> builder(long max, long refresh, long expire, CacheLoad<K, V> load) {
		return builder(CacheParams.MAX, CacheParams.INIT, CacheParams.LEVEL, CacheParams.REFRESH, CacheParams.EXPIRE, load);
	}

	/**
	 * 构建缓存系统
	 * @param max 最大容量
	 * @param init 初始容量
	 * @param level 并发级别
	 * @param refresh 刷新时间
	 * @param expire 有效期
	 * @param load 加载缓存
	 * @return 缓存
	 */
	public static <K, V> Cache<K, V> builder(long max, int init, int level, long refresh, long expire, CacheLoad<K, V> load) {
		return new Cache<K, V>(max, init, level, refresh, expire, load);
	}

	private CacheBuilder() {}
}
