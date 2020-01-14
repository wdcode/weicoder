package com.weicoder.socket.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.lang.C;
import com.weicoder.common.params.Params;

/**
 * Socket读取配置
 * @author WD
 */
public final class SocketParams {
	/** socket配置文件 */
	private final static Config	CONFIG			= ConfigFactory.getConfig("socket");
	/** 获得Socket检测时间 单位秒 */
	public final static int		TIME			= CONFIG.getInt("time", 10);
	/** 获得Socket超时时间 单位秒 */
	public final static int		TIMEOUT			= CONFIG.getInt("timeout", Params.getInt("socket.timeout", 60));
	/** 设置socket连接池大小 */
	public final static int		POOL			= CONFIG.getInt("pool", Params.getInt("socket.pool", SystemConstants.CPU_NUM * 2));
	/** 是否支持心跳检测 */
	public final static boolean	HEART			= CONFIG.getBoolean("heart", true);
	/** 心跳检测id */
	public final static short	HEART_ID		= CONFIG.getShort("heart.id", C.toShort(0));
	/** 获得Socket服务器监听端口号 */
	public final static int		SERVER_PORT		= CONFIG.getInt("server.port", Params.getInt("socket.server.port"));
	/** 获得WebSocket服务器监听端口号 */
	public final static int		WEBSOCKET_PORT	= CONFIG.getInt("websocket.port", Params.getInt("socket.websocket.port"));
	/** 获得Socket客户端连接端口号 */
	public final static int		CLINET_PORT		= CONFIG.getInt("client.port", Params.getInt("socket.client.port"));
	/** 获得Socket客户端连接host */
	public final static String	CLINET_HOST		= CONFIG.getString("client.host", Params.getString("socket.client.host"));

	private SocketParams() {
	}
}
