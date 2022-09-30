package com.weicoder.mongo.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.params.P;

/**
 * MongoDB配置读取
 * 
 * @author WD
 */
public final class MongoParams {
	// 前缀
	private final static String PREFIX = "mongo";
	// Properties配置
	private final static Config CONFIG     = ConfigFactory.getConfig(PREFIX);
	private final static String HOST       = "host";
	private final static String USER       = "user";
	private final static String PASSWORD   = "password";
	private final static String DB         = "db";
	private final static String COLLECTION = "collection";
	private final static String PORT       = "port";

	/**
	 * Mongo服务器地址
	 * 
	 * @param  name 名称
	 * @return      服务器地址
	 */
	public static String getHost(String name) {
		return CONFIG.getString(P.getKey(name, HOST), P.getString(getKey(name, HOST), "127.0.0.1"));
	}

	/**
	 * Mongo服务器库用户
	 * 
	 * @param  name 名称
	 * @return      用户
	 */
	public static String getUser(String name) {
		return CONFIG.getString(P.getKey(name, USER), P.getString(getKey(name, USER)));
	}

	/**
	 * Mongo服务器库用户密码
	 * 
	 * @param  name 名称
	 * @return      密码
	 */
	public static String getPassword(String name) {
		return CONFIG.getString(P.getKey(name, PASSWORD), P.getString(getKey(name, PASSWORD)));
	}

	/**
	 * Mongo数据库名
	 * 
	 * @param  name 名称
	 * @return      数据库名
	 */
	public static String getDB(String name) {
		return CONFIG.getString(P.getKey(name, DB), P.getString(getKey(name, DB), name));
	}

	/**
	 * Mongo数据库中集合名
	 * 
	 * @param  name 名
	 * @return      集合
	 */
	public static String getCollection(String name) {
		return CONFIG.getString(P.getKey(name, COLLECTION), P.getString(getKey(name, COLLECTION), name));
	}

	/**
	 * Mongo服务器端口
	 * 
	 * @param  name 名
	 * @return      端口
	 */
	public static int getPort(String name) {
		return CONFIG.getInt(P.getKey(name, PORT), P.getInt(getKey(name, PORT), 27017));
	}

	/**
	 * 用name替换键
	 * 
	 * @param  name 名称
	 * @param  key  键
	 * @return      替换后的键
	 */
	private static String getKey(String name, String key) {
		return P.getKey(PREFIX, name, key);
	}

	private MongoParams() {
	}
}
