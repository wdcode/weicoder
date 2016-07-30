package com.weicoder.socket.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.params.Params;

/**
 * Socket读取配置
 * @author WD
 */
public final class SocketParams {
	/** 前缀 */
	private final static String		PREFIX		= "socket";
	/** Socket连接地址 */
	public final static String		HOST		= Params.getString(PREFIX + ".host");
	/** Socket服务器端口 */
	public final static int			PORT		= Params.getInt(PREFIX + ".port");
	/** Socket服务器名称数组 */
	public final static String[]	NAMES		= Params.getStringArray(PREFIX + ".names", new String[] { StringConstants.EMPTY });
	/** Socket服务器名称数组 */
	public final static String[]	REGISTERS	= Params.getStringArray(PREFIX + ".registers", new String[] { StringConstants.EMPTY });
	/** 写缓存间隔时间 */
	public final static long		WRITE		= Params.getLong(PREFIX + ".write", 0);
	/** 分组广播数 */
	public final static int			BROAD		= Params.getInt(PREFIX + ".broad", 10000);

	/**
	 * 获得Socket连接服务器
	 * @param name 名称
	 * @return 服务器
	 */
	public static String getHost(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "host"));
	}

	/**
	 * 获得Socket连接端口
	 * @param name 名称
	 * @return 端口
	 */
	public static int getPort(String name) {
		return Params.getInt(Params.getKey(PREFIX, name, "port"));
	}

	/**
	 * 获得Socket检测拒绝次数
	 * @param name 名称
	 * @return 次数
	 */
	public static int getOver(String name) {
		return Params.getInt(Params.getKey(PREFIX, name, "over"), 10);
	}

	/**
	 * 获得Socket检测时间 单位秒
	 * @param name 名称
	 * @return 时间
	 */
	public static int getTime(String name) {
		return Params.getInt(Params.getKey(PREFIX, name, "time"));
	}

	/**
	 * 获得Socket 数据是否压缩 默认false
	 * @param name 名称
	 * @return 是否
	 */
	public static boolean isZip(String name) {
		return Params.getBoolean(Params.getKey(PREFIX, name, "zip"), false);
	}

	/**
	 * 获得Socket心跳检测ID指令
	 * @param name 名称
	 * @return 指令
	 */
	public static short getHeartId(String name) {
		return Params.getShort(Params.getKey(PREFIX, name, "heart.id"), (short) 0);
	}

	/**
	 * 获得Socket心跳检测时间 单位秒
	 * @param name 名称
	 * @return 心跳检测时间
	 */
	public static int getHeartTime(String name) {
		return Params.getInt(Params.getKey(PREFIX, name, "heart.time"));
	}

	/**
	 * 是否回心跳包
	 * @param name 名称
	 * @return 是否
	 */
	public static boolean isHeartPack(String name) {
		return Params.getBoolean(Params.getKey(PREFIX, name, "heart.pack"), true);
	}

	/**
	 * 获得Socket是否需要登录 大于0的指令为登录
	 * @param name 名称
	 * @return 登陆
	 */
	public static String getLogin(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "login"));
	}

	/**
	 * 获得socket处理handler包
	 * @param name 名称
	 * @return 包名
	 */
	public static String getPackage(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "package"));
	}

	/**
	 * 获得socket处理handler包
	 * @param name 名称
	 * @return 包
	 */
	public static String[] getPackages(String name) {
		return Params.getStringArray(Params.getKey(PREFIX, name, "packages"), ArrayConstants.STRING_EMPTY);
	}

	/**
	 * 获得socket Session连接处理器
	 * @param name 名称
	 * @return 连接处理器
	 */
	public static String getConnected(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "connected"));
	}

	/**
	 * 获得socket Session关闭处理器
	 * @param name 名称
	 * @return 关闭处理器
	 */
	public static String getClosed(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "closed"));
	}

	private SocketParams() {}
}
