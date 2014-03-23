package com.weicoder.core.nosql.base;

import java.util.List;

import com.weicoder.common.lang.Lists;
import com.weicoder.core.nosql.NoSQL;
import com.weicoder.core.zip.ZipEngine;

/**
 * NoSQL基类
 * @author WD
 * @since JDK7
 * @version 1.0 2012-11-18
 */
public abstract class BaseNoSQL implements NoSQL {
	/**
	 * 压缩值 当值能压缩时才压缩
	 * @param key 键
	 * @param value 值
	 */
	public final boolean compress(String key, Object value) {
		return set(key, ZipEngine.compress(value));
	}

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * @param key 键
	 * @return 值
	 */
	public final byte[] extract(String key) {
		return ZipEngine.extract(get(key));
	}

	/**
	 * 获得多个键的数组
	 * @param key 键
	 * @return 值
	 */
	public Object[] get(String... keys) {
		// 声明列表
		Object[] objs = new Object[keys.length];
		// 循环解压数据
		for (int i = 0; i < keys.length; i++) {
			objs[i] = get(keys[i]);
		}
		// 返回列表
		return objs;
	}

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	public List<byte[]> extract(String... keys) {
		// 声明列表
		List<byte[]> list = Lists.getList(keys.length);
		// 循环解压数据
		for (Object o : get(keys)) {
			list.add(ZipEngine.extract(o));
		}
		// 返回列表
		return list;
	}
}
