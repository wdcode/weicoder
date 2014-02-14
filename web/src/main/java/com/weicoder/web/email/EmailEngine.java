package com.weicoder.web.email;

import com.weicoder.web.email.factory.EmailFactory;

/**
 * Email 处理引擎
 * @author WD
 * @since JDK7
 * @version 1.0 2012-08-28
 */
public final class EmailEngine {
	// Email发送器
	private final static Email	EMAIL;

	/**
	 * 静态初始化
	 */
	static {
		EMAIL = EmailFactory.getEmail();
	}

	/**
	 * 发送简单文本邮件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	public static void send(String to, String subject, String msg) {
		EMAIL.send(to, subject, msg);
	}

	/**
	 * 发送简单文本邮件 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	public static void send(String to, String subject, String msg, String attach) {
		EMAIL.send(to, subject, msg, attach);
	}

	/**
	 * 发送Email 支持HTML格式
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 */
	public static void send(String to, String subject, String msg, boolean flag) {
		EMAIL.send(to, subject, msg, flag);
	}

	/**
	 * 发送Email 支持HTML格式 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 * @param attach 附件
	 */
	public static void send(String to, String subject, String msg, String attach, boolean flag) {
		EMAIL.send(to, subject, msg, attach, flag);
	}

	/**
	 * 发送简单文本邮件
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	public static void send(String[] to, String subject, String msg) {
		EMAIL.send(to, subject, msg);
	}

	/**
	 * 发送简单文本邮件 带附件
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	public static void send(String[] to, String subject, String msg, String attach) {
		EMAIL.send(to, subject, msg, attach);
	}

	/**
	 * 私有构造
	 */
	private EmailEngine() {}
}
