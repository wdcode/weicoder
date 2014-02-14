package com.weicoder.web.socket.interfaces;

import java.util.Map;


/**
 * Socket 服务器
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-28
 */
public interface Server extends Socket {
	/**
	 * 启动服务器监听
	 */
	void bind();

	/**
	 * 根据ID获得session
	 * @param id SessionId
	 * @return Session
	 */
	Session getSession(int id);

	/**
	 * 获得Session列表
	 * @return Session列表
	 */
	Map<Integer, Session> getSessions();
}
