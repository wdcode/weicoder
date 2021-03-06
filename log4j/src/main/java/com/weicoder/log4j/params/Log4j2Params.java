package com.weicoder.log4j.params;

import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.params.Params;

/**
 * 读取日志参数
 * 
 * @author WD
 */
public final class Log4j2Params {
	/** 日志存放目录 */
	public static final String  DIR    = Params.getString("log.dir", "/data/logs/" + SystemConstants.PROJECT_NAME);
	/** 日志打印级别 */
	public static final String  LEVEL  = Params.getString("log.level", "debug");
	/** 日志打印级别 */
	public static final boolean TEST   = Params.getBoolean("log.test", true);
	/** 日志打印级别 */
	public static final String  LOGGER = Params.getString("log.logger", "warn");

	private Log4j2Params() {
	}
}
