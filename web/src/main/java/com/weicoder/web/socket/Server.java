package com.weicoder.web.socket;

/**
 * Socket 服务器
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-28
 */
public interface Server extends Socket {
	/**
	 * 启动服务器监听
	 */
	void bind();
}
