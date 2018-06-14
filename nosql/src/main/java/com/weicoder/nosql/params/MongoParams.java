package com.weicoder.nosql.params;

import com.weicoder.common.params.Params;

/**
 * MongoDB配置读取
 * @author WD
 */
public final class MongoParams {
	/* Redis使用 */
	private final static String	PREFIX		= "mongo";		// 前缀
	private final static String	HOST		= "host";		// 服务器地址
	private final static String	PORT		= "port";		// 服务器端口
	private final static String	DB			= "db";			// 数据库名
	private final static String	COLLECTION	= "collection";	// 集合

	/* Redis使用 */
	private static String		host		= "127.0.0.1";	// 服务器地址
	private static int			port		= 27017;		// 服务器端口

	/**
	 * Mongo服务器地址
	 * @param name 名称
	 * @return 服务器地址
	 */
	public static String getHost(String name) {
		return Params.getString(getKey(name, HOST), host);
	}

	/**
	 * Mongo服务器库用户
	 * @param name 名称
	 * @return 用户
	 */
	public static String getUser(String name) {
		return Params.getString(getKey(name, "user"), "wdcode");
	}

	/**
	 * Mongo服务器库用户密码
	 * @param name 名称
	 * @return 密码
	 */
	public static String getPassword(String name) {
		return Params.getString(getKey(name, "password"), "123456");
	}

	/**
	 * Mongo数据库名
	 * @param name 名称
	 * @return 数据库名
	 */
	public static String getDB(String name) {
		return Params.getString(getKey(name, DB), name);
	}

	/**
	 * Mongo数据库中集合名
	 * @param name 名
	 * @return 集合
	 */
	public static String getCollection(String name) {
		return Params.getString(getKey(name, COLLECTION), name);
	}

	/**
	 * Mongo服务器端口
	 * @param name 名
	 * @return 端口
	 */
	public static int getPort(String name) {
		return Params.getInt(getKey(name, PORT), port);
	}

	/**
	 * 用name替换键
	 * @param name 名称
	 * @param key 键
	 * @return 替换后的键
	 */
	private static String getKey(String name, String key) {
		return Params.getKey(PREFIX, name, key);
	}

	private MongoParams() {}
}
