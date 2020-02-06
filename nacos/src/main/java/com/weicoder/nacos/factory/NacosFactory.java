package com.weicoder.nacos.factory;

import com.weicoder.nacos.NacosConfig;
import com.weicoder.nacos.NacosNaming;

/**
 * Nacos 工厂
 * 
 * @author wudi
 */
public final class NacosFactory {
	/**
	 * 获得NacosConfig
	 * 
	 * @return      NacosConfig
	 */
	public static NacosConfig getConfig() {
		return NacosConfigFactory.FACTORY.getInstance();
	}
	
	/**
	 * 获得NacosConfig
	 * 
	 * @param  name 名称
	 * @return      NacosConfig
	 */
	public static NacosConfig getConfig(String name) {
		return NacosConfigFactory.FACTORY.getInstance(name);
	}
	
	/**
	 * 获得NacosNaming
	 *  
	 * @return      NacosNaming
	 */
	public static NacosNaming getNaming() {
		return NacosNamingFactory.FACTORY.getInstance();
	}

	/**
	 * 获得NacosNaming
	 * 
	 * @param  name 名称
	 * @return      NacosNaming
	 */
	public static NacosNaming getNaming(String name) {
		return NacosNamingFactory.FACTORY.getInstance(name);
	}

	private NacosFactory() {
	}
}
