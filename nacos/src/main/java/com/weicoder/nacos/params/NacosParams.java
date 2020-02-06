package com.weicoder.nacos.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.params.Params;

/**
 * Nacos配置参数
 * 
 * @author wudi
 */
public final class NacosParams {
	/** kafka使用 */
	public final static String PREFIX = "nacos";
	// Properties配置
	private final static Config CONFIG = ConfigFactory.getConfig(PREFIX);
	private final static String ADDR   = "addr";

	/**
	 * 根据名称获取地址
	 * 
	 * @param  name 名称
	 * @return
	 */
	public static String getAddr(String name) {
		return CONFIG.getString(Params.getKey(name, ADDR), Params.getString(Params.getKey(PREFIX, name, ADDR)));
	}

	private NacosParams() {
	}
}
