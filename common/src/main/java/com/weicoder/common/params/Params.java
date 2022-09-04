package com.weicoder.common.params;

import java.util.List;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.U;

/**
 * 系统配置信息 内部使用 CONFIG.properties 中配置,本包实现可配置功能
 * 
 * @author WD
 */
public sealed class Params permits P {
	// Properties配置
	private final static Config CONFIG = getConfig("config");

	/**
	 * 获得读取config.properties配置器
	 * 
	 * @return Config
	 */
	public final static Config getConfig() {
		return CONFIG;
	}

	/**
	 * 根据name获得配置
	 * 
	 * @param name 名称
	 * @return 对应的配置
	 */
	public final static Config getConfig(String name) {
		return ConfigFactory.getConfig(name);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static List<String> getList(String key, List<String> defaultValue) {
		return CONFIG.getList(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static String[] getStringArray(String key, String[] defaultValue) {
		return CONFIG.getStringArray(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	public static String getString(String key) {
		return CONFIG.getString(key);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static String getString(String key, String defaultValue) {
		return CONFIG.getString(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		return CONFIG.getBoolean(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	public static int getInt(String key) {
		return CONFIG.getInt(key);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static int getInt(String key, int defaultValue) {
		return CONFIG.getInt(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	public static byte getByte(String key) {
		return CONFIG.getByte(key);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static byte getByte(String key, byte defaultValue) {
		return CONFIG.getByte(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static long getLong(String key, long defaultValue) {
		return CONFIG.getLong(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static short getShort(String key, short defaultValue) {
		return CONFIG.getShort(key, defaultValue);
	}

	/**
	 * 根据前后缀和和名称获得键
	 * 
	 * @param name   名称
	 * @param suffix 后缀
	 * @return 替换后的键
	 */
	public static String getKey(String name, String suffix) {
		return getKey(StringConstants.EMPTY, name, suffix);
	}

	/**
	 * 根据前后缀和和名称获得键
	 * 
	 * @param prefix 前缀
	 * @param name   名称
	 * @param suffix 后缀
	 * @return 替换后的键
	 */
	public static String getKey(String prefix, String name, String suffix) {
		// 声明字符串缓存
		StringBuilder sb = new StringBuilder(prefix);
		// 前缀不为空添加.
		if (U.E.isNotEmpty(prefix))
			sb.append(StringConstants.POINT);
		// 名称不为空
		if (U.E.isNotEmpty(name))
			sb.append(name).append(StringConstants.POINT);
		// 后缀不为空添加.
		if (U.E.isNotEmpty(suffix))
			sb.append(suffix);
		// 返回替换后的键
		return sb.toString();
	}

	/**
	 * 检查键是否存在
	 * 
	 * @param key 键
	 * @return 是否存在值
	 */
	public static boolean exists(String key) {
		return CONFIG.exists(key);
	}
}
