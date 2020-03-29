package com.weicoder.redis.cache;

import com.weicoder.common.U;
import com.weicoder.common.U.S;
import com.weicoder.common.W.C;
import com.weicoder.common.W.L;
import com.weicoder.common.W.M;
import com.weicoder.common.concurrent.ExecutorUtil;
import com.weicoder.common.interfaces.Callback;

import java.util.List;
import java.util.Map;

import com.weicoder.cache.BeanCache;
import com.weicoder.json.JsonEngine;
import com.weicoder.redis.RedisPool;
import com.weicoder.redis.factory.RedisFactory;
import com.weicoder.redis.params.RedisParams;

/**
 * 使用Redis存储缓存
 * 
 * @author wudi
 */
@SuppressWarnings("unchecked")
public class RedisCache<K, V> extends BeanCache<K, V> {
	/**
	 * 构建一个新的RedisCache
	 * 
	 * @param  <V>   值类型
	 * @param  redis Redis名
	 * @param  key   缓存主key 在hset里的key
	 * @return       一个新的RedisCache
	 */
	public static <K, V> RedisCache<K, V> build(String redis, String key) {
		return build(RedisFactory.getRedis(redis), key);
	}

	/**
	 * 构建一个新的RedisCache
	 * 
	 * @param  <V>   值类型
	 * @param  redis Redis名
	 * @param  key   缓存主key 在hset里的key
	 * @param  load  是否加载全部缓存
	 * @return       一个新的RedisCache
	 */
	public static <K, V> RedisCache<K, V> build(String redis, String key, boolean fill) {
		return build(RedisFactory.getRedis(redis), key, fill);
	}

	/**
	 * 构建一个新的RedisCache
	 * 
	 * @param  <V>   值类型
	 * @param  redis Redis名
	 * @param  key   缓存主key 在hset里的key
	 * @param  cls   要缓存的类
	 * @return       一个新的RedisCache
	 */
	public static <K, V> RedisCache<K, V> build(RedisPool redis, String key) {
		return build(redis, key, true);
	}

	/**
	 * 构建一个新的RedisCache
	 * 
	 * @param  <V>     值类型
	 * @param  redis   Redis名
	 * @param  rkey    缓存主key 在hset里的key
	 * @param  channel 发布订阅的通道
	 * @param  cls     要缓存的类
	 * @param  fill    是否加载全部缓存
	 * @return         一个新的RedisCache
	 */
	public static <K, V> RedisCache<K, V> build(RedisPool redis, String key, boolean fill) {
		return new RedisCache<>(redis, key, U.C.bean(S.convert(key)), fill);
	}

	// redis
	private RedisPool redis;
	// redis channel
	private String put;
	private String remove;

	/**
	 * 加入缓存
	 * 
	 * @param rkey  键
	 * @param value 值
	 */
	public void put(K key, V value) {
		super.put(key, value);
		String json = JsonEngine.toJson(value);
		redis.hset(this.name, C.toString(key), json);
		redis.publish(put, json);
	}

	/**
	 * 删除缓存
	 */
	public void remove(K key) {
		super.remove(key);
		redis.hdel(this.name, C.toString(key));
		redis.publish(remove, C.toString(key));
	}

	/**
	 * 加载所以缓存
	 */
	public void fill() {
		redis.hgetAll(name).forEach((k, v) -> super.put(JsonEngine.toBean(v, val)));
	}

	/**
	 * 获得所有redis里的缓存 注意是使用hgetAll获取redis 如果用本地缓存使用values()
	 * 
	 * @return 所有缓存
	 */
	public Map<K, V> all() {
		Map<K, V> map = M.newMap();
		redis.hgetAll(name).forEach((k, v) -> map.put((K) C.to(k, key), JsonEngine.toBean(v, val)));
		return map;
	}

	/**
	 * 如果本地数量与redis数量一致返回values() 不一致返回all().values()
	 * 
	 * @return 缓存list
	 */
	public List<V> list() {
		return size() == len() ? values() : L.newList(all().values());
	}

	/**
	 * 获得redis里的数量
	 * 
	 * @return
	 */
	public long len() {
		return redis.hlen(name);
	}

	@Override
	public void fill(List<V> vals) {
		super.fill(vals);
		vals.forEach(v -> redis.hset(name, key(v), v));
	}

	/**
	 * 构造
	 * 
	 * @param redis 使用的redis
	 * @param key   保存redis的键
	 * @param cls   缓存值Class
	 * @param load  是否加载全部缓存
	 */
	private RedisCache(RedisPool redis, String key, Class<V> cls, boolean fill) {
		super(key, cls, new Callback<K, V>() {
			@Override
			public V callback(K result) {
				return JsonEngine.toBean(redis.hget(key, key), cls);
			}
		});
		// 获得redis
		this.val = cls;
		this.redis = redis;
		this.put = key + "_put";
		this.remove = key + "_remove";
		// 是否加载所以缓存
		if (fill)
			fill();
		// 使用Redis发布订阅来更新缓存
		ExecutorUtil.pool(RedisParams.PREFIX).execute(() -> {
			this.redis.subscribe((c, m) -> {
				if (put.equals(c))
					// 更新
					super.put(JsonEngine.toBean(m, val));
				else if (remove.equals(c))
					// 删除
					super.remove((K) C.to(m, this.key));
			}, put, remove);
		});
	}
}
