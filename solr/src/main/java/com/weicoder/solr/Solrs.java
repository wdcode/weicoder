package com.weicoder.solr;

import java.util.Collection;
import java.util.List;
 
import com.weicoder.solr.factory.SolrFactory;

/**
 * @author wudi
 */
public final class Solrs {
	private final static Solr SOLR = SolrFactory.getSolr();
   
	/**
	 * 更新索引数据
	 * 
	 * @param  list solr数据
	 * @return      更新数量
	 */
	public static int update(Object... obj) {
		return SOLR.update(obj);
	}

	/**
	 * 更新索引数据
	 * 
	 * @param  list solr数据
	 * @return      更新数量
	 */
	public static int update(Collection<?> list) {
		return SOLR.update(list);
	}

	/**
	 * 查询数据
	 * 
	 * @param  <E>
	 * @param  q     查询语句
	 * @param  cls   查询结果类
	 * @param  start 开始
	 * @param  rows  获得行数
	 * @return       列表
	 */
	public static <E> List<E> query(String q, Class<E> cls, int start, int rows) {
		return SOLR.query(q, cls, start, rows);
	}

	/**
	 * 删除索引
	 * 
	 * @param  ids 索引对象
	 * @return
	 */
	public static int del(String... ids) {
		return SOLR.del(ids);
	}

	private Solrs() {
	}
}
