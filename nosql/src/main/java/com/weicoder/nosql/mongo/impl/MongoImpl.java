package com.weicoder.nosql.mongo.impl;

import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.zip.ZipEngine;
import com.weicoder.nosql.mongo.Mongo;
import com.weicoder.nosql.params.MongoParams;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * MongoDB Dao 实现
 * @author WD
 */
public final class MongoImpl implements Mongo {
	// MongoDB 主键常量
	private final static String						ID	= "_id";
	// Mongo 客户端
	private MongoClient								client;
	// MongoDB
	private MongoDatabase							db;
	// 数据集合对象
	private MongoCollection<Document>				dbc;
	// 数据集合列表
	private Map<String, MongoCollection<Document>>	dbcs;

	/**
	 * 构造方法
	 * @param key 键
	 */
	public MongoImpl(String key) {
		try {
			// Mongo 客户端
			Builder builder = MongoClientOptions.builder();
			builder.connectionsPerHost(100);
			builder.threadsAllowedToBlockForConnectionMultiplier(100);
			// 实例化客户端
			client = new MongoClient(new ServerAddress(MongoParams.getHost(key), MongoParams.getPort(key)), builder.build());
			// 如果库存在
			db = client.getDatabase(MongoParams.getDB(key));
			dbc = db.getCollection(MongoParams.getCollection(key));
			dbcs = Maps.getConcurrentMap();
		} catch (Exception e) {
			Logs.error(e);
			// throw new RuntimeException(e);
		}
	}

	/**
	 * 插入数据
	 * @param maps 数据对象
	 */
	public void insert(String name, Map<String, Object> maps) {
		getCollection(name).insertOne(new Document(maps));
	}

	/**
	 * 插入数据
	 * @param maps 数据对象
	 */
	@SuppressWarnings("unchecked")
	public void insert(String name, Map<String, Object>... maps) {
		// 声明Document列表
		List<Document> documents = Lists.getArrayList(maps.length);
		// 循环map数组
		for (int i = 0; i < maps.length; i++) {
			// 实例化新Document对象
			documents.add(new Document(getMap(maps[i])));
		}
		// 插入数据
		getCollection(name).insertMany(documents);
	}

	/**
	 * 获得数据总数量
	 * @return 数量
	 */
	public long count(String name) {
		return getCollection(name).count();
	}

	/**
	 * 根据查询条件获得数量
	 * @param query 查询条件
	 * @return 数量
	 */
	public long count(String name, Map<String, Object> query) {
		return getCollection(name).count(new BasicDBObject(query));
	}

	/**
	 * 创建索引
	 * @param keys 索引键
	 */
	public void createIndex(String name, Map<String, Object> keys) {
		getCollection(name).createIndex(new BasicDBObject(keys));
	}

	/**
	 * 删除索引
	 * @param name 索引名
	 */
	public void dropIndex(String name, String index) {
		getCollection(name).dropIndex(index);
	}

	/**
	 * 删除索引
	 * @param keys 索引键
	 */
	public void dropIndex(String name, Map<String, Object> keys) {
		getCollection(name).dropIndex(new BasicDBObject(keys));
	}

	/**
	 * 删除所以索引
	 */
	public void dropIndexes(String name) {
		getCollection(name).dropIndexes();
	}

	/**
	 * 删除数据
	 */
	@SuppressWarnings("unchecked")
	public void delete(String name, Map<String, Object>... maps) {
		// 获得数据集合
		MongoCollection<Document> dbc = getCollection(name);
		// 循环map数组
		for (int i = 0; i < maps.length; i++) {
			// 删除对象
			dbc.deleteOne(new BasicDBObject(getMap(maps[i])));
		}
	}

	/**
	 * 删除数据
	 */
	public void delete(String name, Map<String, Object> data) {
		getCollection(name).deleteOne(new BasicDBObject(getMap(data)));
	}

	/**
	 * 根据query参数,更新obj值
	 * @param query 条件值
	 * @param obj 要更新的值
	 */
	public void update(String name, Map<String, Object> query, Map<String, Object> obj) {
		getCollection(name).updateOne(new BasicDBObject(query), new BasicDBObject(obj));
	}

	/**
	 * 获得所有数据
	 * @return 数据列表
	 */
	public List<Map<String, Object>> query(String name) {
		return query(name, null);
	}

	/**
	 * 根据条件获得数据
	 * @param query 查询条件
	 * @return 数据列表
	 */
	public List<Map<String, Object>> query(String name, Map<String, Object> query) {
		return query(name, query, 0, 0);
	}

	/**
	 * 根据条件获得 start到end的数据
	 * @param query 查询条件
	 * @param start 开始条数
	 * @param end 结束条数
	 * @return 数据列表
	 */
	public List<Map<String, Object>> query(String name, Map<String, Object> query, int start, int end) {
		// 获得数据库游标
		FindIterable<Document> iterable = getCollection(name).find(EmptyUtil.isEmpty(query) ? new BasicDBObject() : new BasicDBObject(query));
		// 设置游标开始位置
		iterable.skip(start);
		// 设置限定数量
		iterable.limit(end - start);
		// 获得列表
		List<Map<String, Object>> list = Lists.getList();
		// 设置游标开始位置

		// 循环游标
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) {
			// 添加到列表中
			list.add(toMap(cursor.next()));
		}
		// 返回列表
		return list;
	}

	/**
	 * 如果DBObject为空返回空Map 不为空返回DBObject.toMap();
	 * @param object DBObject
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> toMap(Document object) {
		return (Map<String, Object>) (EmptyUtil.isEmpty(object) ? Maps.getMap() : object);
	}

	/**
	 * 更换id key 键为主键_id
	 * @param map 数据Map
	 * @return 更改完Map
	 */
	private Map<String, Object> getMap(Map<String, Object> map) {
		// 判断_id为空 赋值
		if (!EmptyUtil.isEmpty(map)) {
			// 获得ID
			Object key = map.get(ID);
			// 判断如果为空获得 id键
			key = EmptyUtil.isEmpty(key) ? map.get(StringConstants.KEY) : key;
			// 设置主键
			map.put(ID, key);
		}
		// 返回Map
		return map;
	}

	@Override
	public boolean set(String key, Object value) {
		// 获得Map
		Map<String, Object> map = Maps.getMap();
		// 设置键值
		map.put(ID, key);
		map.put(StringConstants.VALUE, value);
		// 添加数据
		insert(StringConstants.EMPTY, map);
		// 返回成功
		return true;
	}

	@Override
	public Object get(String key) {
		return toMap(dbc.find(new BasicDBObject(key, null)).first()).get(StringConstants.VALUE);
	}

	@Override
	public void remove(String... key) {
		// 获得数据集合
		MongoCollection<Document> dbc = getCollection(StringConstants.EMPTY);
		// 循环删除
		for (String k : key) {
			dbc.deleteOne(new BasicDBObject(ID, k));
		}
	}

	@Override
	public long count(String name, Object key) {
		return count(name, Maps.getMap(ID, key));
	}

	@Override
	public Map<String, Object> get(String name, Object key) {
		return toMap(getCollection(name).find(new BasicDBObject(ID, key)).first());
	}

	@Override
	public Map<String, Object> get(String name, Map<String, Object> query) {
		return toMap(getCollection(name).find(new BasicDBObject(query)).first());
	}

	@Override
	public boolean exists(String key) {
		return getCollection(StringConstants.EMPTY).count(new BasicDBObject(ID, key)) > 0;
	}

	@Override
	public boolean append(String key, Object value) {
		return false;
	}

	/**
	 * 压缩值 当值能压缩时才压缩
	 * @param key 键
	 * @param value 值
	 */
	public final boolean compress(String key, Object value) {
		return set(key, ZipEngine.compress(value));
	}

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * @param key 键
	 * @return 值
	 */
	public final byte[] extract(String key) {
		return ZipEngine.extract(get(key));
	}

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	public Object[] get(String... keys) {
		// 声明列表
		Object[] objs = new Object[keys.length];
		// 循环解压数据
		for (int i = 0; i < keys.length; i++) {
			objs[i] = get(keys[i]);
		}
		// 返回列表
		return objs;
	}

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	public List<byte[]> extract(String... keys) {
		// 声明列表
		List<byte[]> list = Lists.getList(keys.length);
		// 循环解压数据
		for (Object o : get(keys)) {
			list.add(ZipEngine.extract(o));
		}
		// 返回列表
		return list;
	}

	/**
	 * 获得数据集合
	 * @param name 集合名
	 * @return 数据集合
	 */
	private MongoCollection<Document> getCollection(String name) {
		// 获得数据集合
		MongoCollection<Document> dbc = EmptyUtil.isEmpty(name) ? this.dbc : dbcs.get(name);
		// 如果数据集合为空
		if (dbc == null) {
			dbcs.put(name, dbc = db.getCollection(name));
		}
		// 返回集合
		return dbc;
	}
}