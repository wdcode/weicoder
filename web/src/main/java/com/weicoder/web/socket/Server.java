package com.weicoder.web.socket;

import java.util.Map;

import com.weicoder.web.socket.heart.Heart;

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
	 * 添加心跳包处理器
	 * @param heart
	 */
	void setHeart(Heart heart);

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
