package com.weicoder.web.socket.process;

import java.util.Map;

import com.weicoder.web.socket.Closed;
import com.weicoder.web.socket.Handler;
import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.heart.Heart;

/**
 * Socket 数据处理接口
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-22
 */
public interface Process {
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

	/**
	 * 设置心跳包处理器
	 * @param heart
	 */
	void setHeart(Heart heart);

	/**
	 * 设置关闭处理器
	 * @param closed 关闭处理器
	 */
	void setClosed(Closed closed);

	/**
	 * 添加要处理的Handler
	 * @param handler
	 */
	void addHandler(Handler<?> handler);

	/**
	 * Session连接时
	 * @param session
	 */
	void connected(Session session);

	/**
	 * Session关闭时
	 * @param session
	 */
	void closed(Session session);

	/**
	 * 处理数据
	 * @param session Session
	 * @param data 字节流
	 */
	void process(Session session, byte[] message);
}
