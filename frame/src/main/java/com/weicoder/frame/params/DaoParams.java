package com.weicoder.frame.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.constants.C;
import com.weicoder.common.util.U;

/**
 * 读取Dao配置
 * @author WD
 * 
 * @version 1.0 2013-12-31
 */
public final class DaoParams {
	// 读取配置
	private final static Config		CONFIG	= ConfigFactory.getConfig("db");
	/** 多库名称 */
	public final static String[]	NAMES	= CONFIG.getStringArray("names",
			new String[] { C.S.EMPTY });

	/**
	 * 使用哪种数据库连接池 现在支持 proxool dbcp c3p0 bonecp druid
	 * @param name 名称
	 * @return 使用哪种数据库
	 */
	public static String getParse(String name) {
		return CONFIG.getString(getKey(name, "parse"), "dbcp");
	}

	/**
	 * hibernate的数据库方言
	 * @param name 名称
	 * @return 数据库方言
	 */
	public static String getDialect(String name) {
		return CONFIG.getString(getKey(name, "dialect"), "org.hibernate.dialect.MySQL5Dialect");
	}

	/**
	 * 数据库驱动
	 * @param name 名称
	 * @return 数据库驱动
	 */
	public static String getDriver(String name) {
		return CONFIG.getString(getKey(name, "driver"), "com.mysql.jdbc.Driver");
	}

	/**
	 * 数据库连接url
	 * @param name 名称
	 * @return 数据库连接
	 */
	public static String getUrl(String name) {
		return CONFIG.getString(getKey(name, "url"));
	}

	/**
	 * 数据库用户名
	 * @param name 名称
	 * @return 用户名
	 */
	public static String getUser(String name) {
		return CONFIG.getString(getKey(name, "user"));
	}

	/**
	 * 数据库密码
	 * @param name 名称
	 * @return 密码
	 */
	public static String getPassword(String name) {
		return CONFIG.getString(getKey(name, "password"));
	}

	/**
	 * 初始化连接池数量
	 * @param name 名称
	 * @return 连接池数量
	 */
	public static int getInitialPoolSize(String name) {
		return CONFIG.getInt(getKey(name, "initialPoolSize"), 30);
	}

	/**
	 * 最小连接池数量
	 * @param name 名称
	 * @return 最小连接池数量
	 */
	public static int getMinPoolSize(String name) {
		return CONFIG.getInt(getKey(name, "minPoolSize"), 30);
	}

	/**
	 * 最大连接池数量
	 * @param name 名称
	 * @return 最大连接池数量
	 */
	public static int getMaxPoolSize(String name) {
		return CONFIG.getInt(getKey(name, "maxPoolSize"), 100);
	}

	/**
	 * 最大连接数
	 * @param name 名称
	 * @return 最大连接数
	 */
	public static int getMaxSize(String name) {
		return CONFIG.getInt(getKey(name, "maxSize"), 200);
	}

	/**
	 * 超时等待时间 毫秒
	 * @param name 名称
	 * @return 超时等待时间
	 */
	public static int getTimeout(String name) {
		return CONFIG.getInt(getKey(name, "timeout"), 10000);
	}

	/**
	 * 获得测试空闲连接时间 超出时间回收
	 * @param name 名称
	 * @return 测试空闲连接时间
	 */
	public static int getMaxIdleTime(String name) {
		return CONFIG.getInt(getKey(name, "maxIdleTime"), 3600000);
	}

	/**
	 * 获得多长时间检查一次空闲连接
	 * @param name 名称
	 * @return int
	 */
	public static int getIdleTime(String name) {
		return CONFIG.getInt(getKey(name, "idleTimeout"), 1800000);
	}

	/**
	 * 数据库用户名
	 * @param name 名称
	 * @return 用户名
	 */
	public static String[] getPackages(String name) {
		return CONFIG.getStringArray(getKey(name, "packages"),
				new String[] { "com.weicoder.*.po" });
	}

	/**
	 * 是否显示sql语句
	 * @param name 名称
	 * @return 是否
	 */
	public static boolean getSql(String name) {
		return CONFIG.getBoolean(getKey(name, "sql"), false);
	}

	/**
	 * 读取数量
	 * @param name 名称
	 * @return 数量
	 */
	public static int getBatch(String name) {
		return CONFIG.getInt(getKey(name, "batch"), 50);
	}

	/**
	 * 写入数量
	 * @param name 名称
	 * @return 数量
	 */
	public static int getFetch(String name) {
		return CONFIG.getInt(getKey(name, "fetch"), 50);
	}

	/**
	 * 是否使用Lucene Search
	 * @param name 名称
	 * @return 是否
	 */
	public static boolean isSearchPower(String name) {
		return CONFIG.getBoolean(getKey(name, "search.power"), false);
	}

	/**
	 * 索引保存目录
	 * @param name 名称
	 * @return 目录
	 */
	public static String getSearchBase(String name) {
		return CONFIG.getString(getKey(name, "search.base"), "${path}/WEB-INF/indexed");
	}

	/**
	 * Lucene保存索引系统 默认filesystem
	 * @param name 名称
	 * @return 目录
	 */
	public static String getSearchDirectory(String name) {
		return CONFIG.getString(getKey(name, "search.directory"), "filesystem");
	}

	/**
	 * 获得Lucene版本
	 * @param name 名称
	 * @return 版本
	 */
	public static String getSearchVersion(String name) {
		return CONFIG.getString(getKey(name, "search.version"), "LUCENE_36");
	}

	/**
	 * 根据后缀和和名称获得键
	 * @param suffix 后缀
	 * @param name 名称
	 * @return 替换后的键
	 */
	public static String getKey(String name, String suffix) {
		return U.E.isEmpty(name) ? suffix : name + C.S.POINT + suffix;
	}

	private DaoParams() {}
}
