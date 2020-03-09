package com.weicoder.redis.cache;

import static com.weicoder.cache.params.CacheParams.*;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.weicoder.common.concurrent.ExecutorUtil;
import com.weicoder.cache.Cache;
import com.weicoder.json.JsonEngine;
import com.weicoder.redis.RedisPool;
import com.weicoder.redis.factory.RedisFactory;
import com.weicoder.redis.params.RedisParams;

/**
 * 使用Redis存储缓存
 * 
 * @author wudi
 */
public class RedisCache<V> extends Cache<String, V> {
	// redis
	private RedisPool redis;
	// redis key
	private String key;
	// class
	private Class<V> cls;
	// redis channel
	private String put;
	private String remove;

	/**
	 * 构建一个新的RedisCache
	 * 
	 * @param  <V>   值类型
	 * @param  redis Redis名
	 * @param  key   缓存主key 在hset里的key
	 * @param  cls   要缓存的类
	 * @return       一个新的RedisCache
	 */
	public static <V> RedisCache<V> build(String redis, String key, Class<V> cls) {
		return build(RedisFactory.getRedis(redis), key, cls);
	}

	/**
	 * 构建一个新的RedisCache
	 * 
	 * @param  <V>     值类型
	 * @param  redis   Redis名
	 * @param  key     缓存主key 在hset里的key
	 * @param  channel 发布订阅的通道
	 * @param  cls     要缓存的类
	 * @return         一个新的RedisCache
	 */
	public static <V> RedisCache<V> build(RedisPool redis, String key, Class<V> cls) {
		return new RedisCache<>(redis, key, cls);
	}

	/**
	 * 加入缓存
	 * 
	 * @param key   键
	 * @param value 值
	 */
	public void put(String key, V value) {
		super.put(key, value);
		redis.hset(this.key, key, JsonEngine.toJson(value));
		redis.publish(put, key);
	}

	/**
	 * 删除缓存
	 */
	public void remove(String key) {
		super.remove(key);
		redis.hdel(this.key, key);
		redis.publish(remove, key);
	}

	private V getRedis(String k) {
		return JsonEngine.toBean(redis.hget(key, k), cls);
	}

	private RedisCache(RedisPool redis, String key, Class<V> cls) {
		// 获得redis
		this.redis = redis;
		this.key = key;
		this.put = key + "_put";
		this.remove = key + "_remove";
		// 反射出本地缓存泛型类
		this.cls = cls;
		// 初始化取缓存
		cache = CacheBuilder.newBuilder().maximumSize(MAX).initialCapacity(INIT).concurrencyLevel(LEVEL)
				.refreshAfterWrite(REFRESH, TimeUnit.SECONDS).expireAfterAccess(EXPIRE, TimeUnit.SECONDS)
				.build(new CacheLoader<String, V>() {
					// 读取缓存
					public V load(String key) throws Exception {
						return getRedis(key);
					}
				});
		// 使用Redis发布订阅来更新缓存
		ExecutorUtil.pool(RedisParams.PREFIX).execute(() -> {
			this.redis.subscribe((c, m) -> {
				// 收到更新消息后获得redis里缓存
				V v = getRedis(m);
				// 如果获得缓存为空或通知删除 删除缓存 否则更新缓存
				if (v == null || remove.equals(c))
					super.remove(m);
				else
					super.put(m, v);
			}, put, remove);
		});
	}
}
