package com.weicoder.common.params;

import java.util.List;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.lang.Lists;

/**
 * ElasticSearch参数
 * 
 * @author WD
 */
public final class ElasticSearchParams {
	/** kafka使用 */
	public final static String PREFIX = "es";
	// Properties配置
	private final static Config CONFIG = ConfigFactory.getConfig(PREFIX);
	private final static String HOSTS  = "hosts";

	/**
	 * 获得ElasticSearch服务器
	 * 
	 * @param  name 名字
	 * @return      服务器
	 */
	public static List<String> getHosts(String name) {
		return CONFIG.getList(Params.getKey(name, HOSTS),
				Params.getList(Params.getKey(PREFIX, name, HOSTS), Lists.emptyList()));
	}

	private ElasticSearchParams() {
	}
}
