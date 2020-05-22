package com.weicoder.cache;

import static com.weicoder.cache.params.CacheParams.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.lang.Lists;

/**
 * 本地缓存 使用google guava 实现
 * 
 * @author WD
 */
public class LoadCache<K, V> {
	// 保存 缓存
	protected LoadingCache<K, V> cache;
//	// 保存空对象
//	protected V empty;

	/**
	 * 构造
	 * 
	 * @param load 加载缓存
	 */
	protected LoadCache(Callback<K, V> load) {
		this(MAX, INIT, LEVEL, REFRESH, EXPIRE, load);
	}

	/**
	 * 构造
	 * 
	 * @param max     最大容量
	 * @param init    初始化容量
	 * @param level   修改并发数
	 * @param refresh 刷新时间 秒
	 * @param expire  有效期(时间内无访问) 秒
	 * @param load    加载缓存
	 */
	LoadCache(long max, int init, int level, long refresh, long expire, Callback<K, V> load) {
		// 初始化取缓存
		cache = CacheBuilder.newBuilder().maximumSize(max).initialCapacity(init).concurrencyLevel(level)
				.refreshAfterWrite(refresh, TimeUnit.SECONDS).expireAfterAccess(expire, TimeUnit.SECONDS)
				.build(new CacheLoader<K, V>() {
					// 读取缓存
					public V load(K key) throws Exception {
						return load.callback(key);
					}
				});
	}

	/**
	 * 加入缓存
	 * 
	 * @param key   键
	 * @param value 值
	 */
	public V put(K key, V value) {
		cache.put(key, value);
		return value;
	}

	/**
	 * 获得
	 * 值
	 * 
	 * @param key 键
	 * @return 值
	 */
	public V get(K key) {
		try {
			return cache.get(key);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得值
	 * 
	 * @param key  键
	 * @param call 如果获取的值为空 处理类 处理后不为空还会填充回内存
	 * @return 值
	 */
	public V get(K key, Callback<K, V> call) {
		// 获取值
		V val = get(key);
		// 值为空 回调处理
		if (val == null)
			val = call.callback(key);
		// 处理后的值不为空 放回缓存
		if (val != null)
			put(key, val);
		// 返回值
		return val;
	}

	/**
	 * 缓存数量
	 * 
	 * @return 数量
	 */
	public long size() {
		return cache.size();
	}

	/**
	 * 查询缓存是否存在
	 * 
	 * @param uid 用户id
	 * @return
	 */
	public boolean exists(K key) {
		try {
			return cache.get(key) != null;
		} catch (ExecutionException e) {
			return false;
		}
	}

	/**
	 * 把缓存转换成map
	 * 
	 * @return map
	 */
	public Map<K, V> map() {
		return cache.asMap();
	}

	/**
	 * 把缓存key转换成Set返回
	 * 
	 * @return Set
	 */
	public Set<K> keys() {
		return map().keySet();
	}

	/**
	 * 把缓存value转换成list返回
	 * 
	 * @return list
	 */
	public List<V> values() {
		return Lists.newList(map().values());
	}

	/**
	 * 清除缓存
	 */
	public void clean() {
		cache.invalidateAll();
		cache.cleanUp();
	}

	/**
	 * 删除缓存
	 */
	public void remove(K key) {
		cache.invalidate(key);
	}
}
