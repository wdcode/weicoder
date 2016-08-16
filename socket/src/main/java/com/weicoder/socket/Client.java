package com.weicoder.socket;

/**
 * Socket 客户端
 * @author WD
 */
public interface Client {
	/**
	 * 连接到服务器
	 */
	void connect();

	/**
	 * 获得客户端Session
	 * @return Session
	 */
	Session session();
}