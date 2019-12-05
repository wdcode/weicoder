package com.weicoder.ssh.socket.base;

import com.weicoder.common.util.BeanUtil;
import com.weicoder.ssh.params.SocketParams;
import com.weicoder.ssh.socket.Client;
import com.weicoder.ssh.socket.Session;
import com.weicoder.ssh.socket.message.Login;

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
