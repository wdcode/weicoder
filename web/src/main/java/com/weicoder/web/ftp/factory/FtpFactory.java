package com.weicoder.web.ftp.factory;

import com.weicoder.core.factory.Factory;
import com.weicoder.web.ftp.Ftp;
import com.weicoder.web.ftp.impl.FtpApache;
import com.weicoder.web.params.WebParams;

/**
 * FTP工厂
 * @author WD
 * @since JDK7
 * @version 1.0 2010-04-20
 */
public final class FtpFactory extends Factory<Ftp> {
	// 工厂
	private final static FtpFactory	FACTORY	= new FtpFactory();

	/**
	 * 返回FTP
	 * @return FTP
	 */
	public static Ftp getFtp() {
		return FACTORY.getInstance();
	}

	/**
	 * 返回FTP
	 * @param host 主机
	 * @param port 端口
	 * @param user 用户
	 * @param password 密码
	 * @return FTP
	 */
	public static Ftp newFtp(String host, int port, String user, String password) {
		return FACTORY.newInstance(host, port, user, password);
	}

	/**
	 * 新生成一个FTPClient
	 * @return FTP
	 */
	public Ftp newInstance() {
		return newInstance(WebParams.FTP_HOST, WebParams.FTP_PORT, WebParams.FTP_USER, WebParams.FTP_PASSWORD);
	}

	/**
	 * 新生成一个FTPClient
	 * @param host 主机
	 * @param port 端口
	 * @param user 用户
	 * @param password 密码
	 * @return FTP
	 */
	public Ftp newInstance(String host, int port, String user, String password) {
		return new FtpApache(host, port, user, password);
	}

	/**
	 * 静态初始化
	 */
	private FtpFactory() {}
}
