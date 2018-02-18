package com.weicoder.nosql.redis;

import com.weicoder.nosql.redis.factory.RedisFactory;

/**
 * MongoDB引擎
 * @author WD
 */
public final class RedisEngine {
	// Mongo接口
	private final static Redis REDIS = RedisFactory.getRedis();

	/**
	 * 压缩值 当值能压缩时才压缩
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	public static String compress(String key, Object value) {
		return REDIS.compress(key, value);
	}

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * @param key 键
	 * @return 值
	 */
	public static byte[] extract(String key) {
		return REDIS.extract(key);
	}

	/**
	 * 设置键值 无论存储空间是否存在相同键，都保存
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	public static String set(String key, String value) {
		return REDIS.set(key, value);
	}

	/**
	 * 删除键值
	 * @param key 键
	 */
	public static void del(String key) {
		REDIS.del(key);
	}

	/**
	 * 根据键获得值
	 * @param key 键
	 * @return 值
	 */
	public static Object get(String key) {
		return REDIS.get(key);
	}

	private RedisEngine() {}
}
