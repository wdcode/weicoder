package com.weicoder.common.config;

import java.util.List;

import com.weicoder.common.W;
import com.weicoder.common.C;
import com.weicoder.common.U;
import com.weicoder.common.U.E;

/**
 * 读取配置类
 * 
 * @author WD
 */
public interface Config {
	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	String getString(String key);

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	default List<String> getList(String key, List<String> defaultValue) {
		return W.C.value(W.L.newList(getStringArray(key)), defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	default String[] getStringArray(String key) {
		return getStringArray(key, C.A.STRING_EMPTY);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	default String[] getStringArray(String key, String[] defaultValue) {
		return U.S.split(getString(key), C.S.COMMA, defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	default String getString(String key, String defaultValue) {
		return W.C.value(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	default boolean getBoolean(String key, boolean defaultValue) {
		return W.C.toBoolean(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	default int getInt(String key) {
		return getInt(key, 0);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	default int getInt(String key, int defaultValue) {
		return W.C.toInt(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	default byte getByte(String key) {
		return getByte(key, (byte) 0);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	default byte getByte(String key, byte defaultValue) {
		return W.C.toByte(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	default long getLong(String key, long defaultValue) {
		return W.C.toLong(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	default short getShort(String key, short defaultValue) {
		return W.C.toShort(getString(key), defaultValue);
	}

	/**
	 * 检查键是否存在
	 * 
	 * @param key 键
	 * @return 是否存在值
	 */
	default boolean exists(String key) {
		return E.isNotEmpty(getString(key));
	}
}
