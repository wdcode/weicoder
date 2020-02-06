package com.weicoder.solr.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.solr.Solr;

/**
 * Solr 工厂
 * 
 * @author wudi
 */
public class SolrFactory extends FactoryKey<String, Solr> {
	private final static SolrFactory FACTORY = new SolrFactory();

	/**
	 * 获得Solr
	 * 
	 * @return      Solr
	 */
	public static Solr getSolr() {
		return FACTORY.getInstance();
	}
	
	/**
	 * 获得Solr
	 * 
	 * @param  name 名称
	 * @return      Solr
	 */
	public static Solr getSolr(String name) {
		return FACTORY.getInstance(name);
	}

	@Override
	public Solr newInstance(String key) {
		return new Solr(key);
	}

	private SolrFactory() {
	}
}
