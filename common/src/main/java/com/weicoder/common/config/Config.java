package com.weicoder.common.config;

import java.util.List;

/**
 * 读取配置类
 * 
 * @author WD
 */
public interface Config {
	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	List<String> getList(String key, List<String> defaultValue);

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	String[] getStringArray(String key);

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	String[] getStringArray(String key, String[] defaultValue);

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
	String getString(String key, String defaultValue);

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	boolean getBoolean(String key, boolean defaultValue);

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	int getInt(String key);

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	int getInt(String key, int defaultValue);

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	byte getByte(String key);

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	byte getByte(String key, byte defaultValue);

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	long getLong(String key, long defaultValue);

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	short getShort(String key, short defaultValue);

	/**
	 * 检查键是否存在
	 * 
	 * @param key 键
	 * @return 是否存在值
	 */
	boolean exists(String key);
}
