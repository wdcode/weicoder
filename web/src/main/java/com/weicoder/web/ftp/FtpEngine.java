package com.weicoder.web.ftp;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.weicoder.web.ftp.factory.FtpFactory;

/**
 * FTP 处理引擎
 * @author WD
 * @since JDK7
 * @version 1.0 2012-08-28
 */
public final class FtpEngine {
	// FTP接口
	private final static Ftp	FTP;

	/**
	 * 私有构造
	 */
	static {
		FTP = FtpFactory.getFtp();
	}

	/**
	 * 上传文件
	 * @param directory 上传到的目录
	 * @param 远程保存的文件
	 * @param fileName 上传的文件
	 * @return 是否成功
	 */
	public static void upload(String directory, String remoteFile, String fileName) {
		init();
		FTP.upload(directory, remoteFile, fileName);
		close();
	}

	/**
	 * 上传文件
	 * @param directory 上传到的目录
	 * @param remoteFile 远程保存的文件
	 * @param file 上传的文件
	 * @return 是否成功
	 */
	public static void upload(String directory, String remoteFile, File file) {
		init();
		FTP.upload(directory, remoteFile, file);
		close();
	}

	/**
	 * 上传流
	 * @param directory 上传到的目录
	 * @param remoteFile 远程保存的文件
	 * @param in 上传的流
	 * @return 是否成功
	 */
	public static void upload(String directory, String remoteFile, InputStream in) {
		init();
		FTP.upload(directory, remoteFile, in);
		close();
	}

	/**
	 * 下载的文件流
	 * @param remoteFile 要下载的文件
	 * @param out 输出流
	 */
	public static void download(String remoteFile, OutputStream out) {
		init();
		FTP.download(remoteFile, out);
		close();
	}

	/**
	 * 初始化FTP连接
	 */
	private static void init() {
		FTP.connect();
		FTP.login();
	}

	/**
	 * 关闭方法
	 */
	private static void close() {
		FTP.logout();
		FTP.disconnect();
	}

	/**
	 * 私有构造
	 */
	private FtpEngine() {}
}
