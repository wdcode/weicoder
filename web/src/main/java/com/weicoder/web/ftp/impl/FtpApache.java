package com.weicoder.web.ftp.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import com.weicoder.common.io.FileUtil;
import com.weicoder.core.log.Logs;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.web.ftp.Ftp;
import com.weicoder.web.params.WebParams;

/**
 * 使用Apache的 commons-net 包实现
 * @author WD
 * @since JDK7
 * @version 1.0 2010-4-23
 */
public final class FtpApache implements Ftp {
	// commons-net 的FTP实现
	private FTPClient	client;
	// 服务器地址
	private String		host;
	// 服务器端口
	private int			port;
	// 连接用户名
	private String		user;
	// 连接密码
	private String		password;

	/**
	 * 构造方法
	 */
	public FtpApache() {
		// 实例化FTPClient对象
		client = new FTPClient();
		// 设置缓存
		client.setBufferSize(WebParams.FTP_BUFFER_SIZE);
		// 设置编码
		client.setControlEncoding(WebParams.FTP_ENCODING);
		// 设置超时时间
		client.setConnectTimeout(WebParams.FTP_TIMEOUT);
	}

	/**
	 * 构造方法
	 * @param host 主机
	 * @param port 端口
	 * @param user 用户
	 * @param password 密码
	 */
	public FtpApache(String host, int port, String user, String password) {
		// 构造方法
		this();
		// 设置主机
		this.host = host;
		// 设置端口
		this.port = port;
		// 设置用户
		this.user = user;
		// 设置密码
		this.password = password;
	}

	/**
	 * 改变目录
	 * @param path 好改变到的目录
	 * @return 是否成功
	 */
	public boolean changeDirectory(String path) {
		try {
			return client.changeWorkingDirectory(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回到根目录
	 * @return 是否成功
	 */
	public boolean changeToParentDirectory() {
		try {
			return client.changeToParentDirectory();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 连接服务器
	 */
	public void connect() {
		try {
			client.connect(host, port);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 是否连接到服务器
	 * @return
	 */
	public boolean isConnected() {
		return client.isConnected();
	}

	/**
	 * 断开连接
	 */
	public void disconnect() {
		try {
			client.disconnect();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 下载的文件流
	 * @param remoteFile 要下载的文件
	 * @param out 输出流
	 */
	public void download(String remoteFile, OutputStream out) {
		// 未连接的ftp连接上
		if (!isConnected()) {
			connect();
		}
		// 登录
		login();
		// 下载文件到流中
		try {
			// 设置文件上传方式
			client.setFileType(org.apache.commons.net.ftp.FTPClient.BINARY_FILE_TYPE);
			// 获得文件流
			client.retrieveFile(remoteFile, out);
		} catch (IOException e) {
			// 记录日志
			Logs.error(e);
		}
	}

	/**
	 * 登录服务器
	 * @return 是否成功
	 */
	public boolean login() {
		try {
			return client.login(user, password);
		} catch (IOException e) {
			// 记录日志
			Logs.error(e);
			// 返回false
			return false;
		}
	}

	/**
	 * 退出登录
	 * @return 是否成功
	 */
	public boolean logout() {
		try {
			return client.logout();
		} catch (IOException e) {
			// 记录日志
			Logs.error(e);
			// 返回false
			return false;
		}
	}

	/**
	 * 发送命令
	 * @param arguments 命令
	 * @return 是否成功
	 */
	public int sendCommand(String command) {
		try {
			return client.sendCommand(command);
		} catch (IOException e) {
			// 记录日志
			Logs.error(e);
			// 返回false
			return -1;
		}
	}

	/**
	 * 上传流
	 * @param directory 上传到的目录
	 * @param remoteFile 远程保存的文件
	 * @param in 上传的流
	 * @return 是否成功
	 */
	public boolean upload(String directory, String remoteFile, InputStream in) {
		// 未连接的ftp连接上
		if (!isConnected()) {
			connect();
		}
		// 登录
		login();
		// enterLocalPassiveMode：设置客户端PASV模式 (客户端主动连服务器端；端口用20)
		client.enterLocalPassiveMode();
		// enterLocalActiveMode：设置客户端PORT模式 (服务器端连客户端；随机打开一个高端端口(端口号大于1024))
		client.enterLocalActiveMode();
		// 设置上传目录
		changeDirectory(directory);
		// 上传文件
		boolean is = false;
		try {
			// 设置文件上传方式
			client.setFileType(org.apache.commons.net.ftp.FTPClient.BINARY_FILE_TYPE);
			// 上传文件
			is = client.storeFile(remoteFile, in);
		} catch (Exception e) {
			// 记录日志
			Logs.error(e);
			// 返回false
			return false;
		} finally {
			CloseUtil.close(in);
		}
		// 返回是否成功
		return is;
	}

	/**
	 * 上传文件
	 * @param directory 上传到的目录
	 * @param 远程保存的文件
	 * @param fileName 上传的文件
	 * @return 是否成功
	 */
	public boolean upload(String directory, String remoteFile, String fileName) {
		return upload(directory, remoteFile, FileUtil.getFile(fileName));
	}

	/**
	 * 上传文件
	 * @param directory 上传到的目录
	 * @param remoteFile 远程保存的文件
	 * @param file 上传的文件
	 * @return 是否成功
	 */
	public boolean upload(String directory, String remoteFile, File file) {
		return upload(directory, remoteFile, FileUtil.getInputStream(file));
	}

	/**
	 * 获得服务器地址
	 * @return 服务器地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置服务器地址
	 * @param hostName 服务器地址
	 */
	public void setHost(String hostName) {
		this.host = hostName;
	}

	/**
	 * 获得服务器端口
	 * @return 服务器端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置服务器端口
	 * @param port 服务器端口
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 获得用户名
	 * @return 用户名
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 设置用户名
	 * @param user 用户名
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 获得密码
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
