package com.weicoder.core.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 本地缓存 使用google guava 实现
 * @author WD
 */
public class Cache<K, V> {
	// 保存 缓存
	private LoadingCache<K, V> cache;

	/**
	 * 构造
	 * @param max 最大容量
	 * @param init 初始化容量
	 * @param level 修改并发数
	 * @param refresh 刷新时间 秒
	 * @param expire 有效期(时间内无访问) 秒
	 * @param load 加载缓存
	 */
	public Cache(long max, int init, int level, long refresh, long expire, CacheLoad<K, V> load) {
		// 初始化取缓存
		cache = CacheBuilder.newBuilder().maximumSize(max).initialCapacity(init).concurrencyLevel(level)
				.refreshAfterWrite(refresh, TimeUnit.SECONDS).expireAfterAccess(expire, TimeUnit.SECONDS)
				.build(new CacheLoader<K, V>() {
					// 读取缓存
					public V load(K key) throws Exception {
						return load.load(key);
					}
				});
	}

	/**
	 * 加入缓存
	 * @param key 键
	 * @param value 值
	 */
	public void put(K key, V value) {
		cache.put(key, value);
	}

	/**
	 * 获得值
	 * @param key 键
	 * @return 值
	 */
	public V get(K key) {
		try {
			return cache.get(key);
		} catch (ExecutionException e) {
			return null;
		}
	}

	/**
	 * 缓存数量
	 * @return 数量
	 */
	public long size() {
		return cache.size();
	}

	/**
	 * 把缓存转换成map
	 * @return map
	 */
	public ConcurrentMap<K, V> asMap() {
		return cache.asMap();
	}

	/**
	 * 缓存加载捷克
	 * @author WD
	 */
	public static interface CacheLoad<K, V> {
		/**
		 * 根据key 加载缓存
		 * @param key 键
		 * @return 值
		 */
		V load(K key);
	}
}
