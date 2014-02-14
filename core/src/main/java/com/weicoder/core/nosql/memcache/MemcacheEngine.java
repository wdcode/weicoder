package com.weicoder.core.nosql.memcache;

import java.util.List;
import java.util.Map;

import com.weicoder.core.nosql.memcache.factory.MemcacheFactory;

/**
 * Memcache 处理器 使用工厂默认对象
 * @author WD
 * @since JDK7
 * @version 1.0 2012-08-29
 */
public final class MemcacheEngine {
	// memcache接口
	private final static Memcache	MEMCACHE;
	static {
		MEMCACHE = MemcacheFactory.getMemcache();
	}

	/**
	 * 压缩值 当值能压缩时才压缩
	 * @param key 键
	 * @param value 值
	 */
	public static boolean compress(String key, Object value) {
		return MEMCACHE.compress(key, value);
	}

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * @param key 键
	 * @return 值
	 */
	public static byte[] extract(String key) {
		return MEMCACHE.extract(key);
	}

	/**
	 * 设置键值 无论存储空间是否存在相同键，都保存
	 * @param key 键
	 * @param value 值
	 */
	public static boolean set(String key, Object value) {
		return MEMCACHE.set(key, value);
	}

	/**
	 * 追加键值
	 * @param key 键
	 * @param value 值
	 */
	public static boolean append(String key, Object value) {
		return MEMCACHE.append(key, value);
	}

	/**
	 * 删除键值
	 * @param key 键
	 */
	public static void remove(String key) {
		MEMCACHE.remove(key);
	}

	/**
	 * 根据键获得值
	 * @param key 键
	 * @return 值
	 */
	public static Object get(String key) {
		return MEMCACHE.get(key);
	}

	/**
	 * 获得多个键的Map
	 * @param keys 键
	 * @return 值
	 */
	public static Map<String, Object> getMap(String... keys) {
		return MEMCACHE.getMap(keys);
	}

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	public static Object[] get(String... keys) {
		return MEMCACHE.get(keys);
	}

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	public static List<byte[]> extract(String... keys) {
		return MEMCACHE.extract(keys);
	}

	private MemcacheEngine() {}
}
