package com.weicoder.core.socket.message;

/**
 * 登录消息接口 用于Client登录验证
 * @author WD 
 * @version 1.0 
 */
public interface Login {
	/**
	 * 登录ID
	 * @return
	 */
	short id();

	/**
	 * 登录消息
	 * @return
	 */
	Object message();
}
