package com.weicoder.socket;

import com.weicoder.socket.impl.netty.NettyClient;
import com.weicoder.socket.impl.netty.NettyServer;
import com.weicoder.socket.manager.Manager;

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

	/**
	 * 初始化Mina
	 */
	public static void init() {
		// 初始化 客户端
		client = new NettyClient("client");
		// 初始化 服务端
		server = new NettyServer("server");
		// 设置管理器
		manager = new Manager();
		// 启动服务器
		if (server != null) {
			server.bind();
		}
		// 启动客户端
		if (client != null) {
			client.connect();
		}
	}

	/**
	 * 广播消息
	 * @param id 发送指令
	 * @param message 发送消息
	 */
	public static void send(short id, Object message) {
		// 循环发送消息
		for (String key : manager.keys()) {
			send(key, id, message);
		}
	}

	/**
	 * 广播消息
	 * @param key 注册键
	 * @param id 发送指令
	 * @param message 发送消息
	 */
	public static void send(String key, short id, Object message) {
		// 循环发送消息
		for (Session session : manager.sessions(key)) {
			session.send(id, message);
		}
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

	private Sockets() {}
}