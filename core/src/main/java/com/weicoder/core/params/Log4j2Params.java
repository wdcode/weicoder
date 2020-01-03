package com.weicoder.core.params;

import com.weicoder.common.params.Params;

/**
 * 读取缓存参赛
 * 
 * @author WD
 */
public final class Log4j2Params {
	/** 日志存放目录 */
	public static final String DIR   = Params.getString("log.dir");
	/** 日志打印级别 */
	public static final String LEVEL = Params.getString("log.level", "debug");

	private Log4j2Params() {
	}
}
