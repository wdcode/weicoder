package com.weicoder.elasticsearch.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.elasticsearch.ElasticSearch;

/**
 * ElasticSearch工厂
 * 
 * @author wudi
 */
public class ElasticSearchFactory extends FactoryKey<String, ElasticSearch> {
	private final static ElasticSearchFactory FACTORY = new ElasticSearchFactory();

	/**
	 * 根据名称获得ElasticSearch
	 * 
	 * @return
	 */
	public static ElasticSearch getElasticSearch() {
		return FACTORY.getInstance();
	}

	/**
	 * 根据名称获得ElasticSearch
	 * 
	 * @param  name
	 * @return
	 */
	public static ElasticSearch getElasticSearch(String name) {
		return FACTORY.getInstance(name);
	}

	@Override
	public ElasticSearch newInstance(String key) {
		return new ElasticSearch(key);
	}

	private ElasticSearchFactory() {
	}
}
