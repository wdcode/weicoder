package com.weicoder.web.socket;

import java.util.Map;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.impl.mina.MinaClient;
import com.weicoder.web.socket.impl.mina.MinaServer;
import com.weicoder.web.socket.impl.netty.NettyClient;
import com.weicoder.web.socket.impl.netty.NettyServer;
import com.weicoder.web.socket.impl.netty3.Netty3Client;
import com.weicoder.web.socket.impl.netty3.Netty3Server;
import com.weicoder.web.socket.manager.Manager;
import com.weicoder.web.socket.message.Message;
import com.weicoder.web.socket.message.Null;

/**
 * Socket 相关类
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-7
 */
public final class Sockets {
	// 保存SocketServer
	private final static Map<String, Server>	SERVERS;
	// 保存SocketClient
	private final static Map<String, Client>	CLIENTS;

	static {
		SERVERS = Maps.getConcurrentMap();
		CLIENTS = Maps.getConcurrentMap();
	}

	/**
	 * 初始化Mina
	 */
	public static void init() {
		// 判断任务不为空
		if (SocketParams.POWER) {
			// 循环数组
			for (String name : SocketParams.NAMES) {
				init(name);
			}
			// 启动服务器
			start();
		}
	}

	/**
	 * 根据名称设置
	 * @param name 名
	 */
	public static Socket init(String name) {
		// Socket
		Socket socket = null;
		// 判断是否客户端
		if (!EmptyUtil.isEmpty(SocketParams.getHost(name)) && "client".equals(SocketParams.getType(name))) {
			socket = addClient(name);
		} else {
			socket = addServer(name);
		}
		// 设置Handler
		for (String c : SocketParams.getHandler(name)) {
			socket.addHandler((Handler<?>) BeanUtil.newInstance(c));
		}
		// 按包处理
		for (String p : SocketParams.getPackages(name)) {
			// Handler
			for (Class<?> c : ClassUtil.getAssignedClass(p, Handler.class)) {
				socket.addHandler((Handler<?>) BeanUtil.newInstance(c));
			}
		}
		// 设置关闭处理器
		socket.setClosed((Closed) BeanUtil.newInstance(SocketParams.getClosed(name)));
		// 返回Socket
		return socket;
	}

	/**
	 * 添加服务器
	 * @param name 名称
	 */
	public static Server addServer(String name) {
		return addServer(getServer(name));
	}

	/**
	 * 添加服务器
	 * @param name 名称
	 */
	public static Server addServer(Server server) {
		SERVERS.put(server.getName(), server);
		return server;
	}

	/**
	 * 添加客户端
	 * @param name 名称
	 */
	public static Client addClient(String name) {
		return addClient(getClient(name));
	}

	/**
	 * 添加客户端
	 * @param name 名称
	 */
	public static Client addClient(Client client) {
		CLIENTS.put(client.getName(), client);
		return client;
	}

	/**
	 * 广播消息
	 * @param key 注册键
	 * @param id 发送指令
	 * @param message 发送消息
	 */
	public static void send(short id, Object message) {
		// 循环发送
		for (String name : SERVERS.keySet()) {
			send(name, id, message);
		}
	}

	/**
	 * 广播消息
	 * @param name 注册键
	 * @param id 发送指令
	 * @param message 发送消息
	 */
	public static void send(String name, short id, Object message) {
		// 循环发送消息
		for (String key : manager(name).keys()) {
			send(name, key, id, message);
		}
	}

	/**
	 * 广播消息
	 * @param key 注册键
	 * @param id 发送指令
	 * @param message 发送消息
	 */
	public static void send(String name, String key, short id, Object message) {
		// 循环发送消息
		for (Session session : manager(name).sessions(key)) {
			session.send(id, message);
		}
	}

	/**
	 * 获得服务器
	 * @param name
	 * @return
	 */
	public static Server server(String name) {
		return SERVERS.get(name);
	}

	/**
	 * 获得服务器
	 * @return
	 */
	public static Server server() {
		return server(StringConstants.EMPTY);
	}

	/**
	 * 获得服务器Session管理器
	 * @param name
	 * @return
	 */
	public static Manager manager(String name) {
		return server(name).getManager();
	}

	/**
	 * 获得服务器
	 * @return
	 */
	public static Manager manager() {
		return server().getManager();
	}

	/**
	 * 获得客户端
	 * @param name
	 * @return
	 */
	public static Client client(String name) {
		return CLIENTS.get(name);
	}

	/**
	 * 获得客户端
	 * @return
	 */
	public static Client client() {
		return client(StringConstants.EMPTY);
	}

	/**
	 * 启动Socket
	 */
	public static void start() {
		// 启动服务器
		for (Server server : SERVERS.values()) {
			server.bind();
		}
		// 启动客户端
		for (Client client : CLIENTS.values()) {
			client.connect();
		}
	}

	/**
	 * 关闭所有连接
	 */
	public static void close() {
		for (String name : SERVERS.keySet()) {
			closeServer(name);
		}
		for (String name : CLIENTS.keySet()) {
			closeClient(name);
		}
	}

	/**
	 * 关闭客户端
	 * @param name 要关闭的Client 名 关闭所有连接
	 */
	public static void closeClient(String name) {
		// 获得Client
		Client client = CLIENTS.get(name);
		// 判断acceptor不为空
		if (!EmptyUtil.isEmpty(client)) {
			// 关闭Session
			client.close();
			// 删除Map中的引用
			CLIENTS.remove(name);
		}
	}

	/**
	 * 关闭服务器
	 * @param name 要关闭的Server 名 关闭所有连接
	 */
	public static void closeServer(String name) {
		// 获得Server
		Server server = SERVERS.get(name);
		// 判断acceptor不为空
		if (!EmptyUtil.isEmpty(server)) {
			// 关闭server
			server.close();
			// 删除Map中的引用
			SERVERS.remove(name);
		}
	}

	/**
	 * 包装数据
	 * @param id 指令
	 * @param message 消息
	 * @return 字节数组
	 */
	public static byte[] pack(short id, Object message) {
		// 声明字节数组
		byte[] data = toByte(message);
		// 返回数据
		return Bytes.toBytes(data.length + 2, id, data);
	}

	/**
	 * 包装数据
	 * @param message 消息
	 * @return 字节数组
	 */
	public static byte[] pack(Object message) {
		// 声明字节数组
		byte[] data = toByte(message);
		// 返回数据
		return Bytes.toBytes(data.length, data);
	}

	/**
	 * 转换message为字节数组
	 * @param message
	 * @return
	 */
	private static byte[] toByte(Object message) {
		// 声明字节数组
		byte[] data = null;
		// 判断类型
		if (message == null) {
			// 空
			data = ArrayConstants.BYTES_EMPTY;
		} else if (message instanceof Null) {
			// 空
			data = ArrayConstants.BYTES_EMPTY;
		} else if (message instanceof String) {
			// 字符串
			data = StringUtil.toBytes(Conversion.toString(message));
		} else if (message instanceof Message) {
			// 消息体
			data = ((Message) message).array();
		} else {
			// 不知道的类型 以字节数组发送
			data = Bytes.toBytes(message);
		}
		// 返回字节数组
		return data;
	}

	/**
	 * 获得服务器
	 * @param name 服务器配置名
	 * @return Server
	 */
	private static Server getServer(String name) {
		switch (SocketParams.getParse(name)) {
			case "netty":
				return new NettyServer(name);
			case "netty3":
				return new Netty3Server(name);
			default:
				// 默认mina
				return new MinaServer(name);
		}
	}

	/**
	 * 获得客户端
	 * @param name 客户端配置名
	 * @return Client
	 */
	private static Client getClient(String name) {
		switch (SocketParams.getParse(name)) {
			case "netty":
				return new NettyClient(name);
			case "netty3":
				return new Netty3Client(name);
			default:
				// 默认mina
				return new MinaClient(name);
		}
	}

	private Sockets() {}
}