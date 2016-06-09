package com.weicoder.core.log;

import org.slf4j.LoggerFactory;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.log.Log;

/**
 * slf4j日志接口实现
 * @author WD  
 */
public final class LoggerSlf4j implements Log {
	// Logger日志对象
	private org.slf4j.Logger log = LoggerFactory.getLogger(StringConstants.EMPTY);

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
		log.error(StringConstants.EMPTY, t);
	}
}
