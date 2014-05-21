package com.weicoder.core.log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weicoder.common.lang.Conversion;
import com.weicoder.core.params.CoreParams;

/**
 * 用于记录各种日志以及打印DEBG信息等
 * @author WD
 * @since JDK7
 * @version 1.0 2014-05-18
 */
public final class Loggers {
	// loggin日志对象
	private final static Logger				LOG		= LoggerFactory.getLogger(Loggers.class);
	// 异步线程
	private final static ExecutorService	SERVICE	= Executors.newSingleThreadExecutor();

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
	 * 异步打印日志
	 * @param logEnum 日志枚举
	 * @param obj 日志信息
	 */
	private static void asyncLog(final LogEnum logEnum, final Object obj) {
		SERVICE.execute(new Runnable() {
			public void run() {
				syncLog(logEnum, obj);
			}
		});
	}

	/**
	 * 打印日志
	 * @param logEnum 日志枚举
	 * @param obj 日志信息
	 */
	private static void log(LogEnum logEnum, Object obj) {
		// 判断同步还是异步
		if (CoreParams.LOGS_SYNC) {
			// 异步
			asyncLog(logEnum, obj);
		} else {
			// 同步
			syncLog(logEnum, obj);

		}
	}

	/**
	 * 同步打印日志
	 * @param logEnum 日志枚举
	 * @param obj 日志信息
	 */
	private static void syncLog(LogEnum logEnum, Object obj) {
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
	private Loggers() {}
}
