package com.weicoder.common.lang;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.weicoder.common.util.EmptyUtil;

/**
 * Map的帮助类,获得Map的一些操作
 * @author WD
 * @since JDK7
 * @version 1.0 2009-09-08
 */
public final class Maps {
	/**
	 * 判断是否Map
	 * @param obj 对象
	 * @return 是否Map
	 */
	public static boolean isMap(Object obj) {
		return !EmptyUtil.isEmpty(obj) && obj instanceof Map<?, ?>;
	}

	/**
	 * 获得Map实例 默认初始化大小为10
	 * @return Map
	 */
	public static <K, V> Map<K, V> getMap() {
		return getHashMap();
	}

	/**
	 * 获得Map实例
	 * @param size 初始化大小
	 * @return Map
	 */
	public static <K, V> Map<K, V> getMap(int size) {
		return getHashMap(size < 1 ? 1 : size);
	}

	/**
	 * 获得Map实例
	 * @param map 初始化的Map
	 * @return Map
	 */
	public static <K, V> Map<K, V> getMap(Map<K, V> map) {
		return getHashMap(map);
	}

	/**
	 * 获得Map实例
	 * @param key 键
	 * @param value 值
	 * @return Map
	 */
	public static <K, V> Map<K, V> getMap(K key, V value) {
		// 获得Map
		Map<K, V> map = getMap();
		// 设置键值
		map.put(key, value);
		// 返回Map
		return map;
	}

	/**
	 * 获得Map实例
	 * @param keys 键数组
	 * @param values 值数组
	 * @return Map
	 */
	public static <K, V> Map<K, V> getMap(K[] keys, V[] values) {
		return getMap(Lists.getList(keys), Lists.getList(values));
	}

	/**
	 * 获得Map实例
	 * @param keys 键数组
	 * @param values 值数组
	 * @return Map
	 */
	public static <K, V> Map<K, V> getMap(List<K> keys, List<V> values) {
		// 判断key和value为空或则键值数量不同 返回空Map
		if (EmptyUtil.isEmpty(keys) || EmptyUtil.isEmpty(values) || keys.size() != values.size()) {
			return emptyMap();
		}
		// 获得Map
		Map<K, V> map = getMap();
		// 循环填充map
		for (int i = 0; i < keys.size(); i++) {
			// 设置键值
			map.put(keys.get(i), values.get(i));
		}
		// 返回Map
		return map;
	}

	/**
	 * 获得Map实例
	 * @param map 初始化的Map
	 * @return Map
	 */
	public static <K, V> Map<K, V> getMap(Map<K, V>... maps) {
		// 获得一个map
		Map<K, V> map = getMap();
		// 循环maps
		for (int i = 0; i < maps.length; i++) {
			// 添加到map
			map.putAll(maps[i]);
		}
		// 返回map
		return map;
	}

	/**
	 * 获得Map实例 实现类是HashMap
	 * @return Map
	 */
	public static <K, V> HashMap<K, V> getHashMap() {
		return new HashMap<K, V>();
	}

	/**
	 * 获得Map实例 实现类是HashMap
	 * @param size 初始化大小
	 * @return Map
	 */
	public static <K, V> HashMap<K, V> getHashMap(int size) {
		return new HashMap<K, V>(size);
	}

	/**
	 * 获得Map实例 实现类是HashMap
	 * @param map 初始化的Map
	 * @return Map
	 */
	public static <K, V> HashMap<K, V> getHashMap(Map<K, V> map) {
		return map == null ? new HashMap<K, V>() : new HashMap<K, V>(map);
	}

	/**
	 * 获得Map实例 实现类是LinkedHashMap 默认初始化大小为10
	 * @return Map
	 */
	public static <K, V> LinkedHashMap<K, V> getLinkedHashMap() {
		return getLinkedHashMap(10);
	}

	/**
	 * 获得Map实例 实现类是LinkedHashMap
	 * @param size 初始化大小
	 * @return Map
	 */
	public static <K, V> LinkedHashMap<K, V> getLinkedHashMap(int size) {
		return new LinkedHashMap<K, V>(size);
	}

	/**
	 * 获得Map实例 实现类是LinkedHashMap
	 * @param map 初始化的Map
	 * @return Map
	 */
	public static <K, V> LinkedHashMap<K, V> getLinkedHashMap(Map<K, V> map) {
		return new LinkedHashMap<K, V>(map);
	}

	/**
	 * 获得同步的Map实例 实现类是HashMap 默认初始化大小为10
	 * @return Map
	 */
	public static <K, V> Map<K, V> getSynchronizedMap() {
		return getSynchronizedMap(10);
	}

	/**
	 * 获得同步的Map实例 实现类是HashMap
	 * @param size 初始化大小
	 * @return Map
	 */
	public static <K, V> Map<K, V> getSynchronizedMap(int size) {
		return Collections.synchronizedMap(new HashMap<K, V>(size));
	}

	/**
	 * 获得同步的Map实例 实现类是HashMap
	 * @param map 初始化的Map
	 * @return Map
	 */
	public static <K, V> Map<K, V> getSynchronizedMap(Map<K, V> map) {
		return Collections.synchronizedMap(new HashMap<K, V>(map));
	}

	/**
	 * 获得同步的Map实例 实现类是ConcurrentHashMap 默认初始化大小为10
	 * @return Map
	 */
	public static <K, V> ConcurrentMap<K, V> getConcurrentMap() {
		return new ConcurrentHashMap<K, V>();
	}

	/**
	 * 获得同步的Map实例 实现类是ConcurrentHashMap
	 * @param size 初始化大小
	 * @return Map
	 */
	public static <K, V> ConcurrentMap<K, V> getConcurrentMap(int size) {
		return new ConcurrentHashMap<K, V>(size);
	}

	/**
	 * 获得同步的Map实例 实现类是ConcurrentHashMap
	 * @param map 初始化的Map
	 * @return Map
	 */
	public static <K, V> ConcurrentMap<K, V> getConcurrentMap(Map<K, V> map) {
		return new ConcurrentHashMap<K, V>(map);
	}

	/**
	 * 获得一个不可变的空Map
	 * @return 一个不可变的空Map
	 */
	public static <K, V> Map<K, V> emptyMap() {
		return Collections.emptyMap();
	}

	/**
	 * 获得列表数量
	 * @param map 数据列表
	 * @return 数量
	 */
	public static int size(Map<?, ?> map) {
		return EmptyUtil.isEmpty(map) ? 0 : map.size();
	}

	/**
	 * 私有构造 禁止外部实例化
	 */
	private Maps() {}
}
