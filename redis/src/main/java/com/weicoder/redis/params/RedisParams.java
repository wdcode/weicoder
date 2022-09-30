package com.weicoder.redis.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.constants.C;
import com.weicoder.common.params.P;
import com.weicoder.common.util.U;

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
	public final static int     DELAY  = CONFIG.getInt("dao.delay", P.getInt(getKey(PREFIX, "dao.delay"), 1));
	/** 是否分分步执行 */
	public final static boolean SETP   = CONFIG.getBoolean("dao.setp",
			P.getBoolean(getKey(PREFIX, "dao.setp"), true));
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
		return CONFIG.getBoolean(P.getKey(name, CACHE_FILL), P.getBoolean(getKey(name, CACHE_FILL), false));
	}

	/**
	 * Redis集群地址
	 * 
	 * @param  name 名
	 * @return      集群地址
	 */
	public static String[] getCluster(String name) {
		return CONFIG.getStringArray(P.getKey(name, CLUSTER),
				P.getStringArray(getKey(name, CLUSTER), C.A.STRING_EMPTY));
	}

	/**
	 * Redis服务器地址
	 * 
	 * @param  name 名
	 * @return      服务器地址
	 */
	public static String getHost(String name) {
		return CONFIG.getString(P.getKey(name, HOST), P.getString(getKey(name, HOST), "127.0.0.1"));
	}

	/**
	 * Redis超时时间
	 * 
	 * @param  name 名
	 * @return      端口
	 */
	public static int getTimeOut(String name) {
		return CONFIG.getInt(P.getKey(name, TIMEOUT),
				P.getInt(getKey(name, TIMEOUT), Protocol.DEFAULT_TIMEOUT));
	}

	/**
	 * Redis服务器端口
	 * 
	 * @param  name 名
	 * @return      端口
	 */
	public static int getPort(String name) {
		return CONFIG.getInt(P.getKey(name, PORT), P.getInt(getKey(name, PORT), 6379));
	}

	/**
	 * Redis最大活动数
	 * 
	 * @param  name 名
	 * @return      int
	 */
	public static int getMaxTotal(String name) {
		return CONFIG.getInt(P.getKey(name, MAXTOTAL), P.getInt(getKey(name, MAXTOTAL), 100));
	}

	/**
	 * Redis最大空闲数
	 * 
	 * @param  name 名
	 * @return      int
	 */
	public static int getMaxIdle(String name) {
		return CONFIG.getInt(P.getKey(name, MAXIDLE), P.getInt(getKey(name, MAXIDLE), 30));
	}

	/**
	 * Redis密码
	 * 
	 * @param  name 名
	 * @return      long
	 */
	public static String getPassword(String name) {
		String password = CONFIG.getString(P.getKey(name, PASSWORD), P.getString(getKey(name, PASSWORD)));
		return U.E.isEmpty(password) ? null : password;
	}

	/**
	 * redis数据库
	 * 
	 * @param  name 数据库名
	 * @return      默认数据库
	 */
	public static int getDatabase(String name) {
		return CONFIG.getInt(P.getKey(name, DB), P.getInt(getKey(name, DB), Protocol.DEFAULT_DATABASE));
	}

	/**
	 * Redis最大等待时间
	 * 
	 * @param  name 名
	 * @return      long
	 */
	public static long getMaxWait(String name) {
		return CONFIG.getLong(P.getKey(name, MAXWAIT), P.getLong(getKey(name, MAXWAIT), 1000));
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

	private RedisParams() {
	}
}
