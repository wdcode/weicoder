package com.weicoder.solr;

import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.solr.params.SolrParams;

/**
 * apache solr client
 * 
 * @author wudi
 */
public class Solr {
	// SolrClient
	private SolrClient client;

	/**
	 * 构造
	 * 
	 * @param name
	 */
	public Solr(String name) {
		client = new Builder(SolrParams.getUrl(name)).build();
	}

	/**
	 * 更新索引数据
	 * 
	 * @param  list solr数据
	 * @return      更新数量
	 */
	public int update(Collection<?> list) {
		try {
			return EmptyUtil.isEmpty(list) ? 0 : client.addBeans(list).getStatus();
//			client.optimize();
//			return client.commit().getStatus();
		} catch (Exception e) {
			Logs.error(e);
			return -1;
		}
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
	public <E> List<E> query(String q, Class<E> cls, int start, int rows) {
		List<E> list = Lists.newList();
		try {
			client.query(new SolrQuery(q).setStart(start).setRows(rows)).getResults()
					.forEach(s -> list.add(BeanUtil.copy(s.getFieldValueMap(), cls)));
		} catch (Exception e) {
			Logs.error(e);
		}
		return list;
	}

	/**
	 * 删除索引
	 * 
	 * @param  ids 索引对象
	 * @return
	 */
	public int del(String... ids) {
		return del(Lists.newList(ids));
	}

	/**
	 * 删除索引
	 * 
	 * @param  ids 索引对象
	 * @return
	 */
	public int del(List<String> ids) {
		try {
			return EmptyUtil.isEmpty(ids) ? 0 : client.deleteById(ids).getStatus();
//			client.optimize();
//			return client.commit().getStatus();
		} catch (Exception e) {
			Logs.error(e);
			return -1;
		}
	}
}
