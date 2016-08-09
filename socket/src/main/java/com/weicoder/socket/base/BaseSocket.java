package com.weicoder.socket.base;

import com.weicoder.socket.Socket;
import com.weicoder.socket.process.Process;

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
}