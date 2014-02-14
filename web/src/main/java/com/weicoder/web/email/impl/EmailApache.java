package com.weicoder.web.email.impl;

import java.net.URL;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.core.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.constants.HttpConstants;
import com.weicoder.web.email.base.BaseEmail;

/**
 * EmailUtil Apache Common Email实现
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-27
 */
public final class EmailApache extends BaseEmail {
	/**
	 * 构造方法
	 * @param host smtp地址
	 * @param from 发送Email服务器
	 * @param password 邮箱密码
	 * @param auth 是否验证
	 * @param charset 邮件编码
	 */
	public EmailApache(String host, String from, String password, boolean auth, String charset) {
		super(host, from, password, auth, charset);
	}

	/**
	 * 发送简单文本邮件
	 * @param to 发送地址
	 * @param subject 邮件标题
	 * @param msg 邮件内容
	 */
	protected final void sendSimpleEmail(String[] to, String subject, String msg) {
		// 发送Email
		sendEmail(new SimpleEmail(), to, subject, msg);
	}

	/**
	 * 发送带附件的邮件
	 * @param to 发送地址
	 * @param subject 邮件标题
	 * @param msg 邮件内容
	 * @param attach 附件
	 */
	protected final void sendMultiPartEmail(String[] to, String subject, String msg, String attach) {
		// 实例化邮件操作类
		MultiPartEmail email = new MultiPartEmail();
		// 添加附件
		setAttachment(email, attach);
		// 发送Email
		sendEmail(email, to, subject, msg);
	}

	/**
	 * 发送HTML格式邮件
	 * @param to 发送地址
	 * @param subject 邮件标题
	 * @param msg 邮件内容
	 * @param attach 附件
	 */
	protected final void sendHtmlEmail(String[] to, String subject, String msg) {
		// 发送Email
		sendEmail(new HtmlEmail(), to, subject, msg);
	}

	/**
	 * 发送HTML格式带附件的邮件
	 * @param to 发送地址
	 * @param subject 邮件标题
	 * @param msg 邮件内容
	 * @param attach 附件
	 */
	protected final void sendHtmlEmail(String[] to, String subject, String msg, String attach) {
		// 实例化邮件操作类
		HtmlEmail email = new HtmlEmail();
		// 添加附件
		setAttachment(email, attach);
		// 发送Email
		sendEmail(email, to, subject, msg);
	}

	/**
	 * 发送Email
	 * @param email Email发送对象
	 * @param to 发送地址
	 * @param subject 邮件标题
	 * @param msg 邮件内容
	 */
	private void sendEmail(Email email, String[] to, String subject, String msg) {
		try {
			// 设置smtp地址
			email.setHostName(getHost());
			// 判断是否验证
			if (isAuth()) {
				// 邮件服务器验证：用户名/密码
				email.setAuthentication(getFrom(), getPassword());
			}
			// 设置编码 必须放在前面，否则乱码
			email.setCharset(getCharset());
			// 发送到
			for (int i = 0; i < to.length; i++) {
				email.addTo(to[i]);
			}
			// 邮件服务器
			email.setFrom(getFrom(), getPassword());
			// 设置标题
			email.setSubject(subject);
			// 设置正文
			email.setMsg(msg);
			// 发送
			email.send();
		} catch (EmailException e) {
			Logs.error(e);
		}
	}

	/***
	 * 设置附件
	 * @param email 附件Email
	 * @param attach 附件
	 */
	private void setAttachment(MultiPartEmail email, String attach) {
		try {
			// 判断附件是否为空
			if (EmptyUtil.isEmpty(attach)) {
				return;
			}
			// 实例化邮件附件
			EmailAttachment attachment = new EmailAttachment();
			// 判断是本地文件还是远程
			if (attach.indexOf(HttpConstants.HTTP) == -1) {
				// 本地文件
				attachment.setPath(attach);
			} else {
				// 远程文件
				attachment.setURL(new URL(attach));
			}
			// 附件设置
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			// 设置描述名字等
			String name = StringUtil.subStringLast(attach, StringConstants.BACKSLASH, StringConstants.POINT);
			// 描述
			attachment.setDescription(name);
			// 名字
			attachment.setName(name);
			// 添加附件
			email.attach(attachment);
		} catch (Exception e) {
			Logs.error(e);
		}
	}
}
