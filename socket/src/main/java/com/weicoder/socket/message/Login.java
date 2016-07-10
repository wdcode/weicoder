package com.weicoder.socket.message;

/**
 * 登录消息接口 用于Client登录验证
 * @author WD
 */
public interface Login {
	/**
	 * 登录ID
	 * @return 登陆ID
	 */
	short id();

	/**
	 * 登录消息
	 * @return 对象
	 */
	Object message();
}
