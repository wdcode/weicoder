package com.weicoder.web.email.factory;

import com.weicoder.web.email.Email;
import com.weicoder.web.email.impl.EmailApache;
import com.weicoder.web.email.impl.EmailJava;
import com.weicoder.web.params.WebParams;

import com.weicoder.core.factory.Factory;

/**
 * 获得Email接口实例的工厂类
 * @see com.weicoder.web.email.Email
 * @author WD
 * @since JDK7
 * @version 1.0 2009-07-17
 */
public final class EmailFactory extends Factory<Email> {
	// 工厂
	private final static EmailFactory	FACTORY	= new EmailFactory();

	/**
	 * 返回工厂
	 * @return 工厂
	 */
	public static Email getEmail() {
		return FACTORY.getInstance();
	}

	/**
	 * 实例化一个新的EmailUtil实例
	 * @param host smtp服务器
	 * @param from 发送Email服务器地址
	 * @param password Email服务器密码
	 * @return EmailUtil
	 */
	public static Email newEmail(String host, String from, String password) {
		return FACTORY.newInstance(host, from, password, WebParams.EMAIL_AUTH, WebParams.EMAIL_ENCODING);
	}

	/**
	 * 实例化一个新的EmailUtil实例
	 * @param host smtp服务器
	 * @param from 发送Email服务器地址
	 * @param password Email服务器密码
	 * @param auth 是否验证
	 * @param charset 编码格式
	 * @param parse 解析包
	 * @return EmailUtil
	 */
	public static Email newEmail(String host, String from, String password, boolean auth, String charset) {
		return FACTORY.newInstance(host, from, password, auth, charset);
	}

	/**
	 * 实例化一个新的EmailUtil实例 所有配置都用默认设置
	 * @return EmailUtil
	 */
	public Email newInstance() {
		return newInstance(WebParams.EMAIL_HOST, WebParams.EMAIL_FROM, WebParams.EMAIL_PASSWORD);
	}

	/**
	 * 实例化一个新的EmailUtil实例
	 * @param host smtp服务器
	 * @param from 发送Email服务器地址
	 * @param password Email服务器密码
	 * @return EmailUtil
	 */
	public Email newInstance(String host, String from, String password) {
		return newInstance(host, from, password, WebParams.EMAIL_AUTH, WebParams.EMAIL_ENCODING);
	}

	/**
	 * 实例化一个新的EmailUtil实例
	 * @param host smtp服务器
	 * @param from 发送Email服务器地址
	 * @param password Email服务器密码
	 * @param auth 是否验证
	 * @param charset 编码格式
	 * @param parse 解析包
	 * @return EmailUtil
	 */
	public Email newInstance(String host, String from, String password, boolean auth, String charset) {
		// 判断使用哪个包
		switch (WebParams.EMAIL_PARSE) {
			case "Apache":
				return new EmailApache(host, from, password, auth, charset);
			default:
				return new EmailJava(host, from, password, auth, charset);
		}
	}

	/**
	 * 私有构造
	 */
	private EmailFactory() {}
}
