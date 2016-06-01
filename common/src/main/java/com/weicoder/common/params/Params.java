package com.weicoder.common.params;

import java.util.List;

import com.weicoder.common.config.Config;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.EmptyUtil;

/**
 * 系统配置信息 内部使用 config.properties 中配置,本包实现可配置功能<br/>
 * @author WD 
 * 
 */
public final class Params {
	// Properties配置
	private static Config config = new Config("config/config.properties", "config.properties");

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static List<String> getList(String key, List<String> defaultValue) {
		return config.getList(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static String[] getStringArray(String key, String[] defaultValue) {
		return config.getStringArray(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @return value
	 */
	public static String getString(String key) {
		return config.getString(key);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static String getString(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		return config.getBoolean(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static int getInt(String key) {
		return config.getInt(key);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static int getInt(String key, int defaultValue) {
		return config.getInt(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static long getLong(String key, long defaultValue) {
		return config.getLong(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static short getShort(String key, short defaultValue) {
		return config.getShort(key, defaultValue);
	}

	/**
	 * 根据前后缀和和名称获得键
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @param name 名称
	 * @return 替换后的键
	 */
	public static String getKey(String prefix, String name, String suffix) {
		// 声明字符串缓存
		StringBuilder sb = new StringBuilder(prefix);
		// 判断名称是否为空
		if (!EmptyUtil.isEmpty(name)) {
			sb.append(StringConstants.POINT);
			sb.append(name);
		}
		sb.append(StringConstants.POINT);
		sb.append(suffix);
		// 返回替换后的键
		return sb.toString();
	}

	private Params() {}
}
