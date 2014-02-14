package com.weicoder.web.email;

import java.util.List;

/**
 * Email相关操作接口
 * @author WD
 * @since JDK7
 * @version 1.0 2009-07-17
 */
public interface Email {
	/**
	 * 发送简单文本邮件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	void send(String to, String subject, String msg);

	/**
	 * 发送简单文本邮件 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	void send(String to, String subject, String msg, String attach);

	/**
	 * 发送HTML邮件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	void sendHTML(String to, String subject, String msg);

	/**
	 * 发送HTML邮件 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	void sendHTML(String to, String subject, String msg, String attach);

	/**
	 * 发送Email 支持HTML格式
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 */
	void send(String to, String subject, String msg, boolean flag);

	/**
	 * 发送Email 支持HTML格式 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 * @param attach 附件
	 */
	void send(String to, String subject, String msg, String attach, boolean flag);

	/**
	 * 发送简单文本邮件
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	void send(String[] to, String subject, String msg);

	/**
	 * 发送简单文本邮件 带附件
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	void send(String[] to, String subject, String msg, String attach);

	/**
	 * 发送HTML邮件 多个地址
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	void sendHTML(String[] to, String subject, String msg);

	/**
	 * 发送HTML邮件 带附件 多个地址
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	void sendHTML(String[] to, String subject, String msg, String attach);

	/**
	 * 发送简单文本邮件 多个地址
	 * @param to 集合发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	void send(List<String> to, String subject, String msg);

	/**
	 * 发送简单文本邮件 带附件
	 * @param to 集合发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	void send(List<String> to, String subject, String msg, String attach);

	/**
	 * 发送HTML邮件 多个地址
	 * @param to 集合发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	void sendHTML(List<String> to, String subject, String msg);

	/**
	 * 发送HTML邮件 带附件 多个地址
	 * @param to 集合发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	void sendHTML(List<String> to, String subject, String msg, String attach);

	/**
	 * 发送Email 支持HTML格式
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 */
	void send(String[] to, String subject, String msg, boolean flag);

	/**
	 * 发送Email 支持HTML格式 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 * @param attach 附件
	 */
	void send(String[] to, String subject, String msg, String attach, boolean flag);

	/**
	 * 获得smtp地址
	 * @return smtp地址
	 */
	String getHost();

	/**
	 * 设置smtp地址
	 * @param host smtp地址
	 */
	void setHost(String host);

	/**
	 * 获得发送服务器地址
	 * @return 发送服务器地址
	 */
	String getFrom();

	/**
	 * 设置发送服务器地址
	 * @param from 发送服务器地址
	 */
	void setFrom(String from);

	/**
	 * 获得邮箱密码
	 * @return 邮箱密码
	 */
	String getPassword();

	/**
	 * 设置邮箱密码
	 * @param password 邮箱密码
	 */
	void setPassword(String password);

	/**
	 * 是否验证
	 * @return 是否验证
	 */
	boolean isAuth();

	/**
	 * 设置验证
	 * @param auth 是否验证
	 */
	void setAuth(boolean auth);

	/**
	 * 获得邮件编码
	 * @return 获得邮件编码
	 */
	String getCharset();

	/**
	 * 设置邮件编码
	 * @param charset 获得邮件编码
	 */
	void setCharset(String charset);
}
