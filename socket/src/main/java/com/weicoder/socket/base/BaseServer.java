package com.weicoder.socket.base;

import com.weicoder.socket.Server;

/**
 * 基础Server
 * @author WD
 */
public abstract class BaseServer extends BaseSocket implements Server {
	/**
	 * 构造
	 * @param name
	 */
	public BaseServer(String name) {
		super(name);
	}
}
