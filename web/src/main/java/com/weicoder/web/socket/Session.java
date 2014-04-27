package com.weicoder.web.socket;

import com.weicoder.common.interfaces.Close;

/**
 * Socket Session
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-28
 */
public interface Session extends Close {
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
	 * 是否连接
	 * @return true 为有连接 false 未连接
	 */
	boolean isConnect();

	/**
	 * 是否关闭
	 * @return true 关闭 false 未关闭
	 */
	boolean isClose();

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
}
