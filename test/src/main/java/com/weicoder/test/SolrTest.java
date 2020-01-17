package com.weicoder.test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder; 
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList; 

import com.weicoder.common.lang.C;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;

public class SolrTest {
	private final static String     SEARCH_URL = "http://192.168.0.200:8983/solr/witch"; 
	private final static SolrClient CLIENT     = new Builder(SEARCH_URL).build();

	public static void main(String[] args) throws SolrServerException, IOException {
//		SolrInputDocument doc = new SolrInputDocument("id","uid","nickname","q");
//		CLIENT.add(doc);
//		CLIENT.optimize();
//		CLIENT.commit(); 
		
//		List<SearchBean> list = Lists.newList();
//		list.add(new SearchBean(1, 11, "呵呵哒"));
//		list.add(new SearchBean(2, 12, "呵呵呵"));
//		list.add(new SearchBean(3, 13, "呵哒呵"));
//		list.add(new SearchBean(4, 14, "哈哈哒"));
//		list.add(new SearchBean(5, 15, "哈哈哈"));
//		System.out.println(update(list).getStatus());

//		System.out.println(del(Lists.newList("1","2","3")).getStatus());

		System.out.println(Lists.toString(query("哈哈", 0, 10)));
	}

	public static List<SearchBean> query(String key, int start, int limit) {
		String query = "nickname:" + key;
		List<SearchBean> dataList = Lists.newList();

		try {
			SolrQuery params = new SolrQuery();
			params.set("q", query);

			params.set("start", start);
			params.set("rows", limit);
			QueryResponse resp = CLIENT.query(params);
			SolrDocumentList docsList = resp.getResults();
			// logger.debug("找到个数：{}", dataNum);
			for (SolrDocument doc : docsList) {
				dataList.add(new SearchBean(C.toLong(doc.getFirstValue("id").toString()), C.toLong(doc.getFirstValue("uid").toString()), doc.getFieldValue("nickname").toString()));
			}
		} catch (Exception e) {
			Logs.error(e);
		}
		return dataList;
	}

	public static <T> UpdateResponse del(List<String> list) {
		if (EmptyUtil.isEmpty(list)) {
			return null;
		}
		UpdateResponse rs = null;
		try {
			CLIENT.deleteById(list);
			// logger.debug("更新索引花费时间:{}", resp.getElapsedTime());
			CLIENT.optimize();
			rs = CLIENT.commit();
		} catch (Exception e) {
			Logs.error("更新索引失败，e={}", e.getMessage());
		} finally {
			try {
				CLIENT.close();
			} catch (IOException e) {
			}
		}
		return rs;
	}

	/**
	 * 说明：更新solr数据
	 */
	public static <T> UpdateResponse update(Collection<T> list) {
		if (EmptyUtil.isEmpty(list)) {
			return null;
		}
		UpdateResponse rs = null;
		try {
			CLIENT.addBeans(list);
			// logger.debug("更新索引花费时间:{}", resp.getElapsedTime());
			CLIENT.optimize();
			rs = CLIENT.commit();
		} catch (Exception e) {
			Logs.error("更新索引失败，e={}", e.getMessage());
		}
		return rs;
	}
}
