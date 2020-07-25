package com.weicoder.influxdb.params;

import com.weicoder.common.params.Params;

/**
 * Influx使用参数
 * 
 * @author wdcode
 *
 */
public final class InfluxParams {
	/**
	 * Influx服务器地址
	 * 
	 * @param name 名
	 * @return
	 */
	public static String getUrl(String name) {
		return Params.getString(getKey(name, "url"));
	}

	/**
	 * Influx密码
	 * 
	 * @param name 名
	 * @return
	 */
	public static String getPassword(String name) {
		return Params.getString(getKey(name, "password"));
	}

	/**
	 * Influx用户名
	 * 
	 * @param name 名
	 */
	public static String getUsername(String name) {
		return Params.getString(getKey(name, "username"));
	}

	/**
	 * Influx数据库
	 * 
	 * @param name 名
	 */
	public static String getDatabase(String name) {
		return Params.getString(getKey(name, "database"));
	}

	/**
	 * Influx 策略
	 * 
	 * @param name 名
	 * @return
	 */
	public static String getPolicy(String name) {
		return Params.getString(getKey(name, "policy"));
	}

	/**
	 * 用name替换键
	 * 
	 * @param name 名称
	 * @param key  键
	 * @return 替换后的键
	 */
	private static String getKey(String name, String key) {
		return Params.getKey("influx", name, key);
	}

	private InfluxParams() {
	}
}
