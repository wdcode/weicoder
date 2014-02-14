package com.weicoder.common.interfaces;

import java.util.List;

/**
 * 读取配置接口
 * @author WD
 * @since JDK7
 * @version 1.0 2012-02-26
 */
public interface Config extends Empty, Clear {
	/**
	 * 写配置文件
	 */
	void write();

	/**
	 * 设置属性 如果这个key存在 会替换掉这个属性
	 * @param key 属性key
	 * @param value 属性value
	 */
	void setProperty(String key, Object value);

	/**
	 * 根据key获得一个属性值 返回一个对象
	 * @param key 属性key
	 * @return 对象值
	 */
	Object getProperty(String key);

	/**
	 * 获得属性value数组
	 * @param key 属性key
	 * @return value数组
	 */
	String[] getStringArray(String key);

	/**
	 * 获得指定标识下第一级.和.之间的Key列表
	 * @param prefix 标识
	 * @return key列表
	 */
	String[] getKeys(String prefix);

	/**
	 * 设置属性 如果这个key存在 会替换掉这个属性
	 * @param key 属性key
	 * @param value 属性value
	 */
	Object getProperty(String key, Object defaultValue);

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	List<String> getList(String key, List<String> defaultValue);

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	String[] getStringArray(String key, String[] defaultValue);

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @return value
	 */
	String getString(String key);

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	String getString(String key, String defaultValue);

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	boolean getBoolean(String key, boolean defaultValue);

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	int getInt(String key);

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	int getInt(String key, int defaultValue);

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	long getLong(String key, long defaultValue);

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	short getShort(String key, short defaultValue);
}
