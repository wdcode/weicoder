package com.weicoder.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.ResourceUtil;

/**
 * 读取配置类
 * @author WD 
 *  
 */
public class Config {
	// Properties配置
	private Properties ps;

	/**
	 * 构造参数
	 * @param fileName
	 * @return
	 */
	public Config(String... fileName) {
		// 声明Properties
		ps = new Properties();
		// 获取配置文件流
		InputStream in = null;
		// 循环加载文件
		for (String name : fileName) {
			in = ResourceUtil.loadResource(name);
			// 有配置文件加载
			if (in != null) {
				try {
					ps.load(in);
					break;
				} catch (IOException e) {
					Logs.error(e);					
				} finally {
					CloseUtil.close(in);
				}
			}
		}
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public List<String> getList(String key, List<String> defaultValue) {
		return Lists.getList(getStringArray(key, EmptyUtil.isEmpty(defaultValue) ? ArrayConstants.STRING_EMPTY : Lists.toArray(defaultValue)));
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public String[] getStringArray(String key) {
		return getStringArray(key, ArrayConstants.STRING_EMPTY);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public String[] getStringArray(String key, String[] defaultValue) {
		// 获得字符串
		String s = getString(key);
		// 如果为空返回默认值 不为空以,拆分
		if (EmptyUtil.isEmpty(s)) {
			return defaultValue;
		} else {
			return s.split(StringConstants.COMMA);
		}
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @return value
	 */
	public String getString(String key) {
		return getString(key, StringConstants.EMPTY);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public String getString(String key, String defaultValue) {
		return ps.getProperty(key, defaultValue);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		return Conversion.toBoolean(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public int getInt(String key) {
		return getInt(key, 0);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public int getInt(String key, int defaultValue) {
		return Conversion.toInt(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public long getLong(String key, long defaultValue) {
		return Conversion.toLong(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public short getShort(String key, short defaultValue) {
		return Conversion.toShort(getString(key), defaultValue);
	}
}
