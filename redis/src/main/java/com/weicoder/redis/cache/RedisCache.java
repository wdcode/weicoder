package com.weicoder.redis.cache;

import com.weicoder.common.U;
import com.weicoder.common.W.C;
import com.weicoder.common.W.L;
import com.weicoder.common.W.M;
import com.weicoder.common.concurrent.ExecutorUtil;

import java.util.List;
import java.util.Map;

import com.weicoder.cache.BeanCache;
import com.weicoder.json.JsonEngine;
import com.weicoder.redis.Redis;
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
	 * @param <V>   值类型
	 * @param redis Redis名
	 * @param key   缓存主key 在hset里的key
	 * @return 一个新的RedisCache
	 */
	public static <K, V> RedisCache<K, V> build(String redis, String key, Class<V> cls) {
		return build(RedisFactory.getRedis(redis), key, cls);
	}

	/**
	 * 构建一个新的RedisCache
	 * 
	 * @param <V>   值类型
	 * @param redis Redis名
	 * @param key   缓存主key 在hset里的key
	 * @param load  是否加载全部缓存
	 * @return 一个新的RedisCache
	 */
	public static <K, V> RedisCache<K, V> build(String redis, String key, Class<V> cls, boolean fill) {
		return build(RedisFactory.getRedis(redis), key, cls, fill);
	}

	/**
	 * 构建一个新的RedisCache
	 * 
	 * @param <V>   值类型
	 * @param redis Redis名
	 * @param key   缓存主key 在hset里的key
	 * @param cls   要缓存的类
	 * @return 一个新的RedisCache
	 */
	public static <K, V> RedisCache<K, V> build(Redis redis, String key, Class<V> cls) {
		return build(redis, key, cls, RedisParams.getCacheFill(redis.name()));
	}

	/**
	 * 构建一个新的RedisCache
	 * 
	 * @param <V>   值类型
	 * @param redis Redis名
	 * @param key   缓存主key 在hset里的key
	 * @param cls   要缓存的类
	 * @param fill  是否加载全部缓存
	 * @return 一个新的RedisCache
	 */
	public static <K, V> RedisCache<K, V> build(Redis redis, String key, Class<V> cls, boolean fill) {
		return new RedisCache<>(redis, key, cls, fill);
	}

	// redis
	protected Redis redis;
	// redis channel
	protected String put;
	protected String remove;
	protected String push;
	// 是否基础类型
	protected boolean base;
	// 基础类型传递分隔符
	protected final static String SEPA = "||";

	/**
	 * 获得当前redis缓存使用的redis
	 * 
	 * @return
	 */
	public Redis redis() {
		return redis;
	}

	/**
	 * 加入缓存
	 * 
	 * @param rkey  键
	 * @param value 值
	 */
	public V put(K key, V value) {
		super.put(key, value);
		String k = C.toString(key);
		String v = base ? C.toString(value) : JsonEngine.toJson(value);
		redis.multi(r -> {
			r.hset(this.name, k, v);
			r.lpush(push, k);
			r.publish(put, base ? k + SEPA + v : v);
		});
		return value;
	}

	/**
	 * 删除缓存
	 */
	public void remove(K key) {
		super.remove(key);
		redis.multi(r -> {
			r.hdel(this.name, C.toString(key));
			r.publish(remove, C.toString(key));
		});
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
	 * 如果本地数量与redis数量一致返回super.map() 不一致返回all()
	 * 
	 * @return 缓存list
	 */
	public Map<K, V> map() {
		return size() == len() ? super.map() : all();
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
		vals.forEach(v -> {
			K k = key(v);
			cache.put(k, v);
			redis.hset(name, k, v);
		});
	}

	/**
	 * 构造
	 * 
	 * @param redis 使用的redis
	 * @param key   保存redis的键
	 * @param cls   缓存值Class
	 * @param load  是否加载全部缓存
	 */
	protected RedisCache(Redis redis, String key, Class<V> cls, boolean fill) {
		super(key, cls, r -> cls == null || U.C.isBaseType(cls) ? (V) C.to(redis.hget(key, C.toString(r)), cls)
				: JsonEngine.toBean(redis.hget(key, C.toString(r)), cls));
		// 获得redis
		this.val = cls;
		this.redis = redis;
		this.base = cls == null || U.C.isBaseType(cls);
		this.put = key + "_put";
		this.remove = key + "_remove";
		this.push = key + "_push";
		// 是否加载所以缓存
		if (fill)
			fill();
		// 使用Redis发布订阅来更新缓存
		ExecutorUtil.pool(RedisParams.PREFIX).execute(() -> {
			this.redis.subscribe((c, m) -> {
				if (put.equals(c))
					// 更新
					if (base) {
						String[] t = U.S.split(m, SEPA);
						cache.put((K) C.to(t[0], this.key), (V) C.to(t[1], this.val));
					} else {
						V v = JsonEngine.toBean(m, this.val);
						cache.put(key(v), v);
					}
				else if (remove.equals(c))
					// 删除
					super.remove((K) C.to(m, this.key));
			}, put, remove);
		});
	}
}
