package com.weicoder.memcache.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.params.Params;
import com.weicoder.common.util.ArrayUtil;

/**
 * MemCache配置读取
 * @author WD
 */
public final class MemcacheParams {
	/**
	 * 集群发送名称服务器
	 */
	public final static String[] NAMES = Params.getStringArray("memcache.names", ArrayConstants.STRING_EMPTY);

	/**
	 * 获得MemCache是否使用binary(二进制协议)
	 * @param name 名称
	 * @return 是否
	 */
	public static boolean getBinary(String name) {
		return Params.getBoolean(getKey(name, "binary"), false);
	}

	/**
	 * 获得MemCache使用的包
	 * @param name 名称
	 * @return 使用的包
	 */
	public static String getParse(String name) {
		return Params.getString(getKey(name, "parse"), "java");
	}

	/**
	 * 获得MemCached服务器
	 * @param name 名称
	 * @return 服务器地址
	 */
	public static String[] getServers(String name) {
		return Params.getStringArray(getKey(name, "server"), new String[] { "127.0.0.1:11211" });
	}

	/**
	 * 获得MemCached权重
	 * @param name 名称
	 * @return 权重
	 */
	public static Integer[] getWeights(String name) {
		return ArrayUtil.toInteger(Params.getStringArray(getKey(name, "weight"), new String[] { "1" }));
	}

	/**
	 * 初始MemCached连接
	 * @param name 名
	 * @return int
	 */
	public static int getInitConn(String name) {
		return Params.getInt(getKey(name, "initConn"), 10);
	}

	/**
	 * MemCached最小连接
	 * @param name 名
	 * @return int
	 */
	public static int getMinConn(String name) {
		return Params.getInt(getKey(name, "minConn"), 10);
	}

	/**
	 * MemCached最大连接
	 * @param name 名
	 * @return int
	 */
	public static int getMaxConn(String name) {
		return Params.getInt(getKey(name, "maxConn"), 30);
	}

	/**
	 * MemCached最大空闲时间
	 * @param name 名
	 * @return long
	 */
	public static long getMaxIdle(String name) {
		return Params.getLong(getKey(name, "maxIdle"), 3000);
	}

	/**
	 * MemCached最大休眠时间
	 * @param name 名
	 * @return long
	 */
	public static long getSleep(String name) {
		return Params.getLong(getKey(name, "sleep"), 30);
	}

	/**
	 * MemCached超时时间
	 * @param name 名
	 * @return int
	 */
	public static int getTO(String name) {
		return Params.getInt(getKey(name, "to"), 3000);
	}

	/**
	 * MemCached连接时间
	 * @param name 名称
	 * @return 连接时间
	 */
	public static int getConnectTO(String name) {
		return Params.getInt(getKey(name, "connectTO"), 3000);
	}

	/**
	 * 用name替换键
	 * @param name 名称
	 * @param key 键
	 * @return 替换后的键
	 */
	private static String getKey(String name, String key) {
		return Params.getKey("memcache", name, key);
	}

	private MemcacheParams() {}
}
