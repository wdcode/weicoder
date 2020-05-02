package com.weicoder.redis.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.params.Params;
import com.weicoder.common.U;

import redis.clients.jedis.Protocol;

/**
 * Redis配置读取
 * 
 * @author WD
 */
public final class RedisParams {
	/** redis前缀 */
	public final static String PREFIX = "redis";
	// Properties配置
	private final static Config CONFIG = ConfigFactory.getConfig(PREFIX);
	/** 定时更新时间 秒 */
	public final static int     DELAY  = CONFIG.getInt("dao.delay", Params.getInt(getKey(PREFIX, "dao.delay"), 1));
	/** 是否分分步执行 */
	public final static boolean SETP   = CONFIG.getBoolean("dao.setp",
			Params.getBoolean(getKey(PREFIX, "dao.setp"), true));
	// 参数
	private final static String CLUSTER    = "cluster";
	private final static String HOST       = "host";
	private final static String TIMEOUT    = "timeout";
	private final static String PORT       = "port";
	private final static String MAXTOTAL   = "maxTotal";
	private final static String MAXIDLE    = "maxIdle";
	private final static String PASSWORD   = "password";
	private final static String DB         = "db";
	private final static String MAXWAIT    = "maxWait";
	private final static String CACHE_FILL = "cache.fill";

	/**
	 * Redis缓存是否自动填充
	 * 
	 * @param  name 名
	 * @return      是否填充
	 */
	public static boolean getCacheFill(String name) {
		return CONFIG.getBoolean(Params.getKey(name, CACHE_FILL), Params.getBoolean(getKey(name, CACHE_FILL), false));
	}

	/**
	 * Redis集群地址
	 * 
	 * @param  name 名
	 * @return      集群地址
	 */
	public static String[] getCluster(String name) {
		return CONFIG.getStringArray(Params.getKey(name, CLUSTER),
				Params.getStringArray(getKey(name, CLUSTER), ArrayConstants.STRING_EMPTY));
	}

	/**
	 * Redis服务器地址
	 * 
	 * @param  name 名
	 * @return      服务器地址
	 */
	public static String getHost(String name) {
		return CONFIG.getString(Params.getKey(name, HOST), Params.getString(getKey(name, HOST), "127.0.0.1"));
	}

	/**
	 * Redis超时时间
	 * 
	 * @param  name 名
	 * @return      端口
	 */
	public static int getTimeOut(String name) {
		return CONFIG.getInt(Params.getKey(name, TIMEOUT),
				Params.getInt(getKey(name, TIMEOUT), Protocol.DEFAULT_TIMEOUT));
	}

	/**
	 * Redis服务器端口
	 * 
	 * @param  name 名
	 * @return      端口
	 */
	public static int getPort(String name) {
		return CONFIG.getInt(Params.getKey(name, PORT), Params.getInt(getKey(name, PORT), 6379));
	}

	/**
	 * Redis最大活动数
	 * 
	 * @param  name 名
	 * @return      int
	 */
	public static int getMaxTotal(String name) {
		return CONFIG.getInt(Params.getKey(name, MAXTOTAL), Params.getInt(getKey(name, MAXTOTAL), 100));
	}

	/**
	 * Redis最大空闲数
	 * 
	 * @param  name 名
	 * @return      int
	 */
	public static int getMaxIdle(String name) {
		return CONFIG.getInt(Params.getKey(name, MAXIDLE), Params.getInt(getKey(name, MAXIDLE), 30));
	}

	/**
	 * Redis密码
	 * 
	 * @param  name 名
	 * @return      long
	 */
	public static String getPassword(String name) {
		String password = CONFIG.getString(Params.getKey(name, PASSWORD), Params.getString(getKey(name, PASSWORD)));
		return U.E.isEmpty(password) ? null : password;
	}

	/**
	 * redis数据库
	 * 
	 * @param  name 数据库名
	 * @return      默认数据库
	 */
	public static int getDatabase(String name) {
		return CONFIG.getInt(Params.getKey(name, DB), Params.getInt(getKey(name, DB), Protocol.DEFAULT_DATABASE));
	}

	/**
	 * Redis最大等待时间
	 * 
	 * @param  name 名
	 * @return      long
	 */
	public static long getMaxWait(String name) {
		return CONFIG.getLong(Params.getKey(name, MAXWAIT), Params.getLong(getKey(name, MAXWAIT), 1000));
	}

	/**
	 * 用name替换键
	 * 
	 * @param  name 名称
	 * @param  key  键
	 * @return      替换后的键
	 */
	private static String getKey(String name, String key) {
		return Params.getKey(PREFIX, name, key);
	}

	private RedisParams() {
	}
}
