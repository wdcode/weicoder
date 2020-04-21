package com.weicoder.socket;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.StringUtil;
import com.weicoder.common.zip.ZipEngine;
import com.weicoder.core.protobuf.Protobuf;
import com.weicoder.core.protobuf.ProtobufEngine;
import com.weicoder.socket.manager.Manager;
import com.weicoder.socket.netty.NettyClient;
import com.weicoder.socket.netty.NettyServer;
import com.weicoder.socket.params.SocketParams;

/**
 * Socket 相关类
 * @author WD
 */
public final class Sockets {
	// Socket Server 模式
	private static Server	server;
	// Socket Client 模式
	private static Client	client;
	// Manager Session管理器 一般给Server使用
	private static Manager	manager;
	// 是否使用压缩
	private static boolean	zip;

	/**
	 * 初始化Mina
	 */
	public static void init() {
		// 获得是否压缩
		zip = SocketParams.ZIP;
		// 初始化 客户端
		if (SocketParams.CONFIG.exists("client.host"))
			client = new NettyClient("client");
		// 初始化 服务端
		if (SocketParams.CONFIG.exists("server.port")) {
			server = new NettyServer("server");
			// 设置管理器
			manager = new Manager();
		}
		// 启动服务器
		if (server != null)
			server.bind();
		// 启动客户端
		if (client != null)
			client.connect();
	}

	/**
	 * 获得服务器
	 * @return 服务器
	 */
	public static Server server() {
		return server;
	}

	/**
	 * 获得服务器
	 * @return Manager
	 */
	public static Manager manager() {
		return manager;
	}

	/**
	 * 获得客户端
	 * @return Client
	 */
	public static Client client() {
		return client;
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
		// 声明字节数组
		byte[] data = null;
		// 判断类型
		if (message == null)
			// 空
			data = ArrayConstants.BYTES_EMPTY;
		else if (message instanceof String)
			// 字符串
			data = StringUtil.toBytes(Conversion.toString(message));
		else if (message.getClass().isAnnotationPresent(Protobuf.class))
			// 字符串
			data = ProtobufEngine.toBytes(message);
		else
			// 不知道的类型 以字节数组发送
			data = Bytes.toBytes(message);
		// 使用压缩并且长度大于一个字节长度返回压缩 不使用直接返回字节数组
		return zip && data.length > Byte.MAX_VALUE ? ZipEngine.compress(data) : data;
	}

	private Sockets() {}
}