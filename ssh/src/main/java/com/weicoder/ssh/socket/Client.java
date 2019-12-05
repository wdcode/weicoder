package com.weicoder.ssh.socket;

/**
 * Socket 客户端
 * @author WD 
 * @version 1.0 
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
