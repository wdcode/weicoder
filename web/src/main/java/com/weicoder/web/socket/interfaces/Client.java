package com.weicoder.web.socket.interfaces;



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
	 * 获得Session
	 * @return Session
	 */
	Session getSession();
}
