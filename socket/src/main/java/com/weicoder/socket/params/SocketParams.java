package com.weicoder.socket.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.params.Params;

/**
 * Socket读取配置
 * @author WD
 */
public final class SocketParams {
	/** socket配置文件 */
	public final static Config	CONFIG	= ConfigFactory.getConfig("socket");
	/** 获得Socket检测时间 单位秒 */
	public final static int		TIME	= CONFIG.getInt("time", 10);
	/** 获得Socket超时时间 单位秒 */
	public final static int		TIMEOUT	= CONFIG.getInt("timeout", 60);
	/** 设置socket连接池大小 */
	public final static int		POOL	= CONFIG.getInt("pool", SystemConstants.CPU_NUM * 2);
	/** 分组广播数 */
	public final static Boolean	ZIP		= CONFIG.getBoolean("zip", false);

	/**
	 * 获得Socket连接服务器
	 * @param name 名称
	 * @return 服务器
	 */
	public static String getHost(String name) {
		return CONFIG.getString(getKey(name, "host"));
	}

	/**
	 * 获得Socket连接端口
	 * @param name 名称
	 * @return 端口
	 */
	public static int getPort(String name) {
		return CONFIG.getInt(getKey(name, "port"));
	}

	/**
	 * 获得Socket 数据是否压缩 默认false
	 * @param name 名称
	 * @return 是否
	 */
	public static boolean isZip(String name) {
		return CONFIG.getBoolean(getKey(name, "zip"), false);
	}

	/**
	 * 用name替换键
	 * @param name 名称
	 * @param key 键
	 * @return 替换后的键
	 */
	private static String getKey(String name, String key) {
		return Params.getKey(StringConstants.EMPTY, name, key);
	}

	private SocketParams() {}
}
