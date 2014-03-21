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
	int getId();

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

	/**
	 * 写入数据
	 * @param id 指令
	 * @param message 消息
	 */
	void send(byte[] message);

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
	String getIp();

	/**
	 * 获得连接端口
	 * @return port
	 */
	int getPort();
}
