package com.weicoder.nosql.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.params.Params;
import com.weicoder.common.util.EmptyUtil;

import redis.clients.jedis.Protocol;

/**
 * Redis配置读取
 * @author WD
 */
public final class RedisParams {
	/* Redis使用 */
	/** Redis前缀 */
	public final static String	PREFIX		= "redis";		// 前缀
	private final static String	HOST		= "host";		// 服务器地址
	private final static String	PORT		= "port";		// 服务器端口
	private final static String	MAX_TOTAL	= "maxTotal";	// 最大活动数
	private final static String	MAX_IDLE	= "maxIdle";	// 最大空闲数
	private final static String	MAX_WAIT	= "maxWait";	// 最大等待时间

	/* Redis使用 */
	private static String		host		= "127.0.0.1";	// 服务器地址
	private static int			port		= 6379;			// 服务器端口
	private static int			maxTotal	= 100;			// 最大活动数
	private static int			maxIdle		= 30;			// 最大空闲数
	private static long			maxWait		= 1000;			// 最大等待时间

	/**
	 * Redis集群地址
	 * @param name 名
	 * @return 集群地址
	 */
	public static String[] getCluster(String name) {
		return Params.getStringArray(getKey(name, "cluster"), ArrayConstants.STRING_EMPTY);
	}

	/**
	 * Redis 解析类型 默认 cluster 可选项 cluster 集群 pool池 redisson
	 * @param name 名
	 * @return 服务器地址
	 */
	public static String getType(String name) {
		return Params.getString(getKey(name, "type"), "cluster");
	}

	/**
	 * Redis服务器地址
	 * @param name 名
	 * @return 服务器地址
	 */
	public static String getHost(String name) {
		return Params.getString(getKey(name, HOST), host);
	}

	/**
	 * Redis服务器端口
	 * @param name 名
	 * @return 端口
	 */
	public static int getPort(String name) {
		return Params.getInt(getKey(name, PORT), port);
	}

	/**
	 * Redis最大活动数
	 * @param name 名
	 * @return int
	 */
	public static int getMaxTotal(String name) {
		return Params.getInt(getKey(name, MAX_TOTAL), maxTotal);
	}

	/**
	 * Redis最大空闲数
	 * @param name 名
	 * @return int
	 */
	public static int getMaxIdle(String name) {
		return Params.getInt(getKey(name, MAX_IDLE), maxIdle);
	}

	/**
	 * Redis最大等待时间
	 * @param name 名
	 * @return long
	 */
	public static String getPassword(String name) {
		String password = Params.getString(getKey(name, "password"));
		return EmptyUtil.isEmpty(password) ? null : password;
	}

	/**
	 * redis数据库
	 * @param name 数据库名
	 * @return 默认数据库
	 */
	public static int getDatabase(String name) {
		return Params.getInt(getKey(name, "database"), Protocol.DEFAULT_DATABASE);
	}

	/**
	 * Redis最大等待时间
	 * @param name 名
	 * @return long
	 */
	public static long getMaxWait(String name) {
		return Params.getLong(getKey(name, MAX_WAIT), maxWait);
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

	private RedisParams() {}
}
