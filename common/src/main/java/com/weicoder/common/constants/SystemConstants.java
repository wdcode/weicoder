package com.weicoder.common.constants;

/**
 * 系统常量
 * @author WD
 * @since JDK7
 * @version 1.0 2011-11-28
 */
public final class SystemConstants {
	/**
	 * JDK版本
	 */
	public final static String	JAVA_VERSION	= System.getProperty("java.version");
	/**
	 * 系统名称
	 */
	public final static String	OS_NAME			= System.getProperty("os.name");
	/**
	 * 系统构架
	 */
	public final static String	OS_ARCH			= System.getProperty("os.arch");
	/**
	 * 系统版本
	 */
	public final static String	OS_VERSION		= System.getProperty("os.version");
	/**
	 * 用户名称
	 */
	public final static String	USER_NAME		= System.getProperty("user.name");
	/**
	 * 用户路径
	 */
	public final static String	USER_DIR		= System.getProperty("user.dir");
	/**
	 * CPU核心数量
	 */
	public final static int		CPU_NUM			= Runtime.getRuntime().availableProcessors();

	/**
	 * 私有构造
	 */
	private SystemConstants() {}

}