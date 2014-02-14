package com.weicoder.core.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.params.Params;
import com.weicoder.common.util.ArrayUtil; 

/**
 * MemCache配置读取
 * @author WD
 * @since JDK7
 * @version 1.0 2011-05-26
 */
public final class MemcacheParams {
	/**
	 * 集群发送名称服务器
	 */
	public final static String[]	NAMES	= Params.getStringArray("memcache.names", ArrayConstants.STRING_EMPTY);

	/**
	 * 获得MemCache是否使用binary(二进制协议)<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.binary = ? <br/>
	 * XML: {@literal <memcache><binary>?</binary></memcache>}</h2>
	 * @return MemCache是否使用binary(二进制协议)
	 */
	public static boolean getBinary(String name) {
		return Params.getBoolean(getKey(name, "binary"), false);
	}

	/**
	 * 获得MemCache使用的包<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.parse = ? <br/>
	 * XML: {@literal <memcache><parse>?</parse></memcache>}</h2>
	 * @return MemCache使用的包
	 */
	public static String getParse(String name) {
		return Params.getString(getKey(name, "parse"), "java");
	}

	/**
	 * 获得MemCached服务器<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.server = ? <br/>
	 * XML: {@literal <memcache><server>?</server></memcache>}</h2>
	 * @return 获得MemCached服务器
	 */
	public static String[] getServers(String name) {
		return Params.getStringArray(getKey(name, "server"), new String[] { "127.0.0.1:11211" });
	}

	/**
	 * 获得MemCached权重<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.weight = ? <br/>
	 * XML: {@literal <memcache><weight>?</weight></memcache>}</h2>
	 * @return 获得MemCached权重
	 */
	public static Integer[] getWeights(String name) {
		return ArrayUtil.toInteger(Params.getStringArray(getKey(name, "weight"), new String[] { "1" }));
	}

	/**
	 * 初始MemCached连接<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.initConn = ? <br/>
	 * XML: {@literal <memcache><initConn>?</initConn></memcache>}</h2>
	 * @return 初始MemCached连接
	 */
	public static int getInitConn(String name) {
		return Params.getInt(getKey(name, "initConn"), 10);
	}

	/**
	 * MemCached最小连接<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.minConn = ? <br/>
	 * XML: {@literal <memcache><minConn>?</minConn></memcache>}</h2>
	 * @return MemCached最小连接
	 */
	public static int getMinConn(String name) {
		return Params.getInt(getKey(name, "minConn"), 10);
	}

	/**
	 * MemCached最大连接<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.maxConn = ? <br/>
	 * XML: {@literal <memcache><maxConn>?</maxConn></memcache>}</h2>
	 * @return MemCached最大连接
	 */
	public static int getMaxConn(String name) {
		return Params.getInt(getKey(name, "maxConn"), 30);
	}

	/**
	 * MemCached最大空闲时间<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.maxIdle = ? <br/>
	 * XML: {@literal <memcache><maxIdle>?</maxIdle></memcache>}</h2>
	 * @return MemCached最大空闲时间
	 */
	public static long getMaxIdle(String name) {
		return Params.getLong(getKey(name, "maxIdle"), 3000);
	}

	/**
	 * MemCached最大休眠时间<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.sleep = ? <br/>
	 * XML: {@literal <memcache><sleep>?</sleep></memcache>}</h2>
	 * @return MemCached休眠时间
	 */
	public static long getSleep(String name) {
		return Params.getLong(getKey(name, "sleep"), 30);
	}

	/**
	 * MemCached超时时间<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.to = ? <br/>
	 * XML: {@literal <memcache><to>?</to></memcache>}</h2>
	 * @return MemCached超时时间
	 */
	public static int getTO(String name) {
		return Params.getInt(getKey(name, "to"), 3000);
	}

	/**
	 * MemCached连接时间<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: memcache.connectTO = ? <br/>
	 * XML: {@literal <memcache><connectTO>?</connectTO></memcache>}</h2>
	 * @return MemCached连接时间
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

	/**
	 * 私有构造
	 */
	private MemcacheParams() {}
}
