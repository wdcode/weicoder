package com.weicoder.core.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.params.Params;

/**
 * MemCache配置读取
 * @author WD
 * @since JDK7
 * @version 1.0 2011-05-26
 */
public final class DataSourceParams {
	/* MemCache使用 */
	private final static String		PREFIX;			// 前缀
	/* DataSource使用 */
	private final static String		DRIVER;			// 获得DataSource驱动类
	private final static String		URL;				// 获得DataSourceUrl
	private final static String		USER;				// 获得DataSourceUser
	private final static String		PASSWORD;			// 获得DataSourcePassword
	private final static String		INITIAL_POOL_SIZE;	// 获得初始化连接数
	private final static String		MAX_POOL_SIZE;		// 连接池最大连接数
	private final static String		MIN_POOL_SIZE;		// 连接池最小连接数
	private final static String		MAX_SIZE;			// 最大连接数
	private final static String		TIMEOUT;			// 超时等待时间
	private final static String		MAXIDLETIME;		// 测试空闲连接时间超出时间回收
	private final static String		IDLETIMEOUT;		// 多长时间检查一次空闲连接
	private final static String		PARSE;				// 默认使用的连接池

	/**
	 * 执行任务名称数组
	 */
	public final static String[]	NAMES;

	/**
	 * 静态初始化
	 */
	static {
		PREFIX = "datasource";
		DRIVER = "driver"; // 获得DataSource驱动类
		URL = "url"; // 获得DataSourceUrl
		USER = "user"; // 获得DataSourceUser
		PASSWORD = "password"; // 获得DataSourcePassword
		INITIAL_POOL_SIZE = "initialPoolSize"; // 获得初始化连接数
		MAX_POOL_SIZE = "maxPoolSize"; // 连接池最大连接数
		MIN_POOL_SIZE = "minPoolSize"; // 连接池最小连接数
		MAX_SIZE = "maxSize"; // 最大连接数
		TIMEOUT = "timeout"; // 超时等待时间
		MAXIDLETIME = "maxIdleTime"; // 测试空闲连接时间超出时间回收
		IDLETIMEOUT = "idleTimeout"; // 多长时间检查一次空闲连接
		PARSE = "parse"; // 默认使用的连接池
		NAMES = Params.getStringArray("datasource.names", ArrayConstants.STRING_EMPTY);
	}

	/* DataSource使用 */
	private static int				initialPoolSize;	// 获得初始化连接数
	private static int				maxPoolSize;		// 连接池最大连接数
	private static int				minPoolSize;		// 连接池最小连接数
	private static int				maxSize;			// 最大连接数
	private static long				timeout;			// 超时等待时间
	private static long				maxIdleTime;		// 测试空闲连接时间超出时间回收
	private static long				idleTimeout;		// 多长时间检查一次空闲连接

	/**
	 * 静态初始化
	 */
	static {
		initialPoolSize = 20; // 获得初始化连接数
		maxPoolSize = 50; // 连接池最大连接数
		minPoolSize = 10; // 连接池最小连接数
		maxSize = 100; // 最大连接数
		timeout = DateConstants.TIME_MINUTE * 3; // 超时等待时间
		maxIdleTime = DateConstants.TIME_MINUTE * 10; // 测试空闲连接时间超出时间回收
		idleTimeout = DateConstants.TIME_HOUR * 2; // 多长时间检查一次空闲连接
	}

	/**
	 * 最大连接数 DataSourceFactory使用<br/>
	 * 需在配置文件中配置,如果不配置或配置不对将优先使用100<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.maxSize = ? <br/>
	 * XML: {@literal <dataSource><maxSize>?</maxSize></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return 最大连接数
	 */
	public static int getMaxSize(String name) {
		return Params.getInt(getKey(name, MAX_SIZE), maxSize);
	}

	/**
	 * 超时等待时间 DataSourceFactory使用<br/>
	 * 需在配置文件中配置,如果不配置或配置不对将优先使用1000L * 60 * 3<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.timeout = ? <br/>
	 * XML: {@literal <dataSource><timeout>?</timeout></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return 超时等待时间
	 */
	public static long getTimeout(String name) {
		return Params.getLong(getKey(name, TIMEOUT), timeout);
	}

	/**
	 * 测试空闲连接时间超出时间回收 DataSourceFactory使用<br/>
	 * 需在配置文件中配置,如果不配置或配置不对将优先使用1000L * 60 * 10<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.maxIdleTime = ? <br/>
	 * XML: {@literal <dataSource><maxIdleTime>?</maxIdleTime></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return 测试空闲连接时间超出时间回收
	 */
	public static long getMaxIdleTime(String name) {
		return Params.getLong(getKey(name, MAXIDLETIME), maxIdleTime);
	}

	/**
	 * 多长时间检查一次空闲连接 DataSourceFactory使用<br/>
	 * 需在配置文件中配置,如果不配置或配置不对将优先使用1000L * 60 * 60 * 2<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.idleTimeout = ? <br/>
	 * XML: {@literal <dataSource><idleTimeout>?</idleTimeout></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return 多长时间检查一次空闲连接
	 */
	public static long getIdleTimeout(String name) {
		return Params.getLong(getKey(name, IDLETIMEOUT), idleTimeout);
	}

	/**
	 * 获得DataSource驱动类 DataSourceFactory使用<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.driver = ? <br/>
	 * XML: {@literal <dataSource><driver>?</driver></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return DataSource驱动类
	 */
	public static String getDriver(String name) {
		return Params.getString(getKey(name, DRIVER));
	}

	/**
	 * 获得DataSourceUrl DataSourceFactory使用<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.url = ? <br/>
	 * XML: {@literal <dataSource><url>?</url></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return DataSourceUrl
	 */
	public static String getUrl(String name) {
		return Params.getString(getKey(name, URL));
	}

	/**
	 * 获得DataSourceUser DataSourceFactory使用<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.user = ? <br/>
	 * XML: {@literal <dataSource><user>?</user></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return DataSourceUser
	 */
	public static String getUser(String name) {
		return Params.getString(getKey(name, USER));
	}

	/**
	 * 获得DataSourcePassword DataSourceFactory使用<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.password = ? <br/>
	 * XML: {@literal <dataSource><password>?</password></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return DataSourcePassword
	 */
	public static String getPassword(String name) {
		return Params.getString(getKey(name, PASSWORD));
	}

	/**
	 * 获得初始化连接数 DataSourceFactory使用<br/>
	 * 需在配置文件中配置,如果不配置或配置不对将优先使用20<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.initialPoolSize = ? <br/>
	 * XML: {@literal <dataSource><initialPoolSize>?</initialPoolSize></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return 初始化连接数
	 */
	public static int getInitialPoolSize(String name) {
		return Params.getInt(getKey(name, INITIAL_POOL_SIZE), initialPoolSize);
	}

	/**
	 * 连接池最大连接数 DataSourceFactory使用<br/>
	 * 需在配置文件中配置,如果不配置或配置不对将优先使用50<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.maxPoolSize = ? <br/>
	 * XML: {@literal <dataSource><maxPoolSize>?</maxPoolSize></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return 连接池最大连接数
	 */
	public static int getMaxPoolSize(String name) {
		return Params.getInt(getKey(name, MAX_POOL_SIZE), maxPoolSize);
	}

	/**
	 * 连接池最小连接数 DataSourceFactory使用<br/>
	 * 需在配置文件中配置,如果不配置或配置不对将优先使用10<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.minPoolSize = ? <br/>
	 * XML: {@literal <dataSource><minPoolSize>?</minPoolSize></dataSource>}</h2>
	 * @see org.com.weicoder.core.datasource.factory.DataSourceFactory
	 * @return 连接池最小连接数
	 */
	public static int getMinPoolSize(String name) {
		return Params.getInt(getKey(name, MIN_POOL_SIZE), minPoolSize);
	}

	/**
	 * 默认使用的连接池 DataSource使用<br/>
	 * 需在配置文件中配置,如果不配置或配置不对将优先使用DBCP<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: datasource.parse = ? <br/>
	 * XML: {@literal <dataSource><parse>?</parse></dataSource>}</h2>
	 * @return 默认使用的连接池
	 */
	public static String getParse(String name) {
		return Params.getString(getKey(name, PARSE), "dbcp2");
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
	private DataSourceParams() {}
}
