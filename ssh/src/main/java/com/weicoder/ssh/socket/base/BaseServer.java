package com.weicoder.ssh.socket.base;

import com.weicoder.ssh.socket.Server;

/**
 * 基础Server
 * @author WD 
 * @version 1.0  
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
