package com.weicoder.web.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.params.Params;

/**
 * Socket读取配置
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-07
 */
public final class SocketParams {
	/** 前缀 */
	private final static String		PREFIX		= "socket";
	/** Mina服务器开关 */
	public final static boolean		POWER		= Params.getBoolean(PREFIX + ".power", false);
	/** Mina服务器开关 */
	public final static boolean		SPRING		= Params.getBoolean(PREFIX + ".spring", false);
	/** Mina服务器名称数组 */
	public final static String[]	NAMES		= Params.getStringArray(PREFIX + ".names", new String[] { StringConstants.EMPTY });
	/** Mina服务器名称数组 */
	public final static String[]	REGISTERS	= Params.getStringArray(PREFIX + ".registers", new String[] { StringConstants.EMPTY });
	/** 写缓存间隔时间 */
	public final static long		WRITE		= Params.getLong(PREFIX + ".write", 0);
	/** 线程池数 */
	public final static int			POOL		= Params.getInt(PREFIX + ".pool", SystemConstants.CPU_NUM + 1);
	/** 线程池数 */
	public final static int			BROAD		= Params.getInt(PREFIX + ".broad", 1000);

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
	 * 获得Socket类型server或则client 只有host和type=client是才是客户端<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: socket.*.type = ? <br/>
	 * XML: {@literal <socket><*><type>?</type></*></socket>}</h2>
	 * @return socket客户端连接服务器
	 */
	public static String getType(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "type"));
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

	/**
	 * 私有构造
	 */
	private SocketParams() {}
}
