package com.weicoder.nacos.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.nacos.NacosConfig;

/**
 * NacosConfig 工厂
 * 
 * @author wudi
 */
final class NacosConfigFactory extends FactoryKey<String, NacosConfig> {
	final static NacosConfigFactory FACTORY = new NacosConfigFactory();

	@Override
	public NacosConfig newInstance(String key) {
		return new NacosConfig(key);
	}

	private NacosConfigFactory() {
	}
}
