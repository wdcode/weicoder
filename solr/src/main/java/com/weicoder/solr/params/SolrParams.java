package com.weicoder.solr.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.params.Params;

/**
 * Solr配置参数
 * 
 * @author wudi
 */
public final class SolrParams {
	/** kafka使用 */
	public final static String PREFIX = "solr";
	// Properties配置
	private final static Config CONFIG = ConfigFactory.getConfig(PREFIX);
	private final static String URL    = "url";

	/**
	 * 根据名称获取地址
	 * 
	 * @param  name 名称
	 * @return
	 */
	public static String getUrl(String name) {
		return CONFIG.getString(Params.getKey(name, URL), Params.getString(Params.getKey(PREFIX, name, URL)));
	}

	private SolrParams() {
	}
}
