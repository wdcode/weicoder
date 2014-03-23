package com.weicoder.core.nosql.mongo.impl;

import java.util.List;
import java.util.Map;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.nosql.base.BaseNoSQL;
import com.weicoder.core.nosql.mongo.Mongo;
import com.weicoder.core.params.MongoParams;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;

/**
 * MongoDB Dao 实现
 * @author WD
 * @since JDK7
 * @version 1.0 2010-11-21
 */
public final class MongoImpl extends BaseNoSQL implements Mongo {
	// MongoDB 主键常量
	private final static String			ID	= "_id";
	// Mongo 客户端
	private MongoClient					client;
	// MongoDB
	private DB							db;
	// 数据集合对象
	private DBCollection				dbc;
	// 数据集合列表
	private Map<String, DBCollection>	dbcs;

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
			// 实例化DB
			// 如果库存在
			boolean is = client.getDatabaseNames().contains(MongoParams.getDB(key));
			db = client.getDB(MongoParams.getDB(key));
			// 库存在
			if (is) {
				// 验证用户
				db.authenticate(MongoParams.getUser(key), MongoParams.getPassword(key).toCharArray());
			} else {
				// 不存在 添加用户
				db.addUser(MongoParams.getUser(key), MongoParams.getPassword(key).toCharArray());
			}
			dbc = db.getCollection(MongoParams.getCollection(key));
			dbcs = Maps.getConcurrentMap();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 插入数据
	 * @param maps 数据对象
	 */
	public void insert(String name, Map<String, Object> maps) {
		getCollection(name).insert(new BasicDBObject(getMap(maps)));
	}

	/**
	 * 插入数据
	 * @param maps 数据对象
	 */
	public void insert(String name, Map<String, Object>... maps) {
		// 声明DBObject数组
		DBObject[] objs = new DBObject[maps.length];
		// 循环map数组
		for (int i = 0; i < maps.length; i++) {
			// 实例化新DBObject对象
			objs[i] = new BasicDBObject(getMap(maps[i]));
		}
		// 插入数据
		getCollection(name).insert(objs);
	}

	/**
	 * 获得数据总数量
	 * @return 数量
	 */
	public long count(String name) {
		return getCollection(name).getCount();
	}

	/**
	 * 根据查询条件获得数量
	 * @param query 查询条件
	 * @return 数量
	 */
	public long count(String name, Map<String, Object> query) {
		return getCollection(name).getCount(new BasicDBObject(query));
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
	public void delete(String name, Map<String, Object>... maps) {
		// 获得数据集合
		DBCollection dbc = getCollection(name);
		// 循环map数组
		for (int i = 0; i < maps.length; i++) {
			// 删除对象
			dbc.remove(new BasicDBObject(getMap(maps[i])));
		}
	}

	/**
	 * 删除数据
	 */
	public void delete(String name, Map<String, Object> data) {
		getCollection(name).remove(new BasicDBObject(getMap(data)));
	}

	/**
	 * 根据query参数,更新obj值
	 * @param query 条件值
	 * @param obj 要更新的值
	 */
	public void update(String name, Map<String, Object> query, Map<String, Object> obj) {
		update(name, query, obj, true, true);
	}

	/**
	 * 根据query参数,更新obj值
	 * @param query 条件值
	 * @param obj 要更新的值
	 * @param upsert 没有查询到直接添加新文档
	 * @param multi 是否多列更新
	 */
	public void update(String name, Map<String, Object> query, Map<String, Object> obj, boolean upsert, boolean multi) {
		getCollection(name).update(new BasicDBObject(query), new BasicDBObject(obj), upsert, multi);
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
		DBCursor cursor = getCollection(name).find(EmptyUtil.isEmpty(query) ? new BasicDBObject() : new BasicDBObject(query));
		// 设置游标开始位置
		cursor.skip(start);
		// 设置限定数量
		cursor.limit(end - start);
		// 获得列表
		List<Map<String, Object>> list = Lists.getList();
		// 设置游标开始位置

		// 循环游标
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
	private Map<String, Object> toMap(DBObject object) {
		return EmptyUtil.isEmpty(object) ? Maps.getMap() : object.toMap();
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
		return toMap(dbc.findOne(key)).get(StringConstants.VALUE);
	}

	@Override
	public void remove(String... key) {
		// 获得数据集合
		DBCollection dbc = getCollection(StringConstants.EMPTY);
		// 循环删除
		for (String k : key) {
			dbc.remove(new BasicDBObject(ID, k));
		}
	}

	@Override
	public long count(String name, Object key) {
		return count(name, Maps.getMap(ID, key));
	}

	@Override
	public Map<String, Object> get(String name, Object key) {
		return toMap(getCollection(name).findOne(new BasicDBObject(ID, key)));
	}

	@Override
	public Map<String, Object> get(String name, Map<String, Object> query) {
		return toMap(getCollection(name).findOne(new BasicDBObject(query)));
	}

	@Override
	public boolean exists(String key) {
		return getCollection(StringConstants.EMPTY).count(new BasicDBObject(ID, key)) > 0;
	}

	@Override
	public boolean append(String key, Object value) {
		return false;
	}

	@Override
	public void clear() {
		getCollection(StringConstants.EMPTY).drop();
	}

	@Override
	public void close() {
		dbc = null;
		db = null;
		client.close();
	}

	/**
	 * 获得数据集合
	 * @param name 集合名
	 * @return 数据集合
	 */
	private DBCollection getCollection(String name) {
		// 获得数据集合
		DBCollection dbc = EmptyUtil.isEmpty(name) ? this.dbc : dbcs.get(name);
		// 如果数据集合为空
		if (dbc == null) {
			dbcs.put(name, dbc = db.getCollection(name));
		}
		// 返回集合
		return dbc;
	}
}