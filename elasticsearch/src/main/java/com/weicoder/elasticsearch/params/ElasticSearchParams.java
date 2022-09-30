package com.weicoder.elasticsearch.params;

import java.util.List;

import com.weicoder.common.config.Config;
import com.weicoder.common.lang.W;
import com.weicoder.common.params.P;

/**
 * ElasticSearch参数
 * 
 * @author WD
 */
public final class ElasticSearchParams {
	private ElasticSearchParams() {
	}

	/** kafka使用 */
	public final static String	PREFIX	= "es";
	// Properties配置
	private final static Config	CONFIG	= P.getConfig(PREFIX);
	private final static String	HOSTS	= "hosts";

	/**
	 * 获得ElasticSearch服务器
	 * 
	 * @param name 名字
	 * @return 服务器
	 */
	public static List<String> getHosts(String name) {
		return CONFIG.getList(P.getKey(name, HOSTS), P.getList(P.getKey(PREFIX, name, HOSTS), W.L.empty()));
	}
}
