package com.weicoder.datasource.params;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.params.Params;
import com.weicoder.common.util.ResourceUtil;
import com.weicoder.common.util.U;

/**
 * MemCache配置读取
 * 
 * @author WD
 */
public final class DataSourceParams {
	// 数据源配置
	private final static Map<String, Config> CONFIGS = Maps.newMap();
	static {
		// 获得配置文件目录下所有配置
		String path = "db/";
		File file = ResourceUtil.newFile(path);
		// 读取配置
		for (String name : file.list()) {
			Properties ps = new Properties();
			try {
				ps.load(ResourceUtil.loadResource(path + name));
			} catch (IOException e) {
			}
			CONFIGS.put(U.S.split(name, "\\.")[0], ConfigFactory.getConfig(ps));
		}
	}

	/**
	 * 获得DataSourceUrl
	 * 
	 * @param name 名
	 * @return 获得DataSourceUrl
	 */
	public static String getUrl(String name) {
		return CONFIGS.get(name).getString(getKey(name, "url"));
	}

	/**
	 * 获得DataSourceUser
	 * 
	 * @param name 名
	 * @return 获得DataSourceUser
	 */
	public static String getUser(String name) {
		return CONFIGS.get(name).getString(getKey(name, "username"));
	}

	/**
	 * 获得DataSourcePassword
	 * 
	 * @param name 名
	 * @return 获得DataSourcePassword
	 */
	public static String getPassword(String name) {
		return CONFIGS.get(name).getString(getKey(name, "password"));
	}

	/**
	 * 最大连接数
	 * 
	 * @param name 名
	 * @return 最大连接数
	 */
	public static int getMaxSize(String name) {
		return CONFIGS.get(name).getInt(getKey(name, "maxSize"), 100);
	}

	/**
	 * 超时等待时间
	 * 
	 * @param name 名
	 * @return 超时等待时间
	 */
	public static long getTimeout(String name) {
		return CONFIGS.get(name).getLong(getKey(name, "timeout"), DateConstants.TIME_MINUTE * 3);
	}

	/**
	 * 测试空闲连接时间超出时间回收
	 * 
	 * @param name 名
	 * @return 测试空闲连接时间
	 */
	public static long getMaxIdleTime(String name) {
		return CONFIGS.get(name).getLong(getKey(name, "maxIdleTime"), DateConstants.TIME_MINUTE * 10);
	}

	/**
	 * 多长时间检查一次空闲连接
	 * 
	 * @param name 名
	 * @return 多长时间检查一次空闲连接
	 */
	public static long getIdleTimeout(String name) {
		return CONFIGS.get(name).getLong(getKey(name, "idleTimeout"), DateConstants.TIME_HOUR * 2);
	}

	/**
	 * 获得DataSource驱动类
	 * 
	 * @param name 名
	 * @return 获得DataSource驱动类
	 */
	public static String getDriver(String name) {
		return CONFIGS.get(name).getString(getKey(name, "driver"), "com.mysql.cj.jdbc.Driver");
	}

	/**
	 * 获得初始化连接数
	 * 
	 * @param name 名
	 * @return 获得初始化连接数
	 */
	public static int getInitialPoolSize(String name) {
		return CONFIGS.get(name).getInt(getKey(name, "initialPoolSize"), 20);
	}

	/**
	 * 连接池最大连接数
	 * 
	 * @param name 名
	 * @return 连接池最大连接数
	 */
	public static int getMaxPoolSize(String name) {
		return CONFIGS.get(name).getInt(getKey(name, "maxPoolSize"), 50);
	}

	/**
	 * 连接池最小连接数
	 * 
	 * @param name 名
	 * @return 连接池最小连接数
	 */
	public static int getMinPoolSize(String name) {
		return CONFIGS.get(name).getInt(getKey(name, "minPoolSize"), 10);
	}

	/**
	 * 用name替换键
	 * 
	 * @param name 名称
	 * @param key  键
	 * @return 替换后的键
	 */
	private static String getKey(String name, String key) {
		return Params.getKey(StringConstants.EMPTY, name, key);
	}

	private DataSourceParams() {
	}
}
