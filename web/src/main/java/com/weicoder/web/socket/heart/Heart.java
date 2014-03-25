package com.weicoder.web.socket.heart;

import com.weicoder.web.socket.Session;

/**
 * 心跳协议处理器
 * @author WD
 * @since JDK7
 * @version 1.0 2014-1-24
 */
public interface Heart {
	/**
	 * 添加Session
	 * @param session
	 */
	void add(Session session);

	/**
	 * 删除Session
	 * @param session
	 */
	void remove(Session session);
}
