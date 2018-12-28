package com.weicoder.email.factory;

import com.weicoder.common.factory.Factory;
import com.weicoder.email.Email;
import com.weicoder.email.impl.EmailApache;
import com.weicoder.email.impl.EmailJava;
import com.weicoder.email.params.EmailParams;
  

/**
 * 获得Email接口实例的工厂类 
 * @author WD 
 * @version 1.0 
 */
public final class EmailFactory extends Factory<Email> {
	// 工厂
	private final static EmailFactory FACTORY = new EmailFactory();

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
		return FACTORY.newInstance(host, from, password, EmailParams.AUTH, EmailParams.ENCODING);
	}

	/**
	 * 实例化一个新的EmailUtil实例
	 * @param host smtp服务器
	 * @param from 发送Email服务器地址
	 * @param password Email服务器密码
	 * @param auth 是否验证
	 * @param charset 编码格式 
	 * @return Email
	 */
	public static Email newEmail(String host, String from, String password, boolean auth, String charset) {
		return FACTORY.newInstance(host, from, password, auth, charset);
	}

	/**
	 * 实例化一个新的EmailUtil实例 所有配置都用默认设置
	 * @return EmailUtil
	 */
	public Email newInstance() {
		return newInstance(EmailParams.HOST, EmailParams.FROM, EmailParams.PASSWORD);
	}

	/**
	 * 实例化一个新的EmailUtil实例
	 * @param host smtp服务器
	 * @param from 发送Email服务器地址
	 * @param password Email服务器密码
	 * @return EmailUtil
	 */
	public Email newInstance(String host, String from, String password) {
		return newInstance(host, from, password, EmailParams.AUTH, EmailParams.ENCODING);
	}

	/**
	 * 实例化一个新的EmailUtil实例
	 * @param host smtp服务器
	 * @param from 发送Email服务器地址
	 * @param password Email服务器密码
	 * @param auth 是否验证
	 * @param charset 编码格式 
	 * @return Email
	 */
	public Email newInstance(String host, String from, String password, boolean auth, String charset) {
		// 判断使用哪个包
		switch (EmailParams.PARSE) {
			case "Apache":
				return new EmailApache(host, from, password, auth, charset);
			default:
				return new EmailJava(host, from, password, auth, charset);
		}
	}

	private EmailFactory() {}
}
