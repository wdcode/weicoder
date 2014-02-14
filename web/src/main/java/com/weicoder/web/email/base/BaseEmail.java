package com.weicoder.web.email.base;

import java.util.List;

import com.weicoder.web.email.Email;

/**
 * Email相关操作抽象类
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-27
 */
public abstract class BaseEmail implements Email {
	// smtp地址
	protected String	host;
	// 发送服务器地址
	protected String	from;
	// 邮箱密码
	protected String	password;
	// 是否验证
	protected boolean	auth;
	// 邮件编码
	protected String	charset;

	/**
	 * 发送简单文本邮件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	public final void send(String to, String subject, String msg) {
		send(to, subject, msg, false);
	}

	/**
	 * 发送简单文本邮件 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	public final void send(String to, String subject, String msg, String attach) {
		send(to, subject, msg, attach, false);
	}

	/**
	 * 发送HTML邮件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	public final void sendHTML(String to, String subject, String msg) {
		send(to, subject, msg, true);
	}

	/**
	 * 发送HTML邮件 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	public final void sendHTML(String to, String subject, String msg, String attach) {
		send(to, subject, msg, attach, true);
	}

	/**
	 * 发送Email 支持HTML格式
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 */
	public final void send(String to, String subject, String msg, boolean flag) {
		send(new String[] { to }, subject, msg, flag);
	}

	/**
	 * 发送Email 支持HTML格式 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 * @param attach 附件
	 */
	public final void send(String to, String subject, String msg, String attach, boolean flag) {
		send(new String[] { to }, subject, msg, attach, flag);
	}

	/**
	 * 发送简单文本邮件
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	public final void send(String[] to, String subject, String msg) {
		send(to, subject, msg, false);
	}

	/**
	 * 发送简单文本邮件 带附件
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	public final void send(String[] to, String subject, String msg, String attach) {
		send(to, subject, msg, attach, false);
	}

	/**
	 * 发送HTML邮件 多个地址
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	public final void sendHTML(String[] to, String subject, String msg) {
		send(to, subject, msg, true);
	}

	/**
	 * 发送HTML邮件 带附件 多个地址
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	public final void sendHTML(String[] to, String subject, String msg, String attach) {
		send(to, subject, msg, attach, true);
	}

	/**
	 * 发送简单文本邮件 多个地址
	 * @param to 集合发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	public final void send(List<String> to, String subject, String msg) {
		send(to.toArray(new String[to.size()]), subject, msg, false);
	}

	/**
	 * 发送简单文本邮件 带附件
	 * @param to 集合发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	public final void send(List<String> to, String subject, String msg, String attach) {
		send(to.toArray(new String[to.size()]), subject, msg, attach, false);
	}

	/**
	 * 发送HTML邮件 多个地址
	 * @param to 集合发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	public final void sendHTML(List<String> to, String subject, String msg) {
		send(to.toArray(new String[to.size()]), subject, msg, true);
	}

	/**
	 * 发送HTML邮件 带附件 多个地址
	 * @param to 集合发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	public final void sendHTML(List<String> to, String subject, String msg, String attach) {
		send(to.toArray(new String[to.size()]), subject, msg, attach, true);
	}

	/**
	 * 发送Email 支持HTML格式
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 */
	public final void send(String[] to, String subject, String msg, boolean flag) {
		// 判断是否支持HTML
		if (flag) {
			// 发送HTML邮件
			sendHtmlEmail(to, subject, msg);
		} else {
			// 发送简单文本邮件
			sendSimpleEmail(to, subject, msg);
		}
	}

	/**
	 * 发送Email 支持HTML格式 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 * @param attach 附件
	 */
	public final void send(String[] to, String subject, String msg, String attach, boolean flag) {
		// 判断是否支持HTML
		if (flag) {
			// 发送HTML邮件带附件
			sendHtmlEmail(to, subject, msg, attach);
		} else {
			// 发送简单文本邮件 带附件
			sendMultiPartEmail(to, subject, msg, attach);
		}
	}

	/**
	 * 获得smtp地址
	 * @return smtp地址
	 */
	public final String getHost() {
		return host;
	}

	/**
	 * 设置smtp地址
	 * @param host smtp地址
	 */
	public final void setHost(String host) {
		this.host = host;
	}

	/**
	 * 获得发送服务器地址
	 * @return 发送服务器地址
	 */
	public final String getFrom() {
		return from;
	}

	/**
	 * 设置发送服务器地址
	 * @param from 发送服务器地址
	 */
	public final void setFrom(String from) {
		this.from = from;
	}

	/**
	 * 获得邮箱密码
	 * @return 邮箱密码
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * 设置邮箱密码
	 * @param password 邮箱密码
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 是否验证
	 * @return 是否验证
	 */
	public final boolean isAuth() {
		return auth;
	}

	/**
	 * 设置验证
	 * @param auth 是否验证
	 */
	public final void setAuth(boolean auth) {
		this.auth = auth;
	}

	/**
	 * 获得邮件编码
	 * @return 获得邮件编码
	 */
	public final String getCharset() {
		return charset;
	}

	/**
	 * 设置邮件编码
	 * @param charset 获得邮件编码
	 */
	public final void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * 构造方法
	 * @param host smtp地址
	 * @param from 发送Email服务器
	 * @param password 邮箱密码
	 * @param auth 是否验证
	 * @param charset 邮件编码
	 */
	public BaseEmail(String host, String from, String password, boolean auth, String charset) {
		this.auth = auth;
		this.host = host;
		this.from = from;
		this.password = password;
		this.charset = charset;
	}

	/**
	 * 发送简单文本邮件
	 * @param to 发送地址
	 * @param subject 邮件标题
	 * @param msg 邮件内容
	 */
	protected abstract void sendSimpleEmail(String[] to, String subject, String msg);

	/**
	 * 发送带附件的邮件
	 * @param to 发送地址
	 * @param subject 邮件标题
	 * @param msg 邮件内容
	 * @param attach 附件
	 */
	protected abstract void sendMultiPartEmail(String[] to, String subject, String msg, String attach);

	/**
	 * 发送HTML格式邮件
	 * @param to 发送地址
	 * @param subject 邮件标题
	 * @param msg 邮件内容
	 */
	protected abstract void sendHtmlEmail(String[] to, String subject, String msg);

	/**
	 * 发送HTML格式带附件的邮件
	 * @param to 发送地址
	 * @param subject 邮件标题
	 * @param msg 邮件内容
	 * @param attach 附件
	 */
	protected abstract void sendHtmlEmail(String[] to, String subject, String msg, String attach);
}