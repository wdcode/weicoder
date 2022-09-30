package com.weicoder.frame.params;

import com.weicoder.common.constants.C; 
import com.weicoder.common.params.P;

/**
 * MemCache配置读取
 * @author WD
 * @version 1.0
 */
public final class DataSourceParams {
	/* MemCache使用 */
	private final static String		PREFIX				= "datasource";								// 前缀
	/* DataSource使用 */
	private final static String		DRIVER				= "driver";									// 获得DataSource驱动类
	private final static String		URL					= "url";									// 获得DataSourceUrl
	private final static String		USER				= "user";									// 获得DataSourceUser
	private final static String		PASSWORD			= "password";								// 获得DataSourcePassword
	private final static String		INITIAL_POOL_SIZE	= "initialPoolSize";						// 获得初始化连接数
	private final static String		MAX_POOL_SIZE		= "maxPoolSize";							// 连接池最大连接数
	private final static String		MIN_POOL_SIZE		= "minPoolSize";							// 连接池最小连接数
	private final static String		MAX_SIZE			= "maxSize";								// 最大连接数
	private final static String		TIMEOUT				= "timeout";								// 超时等待时间
	private final static String		MAXIDLETIME			= "maxIdleTime";							// 测试空闲连接时间超出时间回收
	private final static String		IDLETIMEOUT			= "idleTimeout";							// 多长时间检查一次空闲连接
	private final static String		PARSE				= "parse";									// 默认使用的连接池

	/** 执行任务名称数组 */
	public final static String[]	NAMES				= P.getStringArray("datasource.names",
			C.A.STRING_EMPTY);

	/* DataSource使用 */
	private static int				initialPoolSize		= 20;										// 获得初始化连接数
	private static int				maxPoolSize			= 50;										// 连接池最大连接数
	private static int				minPoolSize			= 10;										// 连接池最小连接数
	private static int				maxSize				= 100;										// 最大连接数
	private static long				timeout				= C.D.TIME_MINUTE * 3;			// 超时等待时间
	private static long				maxIdleTime			= C.D.TIME_MINUTE * 10;			// 测试空闲连接时间超出时间回收
	private static long				idleTimeout			= C.D.TIME_HOUR * 2;				// 多长时间检查一次空闲连接

	/**
	 * 最大连接数 DataSourceFactory使用
	 * @param name 名称
	 * @return 最大连接数
	 */
	public static int getMaxSize(String name) {
		return P.getInt(getKey(name, MAX_SIZE), maxSize);
	}

	/**
	 * 超时等待时间 DataSourceFactory使用
	 * @param name 名称
	 * @return 超时等待时间
	 */
	public static long getTimeout(String name) {
		return P.getLong(getKey(name, TIMEOUT), timeout);
	}

	/**
	 * 测试空闲连接时间超出时间回收 DataSourceFactory使用
	 * @param name 名称
	 * @return 测试空闲连接时间超出时间回收
	 */
	public static long getMaxIdleTime(String name) {
		return P.getLong(getKey(name, MAXIDLETIME), maxIdleTime);
	}

	/**
	 * 多长时间检查一次空闲连接 DataSourceFactory使用
	 * @param name 名称
	 * @return 多长时间检查一次空闲连接
	 */
	public static long getIdleTimeout(String name) {
		return P.getLong(getKey(name, IDLETIMEOUT), idleTimeout);
	}

	/**
	 * 获得DataSource驱动类 DataSourceFactory使用
	 * @param name 名称
	 * @return DataSource驱动类
	 */
	public static String getDriver(String name) {
		return P.getString(getKey(name, DRIVER));
	}

	/**
	 * 获得DataSourceUrl DataSourceFactory使用
	 * @param name 名称
	 * @return DataSourceUrl
	 */
	public static String getUrl(String name) {
		return P.getString(getKey(name, URL));
	}

	/**
	 * 获得DataSourceUser DataSourceFactory使用
	 * @param name 名称
	 * @return DataSourceUser
	 */
	public static String getUser(String name) {
		return P.getString(getKey(name, USER));
	}

	/**
	 * 获得DataSourcePassword DataSourceFactory使用 需在配置文件中配置
	 * @param name 名称
	 * @return DataSourcePassword
	 */
	public static String getPassword(String name) {
		return P.getString(getKey(name, PASSWORD));
	}

	/**
	 * 获得初始化连接数 DataSourceFactory使用
	 * @param name 名称
	 * @return 初始化连接数
	 */
	public static int getInitialPoolSize(String name) {
		return P.getInt(getKey(name, INITIAL_POOL_SIZE), initialPoolSize);
	}

	/**
	 * 连接池最大连接数 DataSourceFactory使用
	 * @param name 名称
	 * @return 连接池最大连接数
	 */
	public static int getMaxPoolSize(String name) {
		return P.getInt(getKey(name, MAX_POOL_SIZE), maxPoolSize);
	}

	/**
	 * 连接池最小连接数 DataSourceFactory使用
	 * @param name 名称
	 * @return 连接池最小连接数
	 */
	public static int getMinPoolSize(String name) {
		return P.getInt(getKey(name, MIN_POOL_SIZE), minPoolSize);
	}

	/**
	 * 默认使用的连接池 DataSource使用
	 * @param name 名称
	 * @return 默认使用的连接池
	 */
	public static String getParse(String name) {
		return P.getString(getKey(name, PARSE), "dbcp2");
	}

	/**
	 * 用name替换键
	 * @param name 名称
	 * @param key 键
	 * @return 替换后的键
	 */
	private static String getKey(String name, String key) {
		return P.getKey(PREFIX, name, key);
	}

	private DataSourceParams() {}
}
