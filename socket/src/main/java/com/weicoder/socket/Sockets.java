package com.weicoder.socket;

import com.weicoder.socket.client.NettyClient;
import com.weicoder.socket.params.SocketParams;

/**
 * Socket 相关类
 * 
 * @author WD
 */
public final class Sockets {
	// Socket Client 模式
	private static Client client;
	static {
		// 如果客户端为空 初始化
		if (client == null && SocketParams.CLINET_PORT > 0)
			client = new NettyClient("client");
	}

	/**
	 * 获得客户端
	 * 
	 * @return Client
	 */
	public static Client client() {
		return client;
	}

	/**
	 * 写入数据
	 * 
	 * @param id      指令
	 * @param message 消息
	 */
	public static void send(short id, Object message) {
		client.send(id, message);
	}

	/**
	 * 写入缓存 必须调用flush才能确保数据写入
	 * 
	 * @param id      指令
	 * @param message 消息
	 */
	public static void write(short id, Object message) {
		client.write(id, message);
	}

	/**
	 * 把缓存区的数据一次性写入
	 */
	public static void flush() {
		client.flush();
	}

	private Sockets() {
	}
}