package com.weicoder.common.config;

import java.util.Properties;

import com.weicoder.common.factory.FactoryInterface; 

/**
 * 读取配置工厂类
 * 
 * @author WD
 */
public final class ConfigFactory extends FactoryInterface<Config> {
	// 配置工厂
	private final static ConfigFactory FACTORY = new ConfigFactory();

	/**
	 * 获得配置
	 * 
	 * @param key 键
	 * @return Config
	 */
	public static Config getConfig(String key) {
		return FACTORY.getInstance(key);
	}

	/**
	 * 使用Properties加载Config
	 * 
	 * @param ps Properties
	 * @return Config
	 */
	public static Config getConfig(Properties ps) {
		return new ConfigProperties(ps);
	}

//	@Override
//	public Config newInstance(String key) {
//		return new ConfigProperties(key);
//	}

	private ConfigFactory() {
	}
}