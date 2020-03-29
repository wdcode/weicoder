package com.weicoder.nacos.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.nacos.NacosNaming;

/**
 * Nacos 工厂
 * 
 * @author wudi
 */
final class NacosNamingFactory extends FactoryKey<String, NacosNaming> {
	final static NacosNamingFactory FACTORY = new NacosNamingFactory();

	@Override
	public NacosNaming newInstance(String key) {
		return new NacosNaming(key);
	}

	private NacosNamingFactory() {
	}
}
