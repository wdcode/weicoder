package com.weicoder.dao.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.params.Params;

/**
 * MemCache配置读取
 * @author WD
 */
public final class DataSourceParams {
	/* MemCache使用 */
	private final static String		PREFIX				= "ds";																// 前缀
	/* DataSource使用 */
	private final static String		DRIVER				= "driver";															// 获得DataSource驱动类
	private final static String		URL					= "url";															// 获得DataSourceUrl
	private final static String		USER				= "user";															// 获得DataSourceUser
	private final static String		PASSWORD			= "password";														// 获得DataSourcePassword
	private final static String		INITIAL_POOL_SIZE	= "initialPoolSize";												// 获得初始化连接数
	private final static String		MAX_POOL_SIZE		= "maxPoolSize";													// 连接池最大连接数
	private final static String		MIN_POOL_SIZE		= "minPoolSize";													// 连接池最小连接数
	private final static String		MAX_SIZE			= "maxSize";														// 最大连接数
	private final static String		TIMEOUT				= "timeout";														// 超时等待时间
	private final static String		MAXIDLETIME			= "maxIdleTime";													// 测试空闲连接时间超出时间回收
	private final static String		IDLETIMEOUT			= "idleTimeout";													// 多长时间检查一次空闲连接
	private final static String		PARSE				= "parse";															// 默认使用的连接池

	/** 执行任务名称数组 */
	public final static String[]	NAMES				= Params.getStringArray("ds.names", ArrayConstants.STRING_EMPTY);

	/* DataSource使用 */
	private static int				initialPoolSize		= 20;																// 获得初始化连接数
	private static int				maxPoolSize			= 50;																// 连接池最大连接数
	private static int				minPoolSize			= 10;																// 连接池最小连接数
	private static int				maxSize				= 100;																// 最大连接数
	private static long				timeout				= DateConstants.TIME_MINUTE * 3;									// 超时等待时间
	private static long				maxIdleTime			= DateConstants.TIME_MINUTE * 10;									// 测试空闲连接时间超出时间回收
	private static long				idleTimeout			= DateConstants.TIME_HOUR * 2;										// 多长时间检查一次空闲连接

	/**
	 * 最大连接数
	 * @param name 名
	 * @return 最大连接数
	 */
	public static int getMaxSize(String name) {
		return Params.getInt(getKey(name, MAX_SIZE), maxSize);
	}

	/**
	 * 超时等待时间
	 * @param name 名
	 * @return 超时等待时间
	 */
	public static long getTimeout(String name) {
		return Params.getLong(getKey(name, TIMEOUT), timeout);
	}

	/**
	 * 测试空闲连接时间超出时间回收
	 * @param name 名
	 * @return 测试空闲连接时间
	 */
	public static long getMaxIdleTime(String name) {
		return Params.getLong(getKey(name, MAXIDLETIME), maxIdleTime);
	}

	/**
	 * 多长时间检查一次空闲连接
	 * @param name 名
	 * @return 多长时间检查一次空闲连接
	 */
	public static long getIdleTimeout(String name) {
		return Params.getLong(getKey(name, IDLETIMEOUT), idleTimeout);
	}

	/**
	 * 获得DataSource驱动类
	 * @param name 名
	 * @return 获得DataSource驱动类
	 */
	public static String getDriver(String name) {
		return Params.getString(getKey(name, DRIVER));
	}

	/**
	 * 获得DataSourceUrl
	 * @param name 名
	 * @return 获得DataSourceUrl
	 */
	public static String getUrl(String name) {
		return Params.getString(getKey(name, URL));
	}

	/**
	 * 获得DataSourceUser
	 * @param name 名
	 * @return 获得DataSourceUser
	 */
	public static String getUser(String name) {
		return Params.getString(getKey(name, USER));
	}

	/**
	 * 获得DataSourcePassword
	 * @param name 名
	 * @return 获得DataSourcePassword
	 */
	public static String getPassword(String name) {
		return Params.getString(getKey(name, PASSWORD));
	}

	/**
	 * 获得初始化连接数
	 * @param name 名
	 * @return 获得初始化连接数
	 */
	public static int getInitialPoolSize(String name) {
		return Params.getInt(getKey(name, INITIAL_POOL_SIZE), initialPoolSize);
	}

	/**
	 * 连接池最大连接数
	 * @param name 名
	 * @return 连接池最大连接数
	 */
	public static int getMaxPoolSize(String name) {
		return Params.getInt(getKey(name, MAX_POOL_SIZE), maxPoolSize);
	}

	/**
	 * 连接池最小连接数
	 * @param name 名
	 * @return 连接池最小连接数
	 */
	public static int getMinPoolSize(String name) {
		return Params.getInt(getKey(name, MIN_POOL_SIZE), minPoolSize);
	}

	/**
	 * 默认使用的连接池
	 * @param name 名
	 * @return 默认使用的连接池
	 */
	public static String getParse(String name) {
		return Params.getString(getKey(name, PARSE), "druid");
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

	private DataSourceParams() {}
}
