package com.weicoder.web.socket.base;

import java.util.Map;

import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.Server;
import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.heart.Heart;
import com.weicoder.web.socket.heart.HeartHandler;

/**
 * 基础Server
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-30
 */
public abstract class BaseServer extends BaseSocket implements Server {
	/**
	 * 构造
	 * @param name
	 */
	public BaseServer(String name) {
		super(name);
		// 获得心跳时间
		int heart = SocketParams.getHeartTime(name);
		// 配置了心跳
		if (heart > 0) {
			// 设置心跳
			HeartHandler handler = new HeartHandler(SocketParams.getHeartId(name), heart);
			setHeart(handler);
			addHandler(handler);
		}
	}

	@Override
	public void setHeart(Heart heart) {
		process.setHeart(heart);
	}

	@Override
	public Session getSession(int id) {
		return process.getSession(id);
	}

	@Override
	public Map<Integer, Session> getSessions() {
		return process.getSessions();
	}
}
