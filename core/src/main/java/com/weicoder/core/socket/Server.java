package com.weicoder.core.socket;

/**
 * Socket 服务器
 * @author WD 
 * @version 1.0 
 */
public interface Server extends Socket {
	/**
	 * 启动服务器监听
	 */
	void bind();
}
