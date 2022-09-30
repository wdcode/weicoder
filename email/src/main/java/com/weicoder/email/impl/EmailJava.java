package com.weicoder.email.impl;

import java.net.URL;
import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.activation.URLDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import com.weicoder.common.constants.C;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.U; 
import com.weicoder.email.base.BaseEmail;

/**
 * EmailUtil JavaMail实现
 * 
 * @author  WD 
 */
public final class EmailJava extends BaseEmail {
	/**
	 * 构造方法
	 * 
	 * @param host     smtp地址
	 * @param from     发送Email服务器
	 * @param password 邮箱密码
	 * @param auth     是否验证
	 * @param charset  邮件编码
	 */
	public EmailJava(String host, String from, String password, boolean auth, String charset) {
		super(host, from, password, auth, charset);
	}

	/**
	 * 发送简单文本邮件
	 * 
	 * @param to      发送地址
	 * @param subject 邮件标题
	 * @param msg     邮件内容
	 */
	protected void sendSimpleEmail(String[] to, String subject, String msg) {
		sendEmail(to, subject, msg, null, false);
	}

	/**
	 * 发送带附件的邮件
	 * 
	 * @param to      发送地址
	 * @param subject 邮件标题
	 * @param msg     邮件内容
	 * @param attach  附件
	 */
	protected void sendMultiPartEmail(String[] to, String subject, String msg, String attach) {
		sendEmail(to, subject, msg, attach, false);
	}

	/**
	 * 发送HTML格式邮件
	 * 
	 * @param to      发送地址
	 * @param subject 邮件标题
	 * @param msg     邮件内容
	 */
	protected void sendHtmlEmail(String[] to, String subject, String msg) {
		sendEmail(to, subject, msg, null, true);
	}

	/**
	 * 发送HTML格式带附件的邮件
	 * 
	 * @param to      发送地址
	 * @param subject 邮件标题
	 * @param msg     邮件内容
	 * @param attach  附件
	 */
	protected void sendHtmlEmail(String[] to, String subject, String msg, String attach) {
		sendEmail(to, subject, msg, attach, true);
	}

	/**
	 * 发送Email
	 * 
	 * @param to      发送地址
	 * @param subject 邮件标题
	 * @param msg     邮件内容
	 * @param attach  附件
	 * @param flag    是否html
	 */
	private void sendEmail(String[] to, String subject, String msg, String attach, boolean flag) {
		try {
			// 参数设置
			Properties props = new Properties();
			// 指定SMTP服务器
			props.put("mail.host", getHost());
			// 是否需要SMTP验证
			props.put("mail.smtp.auth", isAuth());
			if (isAuth())
				props.put("mail.smtp.password", getPassword());
			// 获得Session
			Session mailSession = Session.getDefaultInstance(props, isAuth() ? new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, password);
				}
			} : null);
			// 创建细信息类
			Message message = new MimeMessage(mailSession);
			// 设置邮件服务器
			message.setFrom(new InternetAddress(getFrom()));
			// 收件人
			for (int i = 0; i < to.length; i++) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
			}
			// 邮件主题
			message.setSubject(subject);
			// 是否支持HTML
			if (flag) {
				// HTML
				message.setContent(msg, getCharset());
			} else {
				// 普通文本
				message.setText(msg);
			}
			// 添加附件
			if (!U.E.isEmpty(attach)) {
				// 附件
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				DataSource source = null;
				// 判断是本地文件还是远程
				if (attach.indexOf("http") == -1) {
					// 本地文件
					source = new FileDataSource(attach);
				} else {
					// 远程文件
					source = new URLDataSource(new URL(attach));
				}

				messageBodyPart.setDataHandler(new DataHandler(source));
				// 设置描述名字等
				String name = U.S.subStringLast(attach, C.S.BACKSLASH, C.S.POINT);
				// 添加文件名和描述
				messageBodyPart.setText(name);
				messageBodyPart.setFileName(name);

				// 附件
				Multipart multipart = new MimeMultipart();
				// 添加附件
				multipart.addBodyPart(messageBodyPart);

				// 添加到正文中
				message.setContent(multipart);
			}
			// 保存设置
			message.saveChanges();
			// 发送邮件
			Transport.send(message);
		} catch (Exception e) {
			Logs.error(e);
		}
	}
}
