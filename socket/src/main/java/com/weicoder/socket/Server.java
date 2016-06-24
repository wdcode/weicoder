package com.weicoder.socket;

/**
 * Socket 服务器
 * @author WD  
 */
public interface Server extends Socket {
	/**
	 * 启动服务器监听
	 */
	void bind();
}
