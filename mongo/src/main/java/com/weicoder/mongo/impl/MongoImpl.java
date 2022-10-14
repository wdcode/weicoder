package com.weicoder.mongo.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bson.Document;

import com.weicoder.common.constants.C;
import com.weicoder.common.lang.W; 
import com.weicoder.common.log.Logs;
import com.weicoder.common.statics.S;
import com.weicoder.common.util.U;
import com.weicoder.json.J;
import com.weicoder.mongo.Mongo;
import com.weicoder.mongo.params.MongoParams;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
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
	private final static String						ID		= "_id";
	// 常量字符串 "key"
	private final static String						KEY		= "key";
	// 常量字符串 "value"
	private final static String						VALUE	= "value";
	// Mongo 客户端
	private MongoClient								client;
	// MongoDB
	private MongoDatabase							db;
	// 数据集合对象
	private MongoCollection<Document>				dbc;
	// 数据集合列表
	private Map<String, MongoCollection<Document>>	dbcs;
	// 读写锁
	private Lock									lock	= new ReentrantLock(true);

	/**
	 * 构造方法
	 * @param key 键
	 */
	public MongoImpl(String key) {
		try {
			// Mongo 客户端
			Builder builder = MongoClientOptions.builder();
			builder.connectionsPerHost(100);
//			builder.threadsAllowedToBlockForConnectionMultiplier(100);
			// MongoCredential
			MongoCredential credential = null;
			if (U.E.isNotEmpty(MongoParams.getUser(key)))
				credential = MongoCredential.createScramSha1Credential(MongoParams.getUser(key), "admin", MongoParams.getPassword(key).toCharArray());
			// 实例化客户端
			if (credential == null)
				client = new MongoClient(new ServerAddress(MongoParams.getHost(key), MongoParams.getPort(key)), builder.build());
			else
				client = new MongoClient(new ServerAddress(MongoParams.getHost(key), MongoParams.getPort(key)), credential, builder.build());
			// 如果库存在
			db = client.getDatabase(MongoParams.getDB(key));
			if (U.E.isNotEmpty(MongoParams.getCollection(key)))
				dbc = db.getCollection(MongoParams.getCollection(key));
			dbcs = W.M.map();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	@Override
	public void insert(String name, Object data) {
		getCollection(name).insertOne(Document.parse(J.toJson(data)));
	}

	@Override
	public void insert(String name, List<Object> list) {
		// 声明Document列表
		List<Document> documents = W.L.list(list.size());
		// 循环map数组
		list.forEach(data -> documents.add(Document.parse(J.toJson(data))));
		// 插入数据
		getCollection(name).insertMany(documents);
	}

	@Override
	public long count(String name) {
		return getCollection(name).countDocuments();
	}

	@Override
	public long count(String name, Map<String, Object> query) {
		return getCollection(name).countDocuments(new BasicDBObject(query));
	}

	@Override
	public void createIndex(String name, Map<String, Object> keys) {
		getCollection(name).createIndex(new BasicDBObject(keys));
	}

	@Override
	public void dropIndex(String name, String index) {
		getCollection(name).dropIndex(index);
	}

	@Override
	public void dropIndex(String name, Map<String, Object> keys) {
		getCollection(name).dropIndex(new BasicDBObject(keys));
	}

	@Override
	public void dropIndexes(String name) {
		getCollection(name).dropIndexes();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void delete(String name, Map<String, Object>... maps) {
		// 获得数据集合
		MongoCollection<Document> dbc = getCollection(name);
		// 循环map数组
		for (int i = 0; i < maps.length; i++)
			// 删除对象
			dbc.deleteOne(new BasicDBObject(newMap(maps[i])));
	}

	@Override
	public void delete(String name, Map<String, Object> data) {
		getCollection(name).deleteOne(new BasicDBObject(newMap(data)));
	}

	@Override
	public void update(String name, Map<String, Object> query, Map<String, Object> obj) {
		getCollection(name).updateOne(new BasicDBObject(query), new BasicDBObject(obj));
	}

	@Override
	public List<Map<String, Object>> query(String name) {
		return query(name, null);
	}

	@Override
	public List<Map<String, Object>> query(String name, Map<String, Object> query) {
		return query(name, query, 0, 0);
	}

	@Override
	public List<Map<String, Object>> query(String name, Map<String, Object> query, int start, int end) {
		// 获得数据库游标
		FindIterable<Document> iterable = getCollection(name).find(U.E.isEmpty(query) ? new BasicDBObject() : new BasicDBObject(query));
		// 设置游标开始位置
		iterable.skip(start);
		// 设置限定数量
		iterable.limit(end - start);
		// 获得列表
		List<Map<String, Object>> list = W.L.list();
		// 循环游标
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext())
			// 添加到列表中
			list.add(toMap(cursor.next()));
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
		return (Map<String, Object>) (U.E.isEmpty(object) ? W.M.map() : object);
	}

	/**
	 * 更换id key 键为主键_id
	 * @param map 数据Map
	 * @return 更改完Map
	 */
	private Map<String, Object> newMap(Map<String, Object> map) {
		// 判断_id为空 赋值
		if (U.E.isNotEmpty(map)) {
			// 获得ID
			Object key = map.get(ID);
			// 判断如果为空获得 id键
			key = U.E.isEmpty(key) ? map.get(KEY) : key;
			// 设置主键
			map.put(ID, key);
		}
		// 返回Map
		return map;
	}

	@Override
	public boolean set(String key, Object value) {
		// 获得Map
		Map<String, Object> map = W.M.map();
		// 设置键值
		map.put(ID, key);
		map.put(VALUE, value);
		// 添加数据
		insert(C.S.EMPTY, map);
		// 返回成功
		return true;
	}

	@Override
	public Object get(String key) {
		return toMap(dbc.find(new BasicDBObject(key, null)).first()).get(VALUE);
	}

	@Override
	public void remove(String... key) {
		// 获得数据集合
		MongoCollection<Document> dbc = getCollection(C.S.EMPTY);
		// 循环删除
		for (String k : key)
			dbc.deleteOne(new BasicDBObject(ID, k));
	}

	@Override
	public long count(String name, Object key) {
		return count(name, W.M.map(ID, key));
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
		return getCollection(C.S.EMPTY).countDocuments(new BasicDBObject(ID, key)) > 0;
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
		return set(key, S.Z.compress(value));
	}

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * @param key 键
	 * @return 值
	 */
	public final byte[] extract(String key) {
		return S.Z.extract(get(key));
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
		for (int i = 0; i < keys.length; i++)
			objs[i] = get(keys[i]);
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
		List<byte[]> list = W.L.list(keys.length);
		// 循环解压数据
		for (Object o : get(keys))
			list.add(S.Z.extract(o));
		// 返回列表
		return list;
	}

	/**
	 * 获得数据集合
	 * @param name 集合名
	 * @return 数据集合
	 */
	public MongoCollection<Document> getCollection(String name) {
		// 获得数据集合
		MongoCollection<Document> dbc = U.E.isEmpty(name) ? this.dbc : dbcs.get(name);
		// 如果数据集合为空
		if (dbc == null) {
			lock.lock();
			if (dbc == null) {
				dbc = db.getCollection(name);
				if (dbc == null) {
					db.createCollection(name);
					dbc = db.getCollection(name);
				}
				dbcs.put(name, dbc);
			}
			lock.unlock();
		}
		// 返回集合
		return dbc;
	}
}