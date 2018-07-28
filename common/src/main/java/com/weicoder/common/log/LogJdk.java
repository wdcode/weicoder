package com.weicoder.common.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.weicoder.common.constants.StringConstants;

/**
 * JDK实现
 * @author WD
 */
public class LogJdk implements Log {
	// JDK日志
	private Logger log;

	@Override
	public void setClass(Class<?> c) {
		log = c == null ? Logger.getLogger(StringConstants.EMPTY) : Logger.getLogger(c.getSimpleName());
	}

	@Override
	public void trace(String msg, Object... params) {
		log.log(Level.ALL, msg, params);
	}

	@Override
	public void trace(Throwable t) {
		log.log(Level.ALL, StringConstants.EMPTY, t);
	}

	@Override
	public void debug(String msg, Object... params) {
		log.log(Level.CONFIG, msg, params);
	}

	@Override
	public void debug(Throwable t) {
		log.log(Level.CONFIG, StringConstants.EMPTY, t);
	}

	@Override
	public void info(String msg, Object... params) {
		log.log(Level.INFO, msg, params);
	}

	@Override
	public void info(Throwable t) {
		log.log(Level.INFO, StringConstants.EMPTY, t);
	}

	@Override
	public void warn(String msg, Object... params) {
		log.log(Level.WARNING, msg, params);
	}

	@Override
	public void warn(Throwable t) {
		log.log(Level.WARNING, StringConstants.EMPTY, t);
	}

	@Override
	public void error(String msg, Object... params) {
		log.log(Level.SEVERE, msg, params);
	}

	@Override
	public void error(Throwable t) {
		error(StringConstants.EMPTY, t);
	}

	@Override
	public void error(String msg, Throwable t) {
		log.log(Level.SEVERE, msg, t);
	}

	@Override
	public boolean isTrace() {
		return log.isLoggable(Level.ALL);
	}

	@Override
	public boolean isDebug() {
		return log.isLoggable(Level.CONFIG);
	}

	@Override
	public boolean isInfo() {
		return log.isLoggable(Level.INFO);
	}

	@Override
	public boolean isWarn() {
		return log.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isError() {
		return log.isLoggable(Level.SEVERE);
	}

	@Override
	public void error(Throwable t, String msg, Object... params) {
		// TODO Auto-generated method stub

	}
}
