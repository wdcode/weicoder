package com.weicoder.web.socket;

/**
 * Socket 客户端
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-19
 */
public interface Client extends Socket {
	/**
	 * 连接到服务器
	 */
	void connect();

	/**
	 * 写入数据
	 * @param id 指令
	 * @param message 消息
	 */
	void send(short id, Object message);

	/**
	 * 写入数据
	 * @param id 指令
	 * @param message 消息
	 */
	void send(Object message);
}
