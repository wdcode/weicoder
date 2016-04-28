package com.weicoder.core.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.params.Params;

/**
 * Socket读取配置
 * @author WD 
 * @version 1.0 
 */
public final class SocketParams {
	/** 前缀 */
	private final static String		PREFIX		= "socket";
	/** Socket连接地址 */
	public final static String		HOST		= Params.getString(PREFIX + ".host");
	/** Socket服务器端口 */
	public final static int			PORT		= Params.getInt(PREFIX + ".port");
	/** Socket服务器开关 */
	public final static boolean		POWER		= Params.getBoolean(PREFIX + ".power", false);
	/** Socket服务器开关 */
	public final static boolean		SPRING		= Params.getBoolean(PREFIX + ".spring", false);
	/** Socket服务器名称数组 */
	public final static String[]	NAMES		= Params.getStringArray(PREFIX + ".names", new String[] { StringConstants.EMPTY });
	/** Socket服务器名称数组 */
	public final static String[]	REGISTERS	= Params.getStringArray(PREFIX + ".registers", new String[] { StringConstants.EMPTY });
	/** 写缓存间隔时间 */
	public final static long		WRITE		= Params.getLong(PREFIX + ".write", 0);
	/** 分组广播数 */
	public final static int			BROAD		= Params.getInt(PREFIX + ".broad", 10000);

	/**
	 * 获得Socket线程池数 <br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.pool = ? <br/>
	 * XML: {@literal <socket><pool>?</pool></socket>}</h2>
	 * @return 获得Socket线程池数
	 */
	public static int getPool(String name) {
		return Params.getInt(Params.getKey(PREFIX, name, "pool"), SystemConstants.CPU_NUM);
	}

	/**
	 * 获得Socket使用解析包<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.parse = ? <br/>
	 * XML: {@literal <socket><parse>?</parse></socket>}</h2>
	 * @return 获得Socket使用解析包
	 */
	public static String getParse(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "parse"));
	}

	/**
	 * 获得Socket连接服务器<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.host = ? <br/>
	 * XML: {@literal <socket><host>?</host></socket>}</h2>
	 * @return 获得Socket连接服务器
	 */
	public static String getHost(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "host"));
	}

	/**
	 * 获得Socket连接端口<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.port = ? <br/>
	 * XML: {@literal <socket><port>?</port></socket>}</h2>
	 * @return 获得Socket连接端口
	 */
	public static int getPort(String name) {
		return Params.getInt(Params.getKey(PREFIX, name, "port"));
	}

	/**
	 * 获得Socket检测拒绝次数<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.time = ? <br/>
	 * XML: {@literal <socket><over>?</over></socket>}</h2>
	 * @return 获得Socket检测拒绝次数
	 */
	public static int getOver(String name) {
		return Params.getInt(Params.getKey(PREFIX, name, "over"), 10);
	}

	/**
	 * 获得Socket检测时间 单位秒<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.time = ? <br/>
	 * XML: {@literal <socket><time>?</time></socket>}</h2>
	 * @return 获得Socket检测时间 单位秒
	 */
	public static int getTime(String name) {
		return Params.getInt(Params.getKey(PREFIX, name, "time"), isClient(name) ? 0 : 5);
	}

	/**
	 * 获得Socket 数据是否压缩 默认true<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.zip = ? <br/>
	 * XML: {@literal <socket><zip>?</zip></socket>}</h2>
	 * @return 获得Socket检测时间 单位秒
	 */
	public static boolean getZip(String name) {
		return Params.getBoolean(Params.getKey(PREFIX, name, "zip"), true);
	}

	/**
	 * 获得Socket心跳检测ID指令<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.heart.id = ? <br/>
	 * XML: {@literal <socket><heart><id>?</id></heart></socket>}</h2>
	 * @return 获得Socket心跳检测ID指令
	 */
	public static short getHeartId(String name) {
		return Params.getShort(Params.getKey(PREFIX, name, "heart.id"), (short) 0);
	}

	/**
	 * 获得Socket心跳检测时间 单位秒<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.heart.time = ? <br/>
	 * XML: {@literal <socket><heart><time>?</time></heart></socket>}</h2>
	 * @return 获得Socket心跳检测时间 单位秒
	 */
	public static int getHeartTime(String name) {
		return Params.getInt(Params.getKey(PREFIX, name, "heart.time"));
	}

	/**
	 * 是否回心跳包
	 * @param name
	 * @return
	 */
	public static boolean isHeartPack(String name) {
		return Params.getBoolean(Params.getKey(PREFIX, name, "heart.pack"), true);
	}

	/**
	 * 获得Socket是否需要登录 大于0的指令为登录<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.login.id = ? <br/>
	 * XML: {@literal <socket><login><id>?</id></login></socket>}</h2>
	 * @return 获得Socket是否需要登录 大于0的指令为登录
	 */
	public static String getLogin(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "login"));
	}

	/**
	 * 获得Socket是否客户端 <br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.*.client = ? <br/>
	 * XML: {@literal <socket><*><client>?</client></*></socket>}</h2>
	 * @return socket是否客户端
	 */
	public static boolean isClient(String name) {
		return Params.getBoolean(Params.getKey(PREFIX, name, "client"), false);
	}

	/**
	 * 获得socket处理handler<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.*.handler = ? <br/>
	 * XML: {@literal <socket><*><handler>?</handler></*></socket>}</h2>
	 * @return socket处理handler
	 */
	public static String[] getHandler(String name) {
		return Params.getStringArray(Params.getKey(PREFIX, name, "handler"), ArrayConstants.STRING_EMPTY);
	}

	/**
	 * 获得socket处理handler包<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.*.package = ? <br/>
	 * XML: {@literal <socket><*><package>?</package></*></socket>}</h2>
	 * @return socket处理handler包
	 */
	public static String getPackage(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "package"));
	}

	/**
	 * 获得socket处理handler包<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.*.packages = ? <br/>
	 * XML: {@literal <socket><*><packages>?</packages></*></socket>}</h2>
	 * @return socket处理handler包
	 */
	public static String[] getPackages(String name) {
		return Params.getStringArray(Params.getKey(PREFIX, name, "packages"), ArrayConstants.STRING_EMPTY);
	}

	/**
	 * 获得socket Session连接处理器<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.*.connected = ? <br/>
	 * XML: {@literal <socket><*><connected>?</connected></*></socket>}</h2>
	 * @return socket Session连接处理器
	 */
	public static String getConnected(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "connected"));
	}

	/**
	 * 获得socket Session关闭处理器<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.*.closed = ? <br/>
	 * XML: {@literal <socket><*><closed>?</closed></*></socket>}</h2>
	 * @return socket Session关闭处理器
	 */
	public static String getClosed(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "closed"));
	}

	private SocketParams() {}
}
