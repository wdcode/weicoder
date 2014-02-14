package com.weicoder.web.params;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.params.Params;

/**
 * WdWeb包参数读取类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-01-05
 */
public final class WebParams {
	/* Email */
	/**
	 * 系统发送邮件所用地址
	 */
	public final static String	EMAIL_FROM			= Params.getString("email.from", StringConstants.EMPTY);
	/**
	 * 邮件密码
	 */
	public final static String	EMAIL_PASSWORD		= Params.getString("email.password", StringConstants.EMPTY);
	/**
	 * 邮件服务器Host
	 */
	public final static String	EMAIL_HOST			= Params.getString("email.host", StringConstants.EMPTY);
	/**
	 * 发送Email使用的默认包
	 */
	public final static String	EMAIL_PARSE			= Params.getString("email.parse", "java");
	/**
	 * 发送Email是否验证
	 */
	public final static boolean	EMAIL_AUTH			= Params.getBoolean("email.auth", true);
	/**
	 * 发送Email使用的编码格式
	 */
	public final static String	EMAIL_ENCODING		= Params.getString("email.encoding", CommonParams.ENCODING);

	/* 验证码用 */
	/**
	 * 验证码出现的字符集
	 */
	public final static String	VERIFY_CODE			= Params.getString("verify.code", "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	/**
	 * 验证码出现的字符集
	 */
	public final static char[]	VERIFY_CODE_CHARS	= VERIFY_CODE.toCharArray();
	/**
	 * 验证码长度
	 */
	public final static int		VERIFY_LENGTH		= Params.getInt("verify.length", 4);
	/**
	 * 保存到session中的key
	 */
	public final static String	VERIFY_KEY			= Params.getString("verify.key", "verifyCode");
	/**
	 * 验证码字体
	 */
	public final static String	VERIFY_FONT			= Params.getString("verify.font", "Times New Roman");

	/* FTP使用 */
	/**
	 * FTP主机
	 */
	public final static String	FTP_HOST			= Params.getString("ftp.host", StringConstants.EMPTY);
	/**
	 * FTP端口
	 */
	public final static int		FTP_PORT			= Params.getInt("ftp.port", 21);
	/**
	 * FTP用户名
	 */
	public final static String	FTP_USER			= Params.getString("ftp.user", StringConstants.EMPTY);
	/**
	 * FTP密码
	 */
	public final static String	FTP_PASSWORD		= Params.getString("ftp.password", StringConstants.EMPTY);
	/**
	 * FTP密码
	 */
	public final static int		FTP_BUFFER_SIZE		= Params.getInt("ftp.buffersize", 1024);
	/**
	 * FTP编码
	 */
	public final static String	FTP_ENCODING		= Params.getString("ftp.encoding", CommonParams.ENCODING);
	/**
	 * FTP超时时间
	 */
	public final static int		FTP_TIMEOUT			= Params.getInt("ftp.timeout", 0);

	/**
	 * 域名
	 */
	public final static String	DOMAIN				= Params.getString("domain");

	/**
	 * jmx服务器
	 */
	public final static String	JMX_SERVICE			= Params.getString("jmx.service");

	/**
	 * 私有构造
	 */
	private WebParams() {}
}