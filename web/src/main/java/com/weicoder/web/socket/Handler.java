package com.weicoder.web.socket;

import com.weicoder.web.socket.manager.Manager;



/**
 * Socket 处理器
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-28
 */
public interface Handler<E> {
	/**
	 * 处理消息ID
	 * @return
	 */
	short getId();

	/**
	 * 处理器
	 * @param session Socket Session
	 * @param data 传送的数据
	 * @param manager Session管理器 可以注册获得Session
	 */
	void handler(Session session, E data, Manager manager);
}
