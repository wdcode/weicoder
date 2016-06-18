package com.weicoder.core.socket.base;

import com.weicoder.core.socket.Closed;
import com.weicoder.core.socket.Connected;
import com.weicoder.core.socket.Handler;
import com.weicoder.core.socket.Socket;
import com.weicoder.core.socket.process.Process;

/**
 * 基础Socket
 * @author WD
 */
public abstract class BaseSocket implements Socket {
	// 名称
	protected String	name;
	// 消息处理器
	protected Process	process;

	/**
	 * 构造
	 * @param name 名称
	 */
	public BaseSocket(String name) {
		this.name = name;
		process = new Process(name);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public void addHandler(Handler<?> handler) {
		process.addHandler(handler);
	}

	@Override
	public void connected(Connected connected) {
		process.connected(connected);
	}

	@Override
	public void closed(Closed closed) {
		process.closed(closed);
	}
}