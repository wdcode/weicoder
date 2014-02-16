package com.weicoder.web.socket.base;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.web.socket.interfaces.Client;
import com.weicoder.web.socket.interfaces.Session;

/**
 * 基础Client
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-30
 */
public abstract class BaseClient extends BaseSocket implements Client {
	// Session
	protected Session	session;

	/**
	 * 构造
	 * @param name
	 */
	public BaseClient(String name) {
		super(name);
	}

	@Override
	public void send(int id, Object message) {
		getSession().send(id, message);
	}

	@Override
	public void send(Object message) {
		getSession().send(message);
	}

	/**
	 * 获得session
	 * @return
	 */
	protected Session getSession() {
		// 如果session为空 或 未连接
		if (session == null || session.isClose() || !session.isConnect()) {
			// 连接
			connect();
		}
		// 返回session
		return session;
	}

	/**
	 * 设置 Session
	 * @param session Session
	 */
	protected void setSession(Session session) {
		this.session = session;
		manager.register(StringConstants.EMPTY, session.getId(), session);
		// 心跳出来不为空
		if (heart != null) {
			heart.add(session);
		}
	}
}
