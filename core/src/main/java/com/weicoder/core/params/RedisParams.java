package com.weicoder.core.params;

import com.weicoder.common.params.Params;

/**
 * Redis配置读取
 * @author WD 
 * @version 1.0 
 */
public final class RedisParams {
	/* Redis使用 */
	private final static String	PREFIX		= "redis";		// 前缀
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
	 * Redis服务器地址<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: nosql.redis.host = ? <br/>
	 * XML: {@literal <nosql><redis><host>?</host></redis></nosql>}</h2>
	 * @return Redis服务器地址
	 */
	public static String getHost(String name) {
		return Params.getString(getKey(name, HOST), host);
	}

	/**
	 * Redis服务器端口<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: nosql.redis.port = ? <br/>
	 * XML: {@literal <nosql><redis><port>?</port></redis></nosql>}</h2>
	 * @return Redis服务器端口
	 */
	public static int getPort(String name) {
		return Params.getInt(getKey(name, PORT), port);
	}

	/**
	 * Redis最大活动数<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: nosql.redis.maxTotal = ? <br/>
	 * XML: {@literal <nosql><redis><maxTotal>?</maxTotal></redis></nosql>}</h2>
	 * @return Redis最大活动数
	 */
	public static int getMaxTotal(String name) {
		return Params.getInt(getKey(name, MAX_TOTAL), maxTotal);
	}

	/**
	 * Redis最大空闲数<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: nosql.redis.maxIdle = ? <br/>
	 * XML: {@literal <nosql><redis><maxIdle>?</maxIdle></redis></nosql>}</h2>
	 * @return Redis最大空闲数
	 */
	public static int getMaxIdle(String name) {
		return Params.getInt(getKey(name, MAX_IDLE), maxIdle);
	}

	/**
	 * Redis最大等待时间<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: nosql.redis.maxWait = ? <br/>
	 * XML: {@literal <nosql><redis><maxWait>?</maxWait></redis></nosql>}</h2>
	 * @return Redis最大等待时间
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
