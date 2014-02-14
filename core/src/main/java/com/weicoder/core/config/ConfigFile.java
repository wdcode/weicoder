package com.weicoder.core.config;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.FileConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.interfaces.Config;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * Apache Commons-Configuration 实现
 * @author WD
 * @since JDK7
 * @version 1.0 2012-9-16
 */
final class ConfigFile implements Config {
	// 保存键值并发Map
	private Map<String, Object>	map;
	// Apache commons Configuration
	private FileConfiguration	file;

	/**
	 * 构造方法
	 * @param fileName 文件名
	 */
	public ConfigFile(String fileName) {
		try {
			// 声明Map列表
			map = Maps.getConcurrentMap();
			// 判断文件类型
			if (fileName.indexOf(FileConstants.SUFFIX_XML) > -1) {
				// xml文件
				file = new XMLConfiguration(fileName);
			} else {
				// properties文件
				file = new PropertiesConfiguration(fileName);
			}
		} catch (Exception e) {}
	}

	/**
	 * 获得指定标识下第一级.和.之间的Key列表
	 * @param prefix 标识
	 * @return key列表
	 */
	public String[] getKeys(String prefix) {
		// 声明列表
		List<String> list = Lists.getList();
		// 循环解析出标识
		for (Iterator<String> it = file.getKeys(prefix); it.hasNext();) {
			list.add(StringUtil.subString(it.next(), StringConstants.POINT, StringConstants.POINT));
		}
		// 返回列表
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 设置属性 如果这个key存在 会替换掉这个属性
	 * @param key 属性key
	 * @param value 属性value
	 */
	public void setProperty(String key, Object value) {
		// 设置Map里的值
		map.put(key, value);
		// 添加配置
		file.setProperty(key, value);
	}

	/**
	 * 设置属性 如果这个key存在 会替换掉这个属性
	 * @param key 属性key
	 * @param value 属性value
	 */
	public Object getProperty(String key, Object defaultValue) {
		// 先从Map中获得值
		Object value = map.get(key);
		// 判断value为null
		if (value == null) {
			// 读取配置
			try {
				value = EmptyUtil.isEmpty(file) ? defaultValue : file.getProperty(key);
			} catch (Exception e) {}
			// 添加到Map中
			map.put(key, value == null ? value = defaultValue : value);
		}
		// 返回值
		return value;
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
	public String[] getStringArray(String key, String[] defaultValue) {
		// 先从Map中获得值
		String[] value = (String[]) map.get(key);
		// 判断value为null
		if (value == null) {
			// 读取配置
			try {
				value = file.getStringArray(key);
			} catch (Exception e) {}
			// 添加到Map中
			map.put(key, EmptyUtil.isEmpty(value) ? value = defaultValue : value);
		}
		// 返回值
		return value;
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
		return Conversion.toString(getProperty(key, Conversion.toString(defaultValue)));
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		return Conversion.toBoolean(getProperty(key, defaultValue));
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public int getInt(String key) {
		return Conversion.toInt(getProperty(key, 0));
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public int getInt(String key, int defaultValue) {
		return Conversion.toInt(getProperty(key, defaultValue));
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public long getLong(String key, long defaultValue) {
		return Conversion.toLong(getProperty(key, defaultValue));
	}

	/**
	 * 获得属性value
	 * @param key 属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public short getShort(String key, short defaultValue) {
		return Conversion.toShort(getProperty(key, defaultValue));
	}

	@Override
	public void write() {
		try {
			file.save();
		} catch (Exception e) {}
	}

	@Override
	public Object getProperty(String key) {
		return file.getProperty(key);
	}

	@Override
	public String[] getStringArray(String key) {
		return file.getStringArray(key);
	}

	@Override
	public boolean isEmpty() {
		return EmptyUtil.isEmpty(file);
	}

	@Override
	public void clear() {
		map.clear();
		file.clear();
	}
}
