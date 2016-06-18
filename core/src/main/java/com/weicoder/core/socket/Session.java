package com.weicoder.core.socket;

/**
 * Socket Session
 * @author WD
 */
public interface Session extends AutoCloseable {
	/**
	 * 获得SessionId
	 * @return SessionId
	 */
	int id();

	/**
	 * 写入数据
	 * @param id 指令
	 * @param message 消息
	 */
	byte[] send(short id, Object message);

	/**
	 * 写入数据
	 * @param id 指令
	 * @param message 消息
	 */
	byte[] send(Object message);

	/**
	 * 写入数据
	 * @param data 原始数据
	 */
	byte[] send(byte[] data);

	/**
	 * 写入原始数据 不做其它处理
	 * @param data 原始数据
	 */
	void write(byte[] data);

	/**
	 * 获得连接IP
	 * @return IP
	 */
	String ip();

	/**
	 * 获得连接端口
	 * @return port
	 */
	int port();

	/**
	 * 写入缓存
	 * @param id 指令
	 * @param message 消息
	 */
	byte[] buffer(short id, Object message);

	/**
	 * 写入缓存
	 * @param id 指令
	 * @param message 消息
	 */
	byte[] buffer(Object message);

	/**
	 * 把缓存区的数据一次性写入
	 */
	void flush();
}
