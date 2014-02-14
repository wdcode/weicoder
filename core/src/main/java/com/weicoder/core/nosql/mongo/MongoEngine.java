package com.weicoder.core.nosql.mongo;

import com.weicoder.core.nosql.mongo.factory.MongoFactory;

/**
 * MongoDB引擎
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-23
 */
public final class MongoEngine {
	// Mongo接口
	private final static Mongo	MONGO;
	static {
		MONGO = MongoFactory.getMongo();
	}

	/**
	 * 压缩值 当值能压缩时才压缩
	 * @param key 键
	 * @param value 值
	 */
	public static boolean compress(String key, Object value) {
		return MONGO.compress(key, value);
	}

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * @param key 键
	 * @return 值
	 */
	public static byte[] extract(String key) {
		return MONGO.extract(key);
	}

	/**
	 * 设置键值 无论存储空间是否存在相同键，都保存
	 * @param key 键
	 * @param value 值
	 */
	public static boolean set(String key, Object value) {
		return MONGO.set(key, value);
	}

	/**
	 * 删除键值
	 * @param key 键
	 */
	public static void remove(String key) {
		MONGO.remove(key);
	}

	/**
	 * 根据键获得值
	 * @param key 键
	 * @return 值
	 */
	public static Object get(String key) {
		return MONGO.get(key);
	}

	private MongoEngine() {}
}
