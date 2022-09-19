package com.weicoder.cache;

import static com.weicoder.common.params.CacheParams.*;

import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.util.U;

/**
 * 缓存工厂
 * 
 * @author WD
 */
public final class CacheBuilder {
	/**
	 * 实体类缓存构建
	 * 
	 * @param  <K>  键
	 * @param  <V>  值
	 * @param  name 缓存名称
	 * @param  load 缓存加载
	 * @return      实体类缓存
	 */
	public static <K, V> BeanCache<K, V> build(String name, Callback<K, V> load) {
		return new BeanCache<>(name, U.C.bean(name), load);
	}

	/**
	 * 构建缓存系统
	 * 
	 * @param  load 加载缓存
	 * @return      缓存
	 */
	public static <K, V> LoadCache<K, V> build(Callback<K, V> load) {
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
	public static <K, V> LoadCache<K, V> build(long max, long refresh, long expire, Callback<K, V> load) {
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
	public static <K, V> LoadCache<K, V> build(long max, int init, int level, long refresh, long expire,
			Callback<K, V> load) {
		return new LoadCache<K, V>(max, init, level, refresh, expire, load);
	}

	private CacheBuilder() {
	}
}
