package com.weicoder.web.socket.base;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.ScheduledUtile;
import com.weicoder.core.log.Logs;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.Client;
import com.weicoder.web.socket.Session;

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
	protected void setSession(final Session session) {
		this.session = session;
		manager.register(StringConstants.EMPTY, session.getId(), session);
		// 是否启动心跳
		int heart = SocketParams.getHeartTime(name);
		if (heart > 0) {
			// 心跳指令
			final short id = SocketParams.getHeartId(name);
			// 定时发送心跳信息
			ScheduledUtile.rate(new Runnable() {
				@Override
				public void run() {
					// 循环发送心跳信息
					session.send(id, null);
					Logs.debug("send heart session=" + session.getId());
				}
			}, heart);
		}
	}
}
