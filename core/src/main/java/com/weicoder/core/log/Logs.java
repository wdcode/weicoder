package com.weicoder.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weicoder.common.lang.Conversion;

/**
 * 用于记录各种日志以及打印DEBG信息等
 * @author WD
 * @since JDK7
 * @version 1.0 2014-05-18
 */
public final class Logs {
	// loggin日志对象
	private final static Logger	LOG	= LoggerFactory.getLogger(Logs.class);

	/**
	 * 使用debug打印日志
	 * @param info 日志信息
	 */
	public static void debug(Object info) {
		if (LOG.isDebugEnabled()) {
			log(LogEnum.DEBUG, info);
		}
	}

	/**
	 * 使用info打印日志
	 * @param info 日志信息
	 */
	public static void info(Object info) {
		if (LOG.isInfoEnabled()) {
			log(LogEnum.INFO, info);
		}
	}

	/**
	 * 使用warn打印日志
	 * @param info 日志信息
	 */
	public static void warn(Object info) {
		if (LOG.isWarnEnabled()) {
			log(LogEnum.WARN, info);
		}
	}

	/**
	 * 使用error打印日志
	 * @param info 日志信息
	 */
	public static void error(Object info) {
		if (LOG.isErrorEnabled()) {
			log(LogEnum.ERROR, info);
		}
	}

	/**
	 * 打印日志
	 * @param logEnum 日志枚举
	 * @param obj 日志信息
	 */
	private static void log(LogEnum logEnum, Object obj) {
		// 日志信息
		String mess = null;
		// 异常
		Throwable t = null;
		// 判断传人信息类型
		if (obj instanceof Throwable) {
			t = (Throwable) obj;
		} else {
			mess = Conversion.toString(obj);
		}
		// 打印日志
		switch (logEnum) {
			case DEBUG:
				if (LOG.isDebugEnabled()) {
					LOG.debug(mess, t);
				}
				break;
			case INFO:
				if (LOG.isInfoEnabled()) {
					LOG.info(mess, t);
				}
				break;
			case WARN:
				if (LOG.isWarnEnabled()) {
					LOG.warn(mess, t);
				}
				break;
			case ERROR:
				if (LOG.isErrorEnabled()) {
					LOG.error(mess, t);
				}
				break;
		}

	}

	/**
	 * 日志枚举
	 * @author WD
	 * @since JDK7
	 * @version 1.0 2012-02-17
	 */
	static enum LogEnum {
		DEBUG, INFO, WARN, ERROR
	}

	/** 私有构造 */
	private Logs() {}
}
