package com.weicoder.common.constants;

import java.io.File;

import com.weicoder.common.util.ResourceUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 系统常量
 * 
 * @author WD
 */
public sealed class SystemConstants permits C.O {
	/** JDK版本 */
	public final static String	JAVA_VERSION	= System.getProperty("java.version");
	/** 系统名称 */
	public final static String	OS_NAME			= System.getProperty("os.name");
	/** 系统构架 */
	public final static String	OS_ARCH			= System.getProperty("os.arch");
	/** 系统版本 */
	public final static String	OS_VERSION		= System.getProperty("os.version");
	/** 用户名称 */
	public final static String	USER_NAME		= System.getProperty("user.name");
	/** 用户路径 */
	public final static String	USER_DIR		= System.getProperty("user.dir");
	/** 操作系统文件分隔符 unix=/ win=\ */
	public final static String	FILE_SEPARATOR	= File.separator;
	/** 操作系统行分隔符 unix=\n win=\r\n */
	public final static String	LINE_SEPARATOR	= System.lineSeparator();
	/** 操作系统path分隔符 : */
	public final static String	PATH_SEPARATOR	= File.pathSeparator;
	/** 本项目的名称 */
	public final static String	PROJECT_NAME	= StringUtil.subStringLast(USER_DIR, FILE_SEPARATOR);
	/** 项目运行class目录路径 */
	public final static String	BASE_DIR		= ResourceUtil.getResource(StringConstants.EMPTY).getPath();
	/** CPU核心数量 */
	public final static int		CPU_NUM			= Runtime.getRuntime().availableProcessors();
}