package com.weicoder.web.socket.base;

import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ScheduledUtile;
import com.weicoder.core.log.Logs;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.Client;
import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.message.Login;

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
	public void send(short id, Object message) {
		session().send(id, message);
	}

	@Override
	public void send(Object message) {
		session().send(message);
	}

	/**
	 * 获得session
	 * @return
	 */
	protected Session session() {
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
	protected void session(final Session session) {
		this.session = session;
		// 是否需要登录
		Login login = (Login) BeanUtil.newInstance(SocketParams.getLogin(name));
		if (login != null) {
			send(login.id(), login.message());
		}
		// 是否启动心跳
		int heart = SocketParams.getHeartTime(name);
		if (heart > 0) {
			// 心跳指令
			final short id = SocketParams.getHeartId(name);
			// 定时发送心跳信息
			ScheduledUtile.delay(new Runnable() {
				@Override
				public void run() {
					// 循环发送心跳信息
					session.send(id);
					Logs.debug("send heart session=" + session.id());
				}
			}, heart);
		}
	}
}
