package com.weicoder.common.log;

import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.ClassUtil;

/**
 * 打印日志工具类
 * @author WD
 */
public final class Logs {
	// loggin日志对象
	private final static Log LOG = ClassUtil.newInstance(CommonParams.LOG_CLASS, new LogJdk());

	/**
	 * 使用trace打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void trace(String msg, Object... params) {
		if (LOG.isTrace())
			LOG.trace(msg, params);
	}

	/**
	 * 使用trace打印日志
	 * @param t 异常
	 */
	public static void trace(Throwable t) {
		if (LOG.isTrace())
			LOG.trace(t);
	}

	/**
	 * 使用debug打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void debug(String msg, Object... params) {
		if (LOG.isDebug())
			LOG.debug(msg, params);
	}

	/**
	 * 使用debug打印日志
	 * @param t 异常
	 */
	public static void debug(Throwable t) {
		if (LOG.isDebug())
			LOG.debug(t);
	}

	/**
	 * 使用info打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void info(String msg, Object... params) {
		if (LOG.isInfo())
			LOG.info(msg, params);
	}

	/**
	 * 使用info打印日志
	 * @param t 异常
	 */
	public static void info(Throwable t) {
		if (LOG.isInfo())
			LOG.info(t);
	}

	/**
	 * 使用warn打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void warn(String msg, Object... params) {
		if (LOG.isWarn())
			LOG.warn(msg, params);
	}

	/**
	 * 使用warn打印日志
	 * @param t 异常
	 */
	public static void warn(Throwable t) {
		if (LOG.isWarn())
			LOG.warn(t);
	}

	/**
	 * 使用error打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void error(String msg, Object... params) {
		if (LOG.isError())
			LOG.error(msg, params);
	}

	/**
	 * 使用error打印日志
	 * @param t 异常
	 */
	public static void error(Throwable t) {
		if (LOG.isError())
			LOG.error(t);
	}

	/**
	 * 使用error打印日志
	 * @param msg 信息
	 * @param t 异常
	 */
	public static void error(String msg, Throwable t) {
		if (LOG.isError())
			LOG.error(msg, t);
	}

	/**
	 * 使用error打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void error(Throwable t, String msg, Object... params) {
		if (LOG.isError()) {
			LOG.error(msg, params);
			LOG.error(t);
		}
	}

	private Logs() {}
}
