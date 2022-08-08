package com.weicoder.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.weicoder.common.U;
import com.weicoder.common.U.R;

/**
 * 读取配置类
 * 
 * @author WD
 */
public class ConfigProperties implements Config {
	// Properties配置
	private Properties ps;

	/**
	 * 构造参数
	 * 
	 * @param fileName 文件名 可以已,分割
	 */
	public ConfigProperties(String fileName) {
		// 声明Properties
		ps = new Properties();
		// 循环加载文件
//		for (String name : StringUtil.split(fileName, StringConstants.COMMA)) {
		try (InputStream in = R.loadResource(fileName + ".properties")) {
			// 有配置文件加载
			if (in != null) {
				ps.load(in);
//				System.getProperties().putAll(ps);
			}
		} catch (IOException e) {
		}
//		}
	}

	/**
	 * 构造参数
	 * 
	 * @param ps
	 */
	public ConfigProperties(Properties ps) {
		this.ps = ps;
	}

	@Override
	public String getString(String key) {
		return U.S.trim(ps.getProperty(key));
	}

	@Override
	public boolean exists(String key) {
		return ps.containsKey(key);
	}

	@Override
	public String toString() {
		return "Config [ps=" + ps + "]";
	}
}
