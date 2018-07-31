package com.weicoder.common.config;

import com.weicoder.common.factory.FactoryKey;

/**
 * 读取配置工厂类
 * @author WD
 */
public final class ConfigFactory extends FactoryKey<String, Config> {
	// 配置工厂
	private final static ConfigFactory FACTORY = new ConfigFactory();

	/**
	 * 获得配置
	 * @param key 键
	 * @return Config
	 */
	public static Config getConfig(String key) {
		return FACTORY.getInstance(key);
	}

	@Override
	public Config newInstance(String key) {
		return new Config("%s-test.properties,%s.properties".replaceAll("%s", key));
	}

	private ConfigFactory() {}
}