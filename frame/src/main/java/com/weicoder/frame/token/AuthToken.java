package com.weicoder.frame.token;

import com.weicoder.common.binary.BytesBean;

/**
 * 获得当前网站用户 可获得是是否登录 和用户基本信息接口
 * @author WD 
 * @version 1.0  
 */
public interface AuthToken extends BytesBean {
	/**
	 * 是否登录 不验证是IP和登录时间
	 * @return true 登录 false 未登录
	 */
	boolean isLogin();

	/**
	 * 是否登录用户 一般ID>0
	 * @return
	 */
	boolean isUser();

	/**
	 * 是否游客 一般ID<0
	 * @return
	 */
	boolean isGuest();

	/**
	 * 获得用户ID
	 * @return 用户ID
	 */
	long getId();

	/**
	 * 获得登录时间
	 * @return 登录时间
	 */
	int getTime();

	/**
	 * 获得登录IP
	 * @return IP
	 */
	String getLoginIp();
}
