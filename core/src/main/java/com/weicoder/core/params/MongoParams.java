package com.weicoder.core.params;

import com.weicoder.common.params.Params;

/**
 * MongoDB配置读取
 * @author WD
 * @since JDK7
 * @version 1.0 2011-06-23
 */
public final class MongoParams {
	/* Redis使用 */
	private final static String	PREFIX;	// 前缀
	private final static String	HOST;		// 服务器地址
	private final static String	PORT;		// 服务器端口
	private final static String	DB;		// 数据库名
	private final static String	COLLECTION; // 集合

	/**
	 * 静态初始化
	 */
	static {
		/* Redis使用 */
		PREFIX = "mongo"; // 键
		HOST = "host"; // 服务器地址
		PORT = "port"; // 服务器端口
		DB = "db";// 数据库名
		COLLECTION = "collection";// 集合
	}

	/* Redis使用 */
	private static String		host;		// 服务器地址
	private static int			port;		// 服务器端口

	/**
	 * 静态初始化
	 */
	static {
		host = "127.0.0.1"; // 服务器地址
		port = 27017; // 服务器端口
	}

	/**
	 * Mongo服务器地址<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: mongo.host = ? <br/>
	 * XML: {@literal <mongo><host>?</host></mongo>}</h2>
	 * @return Mongo服务器地址
	 */
	public static String getHost(String name) {
		return Params.getString(getKey(name, HOST), host);
	}

	/**
	 * Mongo服务器库用户<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: mongo.user = ? <br/>
	 * XML: {@literal <mongo><user>?</user></mongo>}</h2>
	 * @return Mongo服务器库用户
	 */
	public static String getUser(String name) {
		return Params.getString(getKey(name, "user"), "wdcode");
	}

	/**
	 * Mongo服务器库用户密码<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: mongo.password = ? <br/>
	 * XML: {@literal <mongo><password>?</password></mongo>}</h2>
	 * @return Mongo服务器库用户密码
	 */
	public static String getPassword(String name) {
		return Params.getString(getKey(name, "password"), "123456");
	}

	/**
	 * Mongo数据库名<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: mongo.db = ? <br/>
	 * XML: {@literal <mongo><db>?</db></mongo>}</h2>
	 * @return Mongo数据库名
	 */
	public static String getDB(String name) {
		return Params.getString(getKey(name, DB), name);
	}

	/**
	 * Mongo数据库中集合名<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: mongo.collection = ? <br/>
	 * XML: {@literal <mongo><collection>?</collection></mongo>}</h2>
	 * @return Mongo数据库中集合名
	 */
	public static String getCollection(String name) {
		return Params.getString(getKey(name, COLLECTION), name);
	}

	/**
	 * Mongo服务器端口<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: mongo.port = ? <br/>
	 * XML: {@literal <mongo><port>?</port></mongo>}</h2>
	 * @return Mongo服务器端口
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

	/**
	 * 私有构造
	 */
	private MongoParams() {}
}
