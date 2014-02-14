package com.weicoder.web.socket.base;

import com.weicoder.common.util.BeanUtil;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.interfaces.Closed;
import com.weicoder.web.socket.interfaces.Handler;
import com.weicoder.web.socket.interfaces.Heart;
import com.weicoder.web.socket.interfaces.Manager;
import com.weicoder.web.socket.interfaces.Process;
import com.weicoder.web.socket.interfaces.Socket;
import com.weicoder.web.socket.simple.Processor;
import com.weicoder.web.socket.simple.SessionManager;

/**
 * 基础Socket
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-30
 */
public abstract class BaseSocket implements Socket {
	// 名称
	protected String	name;
	// 消息处理器
	protected Process	process;
	// 注册Session
	protected Manager	manager;
	// 心跳处理
	protected Heart		heart;

	/**
	 * 构造
	 */
	public BaseSocket(String name) {
		this.name = name;
		manager = new SessionManager();
		process = new Processor(manager);
		// 获得关闭处理器
		Closed closed = (Closed) BeanUtil.newInstance(SocketParams.getClosed(name));
		if (closed != null) {
			process.setClosed(closed);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addHandler(Handler<?>... h) {
		process.addHandler(h);
	}

	@Override
	public Manager getManager() {
		return manager;
	}

	@Override
	public void setHeart(Heart heart) {
		this.heart = heart;
		process.setHeart(heart);
	}
}