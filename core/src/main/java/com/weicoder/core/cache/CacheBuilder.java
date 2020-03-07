package com.weicoder.core.cache;

import static com.weicoder.core.params.CacheParams.*;

import com.weicoder.core.cache.Cache.CacheLoad;

/**
 * 缓存工厂
 * 
 * @author WD
 */
public final class CacheBuilder {
	/**
	 * 构建缓存系统
	 * 
	 * @param  load 加载缓存
	 * @return      缓存
	 */
	public static <K, V> Cache<K, V> build(CacheLoad<K, V> load) {
		return build(MAX, INIT, LEVEL, REFRESH, EXPIRE, load);
	}

	/**
	 * 构建缓存系统
	 * 
	 * @param  max     最大容量
	 * @param  refresh 刷新时间
	 * @param  expire  有效期
	 * @param  load    加载缓存
	 * @return         缓存
	 */
	public static <K, V> Cache<K, V> build(long max, long refresh, long expire, CacheLoad<K, V> load) {
		return build(max, INIT, LEVEL, refresh, expire, load);
	}

	/**
	 * 构建缓存系统
	 * 
	 * @param  max     最大容量
	 * @param  init    初始容量
	 * @param  level   并发级别
	 * @param  refresh 刷新时间
	 * @param  expire  有效期
	 * @param  load    加载缓存
	 * @return         缓存
	 */
	public static <K, V> Cache<K, V> build(long max, int init, int level, long refresh, long expire,
			CacheLoad<K, V> load) {
		return new Cache<K, V>(max, init, level, refresh, expire, load);
	}

	private CacheBuilder() {
	}
}
