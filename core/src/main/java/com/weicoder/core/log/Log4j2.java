package com.weicoder.core.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.log.Log;

/**
 * Log4j2 实现
 * @author WD
 */
public class Log4j2 implements Log {
	// 日志
	private Logger log = LogManager.getLogger();

	@Override
	public void trace(String msg, Object... params) {
		log.trace(msg, params);
	}

	@Override
	public void trace(Throwable t) {
		log.trace(StringConstants.EMPTY, t);
	}

	@Override
	public void debug(String msg, Object... params) {
		log.debug(msg, params);
	}

	@Override
	public void debug(Throwable t) {
		log.debug(StringConstants.EMPTY, t);
	}

	@Override
	public void info(String msg, Object... params) {
		log.info(msg, params);
	}

	@Override
	public void info(Throwable t) {
		log.info(StringConstants.EMPTY, t);
	}

	@Override
	public void warn(String msg, Object... params) {
		log.warn(msg, params);
	}

	@Override
	public void warn(Throwable t) {
		log.warn(StringConstants.EMPTY, t);
	}

	@Override
	public void error(String msg, Object... params) {
		log.error(msg, params);
	}

	@Override
	public void error(Throwable t) {
		error(StringConstants.EMPTY, t);
	}

	@Override
	public void error(String msg, Throwable t) {
		log.error(StringConstants.EMPTY, t);
	}

	@Override
	public boolean isTrace() {
		return log.isTraceEnabled();
	}

	@Override
	public boolean isDebug() {
		return log.isDebugEnabled();
	}

	@Override
	public boolean isInfo() {
		return log.isInfoEnabled();
	}

	@Override
	public boolean isWarn() {
		return log.isWarnEnabled();
	}

	@Override
	public boolean isError() {
		return log.isErrorEnabled();
	}
}
