package com.weicoder.base.entity;

/**
 * 登录接口
 * @author WD
 * @since JDK7
 * @version 1.0 2012-08-13
 */
public interface EntityUser extends Entity, EntityIp, EntityTime {
	/**
	 * 获得ID
	 * @return ID
	 */
	int getId();

	/**
	 * 设置Id
	 * @param id ID
	 */
	void setId(int id);

	/**
	 * 获得名称
	 */
	String getName();

	/**
	 * 设置名称
	 */
	void setName(String name);

	/**
	 * 获得Email
	 * @return Email
	 */
	String getEmail();

	/**
	 * 设置Email
	 * @param email Email
	 */
	void setEmail(String email);

	/**
	 * 设置用户密码
	 * @param password 用户密码
	 */
	void setPassword(String password);

	/**
	 * 获得密码
	 * @return 用户密码
	 */
	String getPassword();

	/**
	 * 设置登录IP
	 * @param ip 登录IP
	 */
	void setLoginIp(String ip);

	/**
	 * 获得登录IP
	 * @return 登录IP
	 */
	String getLoginIp();

	/**
	 * 获得登录时间
	 */
	Integer getLoginTime();

	/**
	 * 设置登录时间
	 */
	void setLoginTime(Integer time);

	/**
	 * 获得状态
	 * @param state
	 */
	Integer getState();

	/**
	 * 设置状态
	 * @param state
	 */
	void setState(Integer state);
}
