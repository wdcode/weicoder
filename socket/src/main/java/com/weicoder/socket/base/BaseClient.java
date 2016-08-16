package com.weicoder.socket.base;

import com.weicoder.socket.Client;
import com.weicoder.socket.Session;

/**
 * 基础Client
 * @author WD
 */
public abstract class BaseClient extends BaseSocket implements Client {
	// Session
	protected Session session;

	/**
	 * 构造
	 * @param name 名称
	 */
	public BaseClient(String name) {
		super(name);
	}

	@Override
	public Session session() {
		// 如果session为空 或 未连接
		if (session == null) {
			// 连接
			connect();
		}
		// 返回session
		return session;
	}
}
