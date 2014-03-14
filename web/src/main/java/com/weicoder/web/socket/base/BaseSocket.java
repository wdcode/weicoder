package com.weicoder.web.socket.base;

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

	/**
	 * 构造
	 */
	public BaseSocket(String name) {
		this.name = name;
		manager = new SessionManager();
		process = new Processor(manager);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addHandler(Handler<?> handler) {
		process.addHandler(handler);
	}

	@Override
	public void addClosed(Closed closed) {
		process.addClosed(closed);
	}

	@Override
	public Manager getManager() {
		return manager;
	}

	@Override
	public void setHeart(Heart heart) {
		process.setHeart(heart);
	}
}