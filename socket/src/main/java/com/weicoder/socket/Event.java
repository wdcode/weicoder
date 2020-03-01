package com.weicoder.socket;

import com.weicoder.socket.Session;

/**
 * session 事件处理
 * 
 * @author wudi
 */
public interface Event {
	/**
	 * session连接时做的处理
	 * 
	 * @param session
	 */
	void connected(Session session);

	/**
	 * 关闭时处理
	 * 
	 * @param session
	 */
	void closed(Session session);

	/**
	 * 心跳处理器
	 * 
	 * @param session
	 */
	void heart(Session session);
}
