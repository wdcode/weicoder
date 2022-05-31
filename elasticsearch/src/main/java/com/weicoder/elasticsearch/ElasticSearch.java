package com.weicoder.elasticsearch;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import com.weicoder.common.U;
import com.weicoder.common.W;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.json.JsonEngine;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;

import com.weicoder.elasticsearch.annotation.Index;
import com.weicoder.elasticsearch.params.ElasticSearchParams;

/**
 * ElasticSearch client
 * 
 * @author wudi
 */
public class ElasticSearch {
	// RestHighLevelClient
//	private RestHighLevelClient client;
	private ElasticsearchClient client;

	/**
	 * 构造
	 * 
	 * @param name
	 */
	public ElasticSearch(String name) {
		RestClient rc = RestClient.builder(Lists.toArray(ElasticSearchParams.getHosts(name).stream().map(HttpHost::create).collect(Collectors.toList())))
				.build();
//		client = new RestHighLevelClient(rc);
		client = new ElasticsearchClient(new RestClientTransport(rc, new JacksonJsonpMapper()));
	}

	/**
	 * 创建索引
	 * 
	 * @param <E>
	 * @param index 索引对象
	 * @throws IOException
	 */
	public void create(Index index) {
		try {
			// 创建索引
//			CreateIndexRequest request =  new CreateIndexRequest(getIndexName(index));
			CreateIndexRequest request = CreateIndexRequest
					.of(r -> r.index(getIndexName(index)).settings(s -> s.numberOfRoutingShards(index.shards()).numberOfReplicas(index.replica())));
			// 创建索引对象类型
//			Map<String, Object> properties = Maps.newMap();
			Map<String, Property> properties = Maps.newMap();
			// 获得所有索引字段 根据数据类型设置
//			BeanUtil.getFields(index.getClass())
//					.forEach(f -> properties.put(f.getName(), Maps.newMap("type", f.getType())));  
			BeanUtil.getFields(index.getClass()).forEach(f -> properties.put(f.getName(), Property.of(p -> p.searchAsYouType(v -> v.searchAnalyzer(f.getName())))));//v.name(f.getName())
			// f.getType())));xxxxxxx
//			request.mapping(JsonEngine.toJson(Maps.newMap("properties", properties)));//, XContentType.JSON
			request.mappings().properties().putAll(properties);
			// 创建索引
//			client.indices().create(request, RequestOptions.DEFAULT);
			client.indices().create(c -> c.index("products"));
		} catch (IOException e) {
			Logs.error(e);
		}
	}

	/**
	 * 删除索引
	 * 
	 * @param index
	 */
	public void del(Index index) {
		// 删除索引对象
		try {
			client.indices().delete(new DeleteIndexRequest.Builder().index(getIndexName(index)).build());
		} catch (IOException e) {
			Logs.error(e);
		}
	}

	/**
	 * 添加索引
	 * 
	 * @param index 索引
	 */
	public void add(Index... index) {
		String name = getIndexName(index[0]);
		String id = index[0].id();
		Lists.newList(index).forEach(i -> {
			try {
				client.index(new IndexRequest.Builder<>().index(name).id(W.C.toString(BeanUtil.getFieldValue(i, id))).document(JsonEngine.toJson(i)).build())
						.result();
			} catch (IOException e) {
				Logs.error(e);
			}
		});
	}

	/**
	 * 查询全部数据
	 * 
	 * @param <E>
	 * @param cls
	 * @return
	 */
	public <E extends Index> List<E> all(Class<E> cls) {
		// 执行搜索,向ES发起http请求
		List<E> list = Lists.newList();
		try {
			// 搜索请求对象
			SearchRequest searchRequest = SearchRequest.of(r -> r.index(getIndexName(U.C.newInstance(cls))).query(QueryBuilders.matchAll().build()._toQuery())
					.source(new SourceConfig.Builder().build()));
			// 搜索全部对象
			client.search(searchRequest, cls).hits().hits().forEach(h -> list.add(BeanUtil.copy(h.fields(), cls)));
		} catch (Exception e) {
			Logs.error(e);
		}
		// 返回查询列表
		return list;
	}

	/**
	 * 根据条件查询
	 * 
	 * @param <E>
	 * @param cls   索引类
	 * @param name  查询字段
	 * @param value 查询value
	 * @param start 开始数
	 * @param size  返回数量
	 * @return 列表
	 */
	public <E extends Index> List<E> query(Class<E> cls, String name, String value, int start, int size) {
		// 执行搜索,向ES发起http请求
		List<E> list = Lists.newList();
		try {
			// 搜索源构建对象
			SearchRequest.Builder ssb = new SearchRequest.Builder();
//			SearchSourceBuilder ssb = new SearchSource.Builder();
////			search
//			SourceConfig.Builder ssb = new SourceConfig.Builder();
			// 设置分页参数
			if (start > -1 && size > 0) {
				ssb.from(start).size(size);
			}
			// 搜索方式
			ssb.query(QueryBuilders.term().queryName(name).field(value).build()._toQuery());
			// 搜索
			client.search(SearchRequest.of(r -> r.index(getIndexName(U.C.newInstance(cls))).source(ssb.build().source())), cls).hits().hits()
					.forEach(h -> list.add(BeanUtil.copy(h.fields(), cls)));
		} catch (Exception e) {
			Logs.error(e);
		}
		// 返回列表
		return list;

	}

	private String getIndexName(Index index) {
		return U.E.isEmpty(index.name()) ? index.getClass().getSimpleName() : index.name();
	}
}
