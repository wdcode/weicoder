package com.weicoder.core.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.weicoder.common.util.ExecutorUtil;
import com.weicoder.core.params.CoreParams;

/**
 * 用于记录各种日志以及打印DEBG信息等<br/>
 * @author WD
 * @since JDK7
 * @version 1.0 2012-02-17
 */
public final class Logs {
	// common loggin日志对象
	private final static Log	LOG	= LogFactory.getLog(Logs.class);

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
	 * 使用fatal打印日志
	 * @param info 日志信息
	 */
	public static void fatal(Object info) {
		if (LOG.isFatalEnabled()) {
			log(LogEnum.FATAL, info);
		}
	}

	/**
	 * 异步打印日志
	 * @param logEnum 日志枚举
	 * @param obj 日志信息
	 */
	private static void asyncLog(final LogEnum logEnum, final Object obj) {
		ExecutorUtil.execute(new Runnable() {
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
		Object mess = null;
		// 异常
		Throwable t = null;
		// 判断传人信息类型
		if (obj instanceof Throwable) {
			t = (Throwable) obj;
		} else {
			mess = obj;
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
			case FATAL:
				if (LOG.isFatalEnabled()) {
					LOG.fatal(mess, t);
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
		DEBUG, INFO, WARN, ERROR, FATAL
	}

	/**
	 * 私有构造
	 */
	private Logs() {}
}
