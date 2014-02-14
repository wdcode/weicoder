package com.weicoder.web.ftp;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * FTP客户端接口
 * @author WD
 * @since JDK7
 * @version 1.0 2010-04-20
 */
public interface Ftp {
	/**
	 * 连接服务器
	 */
	void connect();

	/**
	 * 是否连接到服务器
	 * @return
	 */
	boolean isConnected();

	/**
	 * 断开连接
	 */
	void disconnect();

	/**
	 * 登录服务器
	 * @return 是否成功
	 */
	boolean login();

	/**
	 * 退出登录
	 * @return 是否成功
	 */
	boolean logout();

	/**
	 * 上传文件
	 * @param directory 上传到的目录
	 * @param 远程保存的文件
	 * @param fileName 上传的文件
	 * @return 是否成功
	 */
	boolean upload(String directory, String remoteFile, String fileName);

	/**
	 * 上传文件
	 * @param directory 上传到的目录
	 * @param remoteFile 远程保存的文件
	 * @param file 上传的文件
	 * @return 是否成功
	 */
	boolean upload(String directory, String remoteFile, File file);

	/**
	 * 上传流
	 * @param directory 上传到的目录
	 * @param remoteFile 远程保存的文件
	 * @param in 上传的流
	 * @return 是否成功
	 */
	boolean upload(String directory, String remoteFile, InputStream in);

	/**
	 * 下载的文件流
	 * @param remoteFile 要下载的文件
	 * @param out 输出流
	 */
	void download(String remoteFile, OutputStream out);

	/**
	 * 发送命令
	 * @param command 命令
	 * @return 状态
	 */
	int sendCommand(String command);

	/**
	 * 返回到根目录
	 * @return 是否成功
	 */
	boolean changeToParentDirectory();

	/**
	 * 改变目录
	 * @param path 好改变到的目录
	 * @return 是否成功
	 */
	boolean changeDirectory(String path);

	/**
	 * 获得服务器地址
	 * @return 服务器地址
	 */
	public String getHost();

	/**
	 * 设置服务器地址
	 * @param host 服务器地址
	 */
	public void setHost(String host);

	/**
	 * 获得服务器端口
	 * @return 服务器端口
	 */
	public int getPort();

	/**
	 * 设置服务器端口
	 * @param port 服务器端口
	 */
	public void setPort(int port);

	/**
	 * 获得用户名
	 * @return 用户名
	 */
	public String getUser();

	/**
	 * 设置用户名
	 * @param user 用户名
	 */
	public void setUser(String user);

	/**
	 * 获得密码
	 * @return 密码
	 */
	public String getPassword();

	/**
	 * 设置密码
	 * @param password 密码
	 */
	public void setPassword(String password);
}
