package com.weicoder.ssh.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.EmptyUtil; 

/**
 * 读取Dao配置
 * @author WD 
 *   
 */
public final class DaoParams {
	// Properties配置
	private static Config			config	= ConfigFactory.getConfig(FrameParams.DATA_SOURCE_CONFIG);
	/** 多库名称 */
	public final static String[]	NAMES	= config.getStringArray("names", new String[] { StringConstants.EMPTY });

	/**
	 * 使用哪种数据库连接池 现在支持 proxool dbcp c3p0 bonecp druid
	 * @param name 名称
	 * @return
	 */
	public static String getParse(String name) {
		return config.getString(getKey(name, "parse"), "dbcp2");
	}

	/**
	 * hibernate的数据库方言
	 * @param name 名称
	 * @return
	 */
	public static String getDialect(String name) {
		return config.getString(getKey(name, "dialect"), "org.hibernate.dialect.MySQL8Dialect");
	}

	/**
	 * 数据库驱动
	 * @param name 名称
	 * @return
	 */
	public static String getDriver(String name) {
		return config.getString(getKey(name, "driver"), "com.mysql.cj.jdbc.Driver");
	}

	/**
	 * 数据库连接url
	 * @param name 名称
	 * @return
	 */
	public static String getUrl(String name) {
		return config.getString(getKey(name, "url"));
	}

	/**
	 * 数据库用户名
	 * @param name 名称
	 * @return
	 */
	public static String getUser(String name) {
		return config.getString(getKey(name, "user"));
	}

	/**
	 * 数据库密码
	 * @param name 名称
	 * @return
	 */
	public static String getPassword(String name) {
		return config.getString(getKey(name, "password"));
	}

	/**
	 * 初始化连接池数量
	 * @param name 名称
	 * @return
	 */
	public static int getInitialPoolSize(String name) {
		return config.getInt(getKey(name, "initialPoolSize"), 30);
	}

	/**
	 * 最小连接池数量
	 * @param name 名称
	 * @return
	 */
	public static int getMinPoolSize(String name) {
		return config.getInt(getKey(name, "minPoolSize"), 30);
	}

	/**
	 * 最大连接池数量
	 * @param name 名称
	 * @return
	 */
	public static int getMaxPoolSize(String name) {
		return config.getInt(getKey(name, "maxPoolSize"), 100);
	}

	/**
	 * 最大连接数
	 * @param name 名称
	 * @return
	 */
	public static int getMaxSize(String name) {
		return config.getInt(getKey(name, "maxSize"), 200);
	}

	/**
	 * 超时等待时间 毫秒
	 * @param name 名称
	 * @return
	 */
	public static int getTimeout(String name) {
		return config.getInt(getKey(name, "timeout"), 10000);
	}

	/**
	 * 获得测试空闲连接时间 超出时间回收
	 * @param name 名称
	 * @return
	 */
	public static int getMaxIdleTime(String name) {
		return config.getInt(getKey(name, "maxIdleTime"), 3600000);
	}

	/**
	 * 获得多长时间检查一次空闲连接
	 * @param name 名称
	 * @return
	 */
	public static int getIdleTime(String name) {
		return config.getInt(getKey(name, "idleTimeout"), 1800000);
	}

	/**
	 * 数据库用户名
	 * @param name 名称
	 * @return
	 */
	public static String[] getPackages(String name) {
		return config.getStringArray(getKey(name, "packages"), new String[] { "com.weicoder.*.po" });
	}

	/**
	 * 是否显示sql语句
	 * @param name 名称
	 * @return
	 */
	public static boolean getSql(String name) {
		return config.getBoolean(getKey(name, "sql"), false);
	}

	/**
	 * 读取数量
	 * @param name 名称
	 * @return
	 */
	public static int getBatch(String name) {
		return config.getInt(getKey(name, "batch"), 50);
	}

	/**
	 * 写入数量
	 * @param name 名称
	 * @return
	 */
	public static int getFetch(String name) {
		return config.getInt(getKey(name, "fetch"), 50);
	}

	/**
	 * 是否使用Lucene Search
	 * @param name 名称
	 * @return
	 */
	public static boolean isSearchPower(String name) {
		return config.getBoolean(getKey(name, "search.power"), false);
	}

	/**
	 * 索引保存目录
	 * @param name 名称
	 * @return
	 */
	public static String getSearchBase(String name) {
		return config.getString(getKey(name, "search.base"), "${path}/WEB-INF/indexed");
	}

	/**
	 * Lucene保存索引系统 默认filesystem
	 * @param name 名称
	 * @return
	 */
	public static String getSearchDirectory(String name) {
		return config.getString(getKey(name, "search.directory"), "filesystem");
	}

	/**
	 * 获得Lucene版本
	 * @param name 名称
	 * @return
	 */
	public static String getSearchVersion(String name) {
		return config.getString(getKey(name, "search.version"), "LUCENE_36");
	}

	/**
	 * 根据后缀和和名称获得键
	 * @param suffix 后缀
	 * @param name 名称
	 * @return 替换后的键
	 */
	public static String getKey(String name, String suffix) {
		return EmptyUtil.isEmpty(name) ? suffix : name + StringConstants.POINT + suffix;
	}

	private DaoParams() {}
}
