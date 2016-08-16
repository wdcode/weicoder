package com.weicoder.socket.base;

import com.weicoder.socket.process.Process;

/**
 * 基础Socket
 * @author WD
 */
public abstract class BaseSocket {
	// 消息处理器
	protected Process process;

	/**
	 * 构造
	 * @param name 名称
	 */
	public BaseSocket(String name) {
		process = new Process(name);
	}
}