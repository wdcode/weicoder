package com.weicoder.nacos.config;

import com.weicoder.common.U;
import com.weicoder.common.config.BaseConfig;
import com.weicoder.nacos.NacosConfig;
import com.weicoder.nacos.factory.NacosFactory;

/**
 * nacos配置中心实现
 * 
 * @author wdcode
 *
 */
public class ConfigNacos extends BaseConfig {
	// nacos配置
	private NacosConfig config;

	/**
	 * 构造
	 * 
	 * @param name 服务器地址
	 */
	public ConfigNacos(String name) {
		config = NacosFactory.getConfig(name);
	}

	@Override
	public String getString(String key) {
		return U.S.trim(config.get(key));
	}
}
