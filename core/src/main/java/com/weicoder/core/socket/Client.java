package com.weicoder.core.socket;

/**
 * Socket 客户端
 * @author WD  
 */
public interface Client extends Socket {
	/**
	 * 连接到服务器
	 */
	void connect();

	/**
	 * 获得客户端Session
	 * @return
	 */
	Session session();
}
