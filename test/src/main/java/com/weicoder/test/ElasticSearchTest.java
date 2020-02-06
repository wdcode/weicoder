package com.weicoder.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions; 
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.weicoder.common.lang.C;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.core.json.JsonEngine;

public class ElasticSearchTest {

	private final static String index = "test";

	public static void main(String[] args) throws Exception {
		System.out.println(SearchBean.class.getCanonicalName());
		System.out.println(SearchBean.class.getName());
		System.out.println(SearchBean.class.getSimpleName());
//		String[] ips = {"172.18.77.135:9200"};
//		HttpHost[] httpHosts = new HttpHost[ips.length];
//		for (int i = 0; i < ips.length; i++) {
//			httpHosts[i] = HttpHost.create(ips[i]);
//		}
//		RestClientBuilder builder = RestClient.builder(httpHosts); 
//		RestHighLevelClient client = new RestHighLevelClient(builder);

		// createIndex
//		createIndex(client);

		// add
//		add(client);

		// all
//		all(client, -1, -1);
//
//		// get
//		query(client,"nickname", "呵", -1, -1);
//		query(client,"uid", "11", -1, -1);

//		// delete
//		del(client);
//
//		// page
//		page(client);
	}

	// 创建索引
	public static void createIndex(RestHighLevelClient client) throws IOException {
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 0));
		buildIndexMapping(request);
		client.indices().create(request, RequestOptions.DEFAULT);
	}

	// 设置index的mapping
	public static void buildIndexMapping(CreateIndexRequest request) {
		Map<String, Object> uid = new HashMap<>();
		uid.put("type", "long");
		Map<String, Object> nickname = new HashMap<>();
		nickname.put("type", "text");
		Map<String, Object> properties = new HashMap<>();
		properties.put("uid", uid);
		properties.put("nickname", nickname);
		Map<String, Object> book = new HashMap<>();
		book.put("properties", properties);
		request.mapping(JsonEngine.toJson(book), XContentType.JSON);
	}

	public static void del(RestHighLevelClient client) throws Exception {
		// 删除索引对象
		DeleteIndexRequest del = new DeleteIndexRequest(index);
//		DeleteRequest d = new DeleteRequest(index);
//		d.id(id);
		// 操作索引的客户端
		IndicesClient indices = client.indices();
//		client.delete(d, RequestOptions.DEFAULT);
		// 执行删除索引
		AcknowledgedResponse delete = indices.delete(del, RequestOptions.DEFAULT);
		// 得到响应
		boolean acknowledged = delete.isAcknowledged();
		System.out.println(acknowledged);

	}

	public static void add(RestHighLevelClient client) {
		// 文档内容
		// 准备json数据
		List<Map<String, Object>> list = Lists.newList();
		list.add(Maps.newMap(new String[]{"id","uid", "nickname"}, new Object[]{1, 11, "呵呵哒"}));
		list.add(Maps.newMap(new String[]{"id","uid", "nickname"}, new Object[]{2, 12, "呵呵呵"}));
		list.add(Maps.newMap(new String[]{"id","uid", "nickname"}, new Object[]{3, 13, "呵哒呵"}));
		list.add(Maps.newMap(new String[]{"id","uid", "nickname"}, new Object[]{4, 14, "哈哈哒"}));
		list.add(Maps.newMap(new String[]{"id","uid", "nickname"}, new Object[]{5, 15, "哈哈哈"}));
		list.add(Maps.newMap(new String[]{"id","uid", "nickname"}, new Object[]{6, 16, "呵哈呵"}));

		list.forEach(m -> {
			try {// .id(C.toString(m.get("id")))
				System.out.println(client.index(new IndexRequest(index).id(C.toString(m.remove("id"))).source(m), RequestOptions.DEFAULT).getResult());
			} catch (IOException e) {
				System.out.println(e);
			}
		});
	}

	public static void all(RestHighLevelClient client, int page, int size) throws Exception {
		// 搜索请求对象
		SearchRequest searchRequest = new SearchRequest(index);
		// 搜索源构建对象
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 计算出记录起始下标
		if (page > -1 && size > 0) {
			int from = (page - 1) * size;
			searchSourceBuilder.from(from);// 起始记录下标，从0开始
			searchSourceBuilder.size(size);// 每页显示的记录数
		}
		// 搜索方式
		// matchAllQuery搜索全部
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		// 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
//		searchSourceBuilder.fetchSource(new String[]{"uid", "nickname"}, new String[]{});
		// 向搜索请求对象中设置搜索源
		searchRequest.source(searchSourceBuilder);
		// 执行搜索,向ES发起http请求
		List<Map<String, Object>> list = Lists.newList();
		client.search(searchRequest, RequestOptions.DEFAULT).getHits().forEach(h -> list.add(h.getSourceAsMap()));
		System.out.println(list);
	}

	public static void query(RestHighLevelClient client,String f, String n, int page, int size) throws Exception {
		// 搜索请求对象
		SearchRequest searchRequest = new SearchRequest(index);
		// 搜索源构建对象
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 设置分页参数
		if (page > -1 && size > 0) {
			int from = (page - 1) * size;
			searchSourceBuilder.from(from);// 起始记录下标，从0开始
			searchSourceBuilder.size(size);// 每页显示的记录数
		}
		// 添加排序
//				searchSourceBuilder.sort("studymodel", SortOrder.DESC);
//				searchSourceBuilder.sort("price", SortOrder.ASC);
		// 搜索方式
		// termQuery
		searchSourceBuilder.query(QueryBuilders.termQuery(f, n));
		// 向搜索请求对象中设置搜索源
		searchRequest.source(searchSourceBuilder);
		// 执行搜索,向ES发起http请求
		List<String> list = Lists.newList();
		client.search(searchRequest, RequestOptions.DEFAULT).getHits().forEach(h -> list.add(h.getSourceAsString()));
		System.out.println(list);

	}
}
