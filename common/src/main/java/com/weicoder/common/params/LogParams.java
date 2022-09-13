package com.weicoder.common.params;

import com.weicoder.common.constants.C;

/**
 * 读取日志参数
 * 
 * @author WD
 */
public sealed class LogParams permits P.L {
	/** 日志存放目录 */
	public static final String	DIR		= P.getString("log.dir", "/data/logs/" + C.O.PROJECT_NAME);
	/** 日志打印级别 */
	public static final String	LEVEL	= P.getString("log.level", "debug");
	/** 日志打印级别 */
	public static final boolean	TEST	= P.getBoolean("log.test", true);
	/** 日志打印级别 */
	public static final String	LOGGER	= P.getString("log.logger", "warn");
	/** 截取日志长度 */
	public final static int		LEN		= P.getInt("log.len", 500);
}
