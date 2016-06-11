package com.weicoder.common.log;

import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.ClassUtil;

/**
 * 打印日志工具类
 * @author WD 
 */
public final class Logs {
	// loggin日志对象
	private final static Log LOG = (Log) ClassUtil.newInstance(CommonParams.LOG_CLASS);

	/**
	 * 使用trace打印日志
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	public static void trace(String msg, Object... params) {
		LOG.trace(msg, params);
	}

	/**
	 * 使用trace打印日志
	 * @param t 异常
	 */
	public static void trace(Throwable t) {
		LOG.trace(t);
	}

	/**
	 * 使用debug打印日志
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	public static void debug(String msg, Object... params) {
		LOG.debug(msg, params);
	}

	/**
	 * 使用debug打印日志
	 * @param t 异常
	 */
	public static void debug(Throwable t) {
		LOG.debug(t);
	}

	/**
	 * 使用info打印日志
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	public static void info(String msg, Object... params) {
		LOG.info(msg, params);
	}

	/**
	 * 使用info打印日志
	 * @param t 异常
	 */
	public static void info(Throwable t) {
		LOG.info(t);
	}

	/**
	 * 使用debug打印日志
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	public static void warn(String msg, Object... params) {
		LOG.warn(msg, params);
	}

	/**
	 * 使用debug打印日志
	 * @param t 异常
	 */
	public static void warn(Throwable t) {
		LOG.warn(t);
	}

	/**
	 * 使用error打印日志
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	public static void error(String msg, Object... params) {
		LOG.error(msg, params);
	}

	/**
	 * 使用debug打印日志
	 * @param t 异常
	 */
	public static void error(Throwable t) {
		LOG.error(t);
	}

	private Logs() {}
}
