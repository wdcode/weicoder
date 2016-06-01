package com.weicoder.common.log;

import com.weicoder.common.util.ClassUtil;

/**
 * 打印日志工具类
 * @author WD 
 * 
 */
public final class Logs {
	// loggin日志对象
	private final static Log LOG = (Log) ClassUtil.newInstance("com.weicoder.core.log.LoggerSlf4j");

	/**
	 * 使用debug打印日志
	 * @param info 日志信息
	 */
	public static void debug(Object info) {
		LOG.debug(info);
	}

	/**
	 * 使用info打印日志
	 * @param info 日志信息
	 */
	public static void info(Object info) {
		LOG.info(info);
	}

	/**
	 * 使用warn打印日志
	 * @param info 日志信息
	 */
	public static void warn(Object info) {
		LOG.warn(info);
	}

	/**
	 * 使用error打印日志
	 * @param info 日志信息
	 */
	public static void error(Object info) {
		LOG.error(info);
	}

	private Logs() {}
}
