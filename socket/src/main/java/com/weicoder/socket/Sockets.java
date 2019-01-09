package com.weicoder.socket;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.StringUtil;
import com.weicoder.protobuf.Protobuf;
import com.weicoder.protobuf.ProtobufEngine;
import com.weicoder.socket.client.NettyClient;
import com.weicoder.socket.manager.Manager;
import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.server.TcpServer;
import com.weicoder.socket.server.WebSocketServer;

/**
 * Socket 相关类
 * @author WD
 */
public final class Sockets {
	// Socket Client 模式
	private static Client	client;
	// Manager Session管理器 一般给Server使用
	private static Manager	manager;

	/**
	 * 初始化Mina
	 */
	public static void init() {
		// 初始化管理器
		manager = new Manager();
		// 初始化 客户端
		if (SocketParams.CLINET_PORT > 0) {
			client = new NettyClient("client");
			client.connect();
		}
		// 初始化 tcp服务端
		if (SocketParams.SERVER_PORT > 0)
			new TcpServer().bind();
		// 初始化 websocket服务端
		if (SocketParams.WEBSOCKET_PORT > 0)
			new WebSocketServer().bind();

	}

	/**
	 * 获得客户端
	 * @return Client
	 */
	public static Client client() {
		return client;
	}

	/**
	 * 获得客户端
	 * @return Client
	 */
	public static Manager manager() {
		return manager;
	}

	/**
	 * 包装数据
	 * @param id 指令
	 * @param message 消息
	 * @return 字节数组
	 */
	public static byte[] pack(short id, Object message) {
		// 声明字节数组
		byte[] data = toBytes(message);
		// 返回数据
		return Bytes.toBytes(Conversion.toShort(data.length + 2), id, data);
	}

	/**
	 * 包装数据
	 * @param message 消息
	 * @return 字节数组
	 */
	public static byte[] pack(Object message) {
		// 声明字节数组
		byte[] data = toBytes(message);
		// 返回数据
		return Bytes.toBytes(Conversion.toShort(data.length), data);
	}

	/**
	 * 转换message为字节数组
	 * @param message 消息
	 * @return 字节数组
	 */
	public static byte[] toBytes(Object message) {
		// 判断类型
		if (message == null)
			// 空
			return ArrayConstants.BYTES_EMPTY;
		else if (message instanceof String)
			// 字符串
			return StringUtil.toBytes(Conversion.toString(message));
		else if (message.getClass().isAnnotationPresent(Protobuf.class))
			// 字符串
			return ProtobufEngine.toBytes(message);
		else
			// 不知道的类型 以字节数组发送
			return Bytes.toBytes(message);
	}

	private Sockets() {
	}
}