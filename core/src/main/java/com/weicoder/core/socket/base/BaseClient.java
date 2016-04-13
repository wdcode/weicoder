package com.weicoder.core.socket.base;

import com.weicoder.common.util.BeanUtil;
import com.weicoder.core.params.SocketParams;
import com.weicoder.core.socket.Client;
import com.weicoder.core.socket.Session;
import com.weicoder.core.socket.message.Login;

/**
 * 基础Client
 * @author WD 
 * @version 1.0  
 */
public abstract class BaseClient extends BaseSocket implements Client {
	// Session
	protected Session session;

	/**
	 * 构造
	 * @param name
	 */
	public BaseClient(String name) {
		super(name);
	}

	@Override
	public Session session() {
		// 如果session为空 或 未连接
		if (session == null || session.isEmpty()) {
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
	protected void session(final Session session) {
		this.session = session;
		// 是否需要登录
		Login login = (Login) BeanUtil.newInstance(SocketParams.getLogin(name));
		if (login != null) {
			session.send(login.id(), login.message());
		}
	}
}
