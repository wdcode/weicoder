package com.weicoder.common.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.weicoder.common.log.Logs;
import com.weicoder.common.util.ResourceUtil;

/**
 * 读取配置 Properties 实现
 * @author WD
 * @since JDK7
 * @version 1.0
 */
public final class ConfigProperties implements Config {
	// Properties配置
	private Properties	ps	= new Properties();

	/**
	 * 构造
	 * @param filename 配置文件名
	 */
	public ConfigProperties(String filename) {
		try {
			ps.load(ResourceUtil.loadResource(filename));
		} catch (IOException e) {
			Logs.warn(e);
		}
	}

	@Override
	public Object getProperty(String key) {
		return ps.getProperty(key);
	}

	@Override
	public Object getProperty(String key, Object defaultValue) {
		return ps.getProperty(key, defaultValue.toString());
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void clear() {

	}

	@Override
	public void write() {

	}

	@Override
	public void setProperty(String key, Object value) {

	}

	@Override
	public String[] getStringArray(String key) {

		return null;
	}

	@Override
	public String[] getKeys(String prefix) {

		return null;
	}

	@Override
	public List<String> getList(String key, List<String> defaultValue) {

		return null;
	}

	@Override
	public String[] getStringArray(String key, String[] defaultValue) {

		return null;
	}

	@Override
	public String getString(String key) {

		return null;
	}

	@Override
	public String getString(String key, String defaultValue) {

		return null;
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {

		return false;
	}

	@Override
	public int getInt(String key) {

		return 0;
	}

	@Override
	public int getInt(String key, int defaultValue) {

		return 0;
	}

	@Override
	public long getLong(String key, long defaultValue) {

		return 0;
	}

	@Override
	public short getShort(String key, short defaultValue) {
		return 0;
	}
}
