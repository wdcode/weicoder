package com.weicoder.common.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.weicoder.common.constants.C;

/**
 * JDK实现
 * 
 * @author WD
 */
public class LogJdk implements Log {
	// JDK日志
	private Logger log;
	 
	@Override
	public void setClass(Class<?> c) {
		log = c == null ? Logger.getLogger(C.S.EMPTY) : Logger.getLogger(c.getSimpleName());
	}

	@Override
	public void trace(String msg, Object... params) {
		if (isTrace())
			log.log(Level.ALL, msg, params);
	}

	@Override
	public void trace(Throwable t) {
		if (isTrace())
			log.log(Level.ALL, C.S.EMPTY, t);
	}

	@Override
	public void debug(String msg, Object... params) {
		if (isDebug())
			log.log(Level.CONFIG, msg, params);
	}

	@Override
	public void debug(Throwable t) {
		if (isDebug())
			log.log(Level.CONFIG, C.S.EMPTY, t);
	}

	@Override
	public void info(String msg, Object... params) {
		if (isInfo())
			log.log(Level.INFO, msg, params);
	}

	@Override
	public void info(Throwable t) {
		if (isInfo())
			log.log(Level.INFO, C.S.EMPTY, t);
	}

	@Override
	public void warn(String msg, Object... params) {
		if (isWarn())
			log.log(Level.WARNING, msg, params);
	}

	@Override
	public void warn(Throwable t) {
		if (isWarn())
			log.log(Level.WARNING, C.S.EMPTY, t);
	}

	@Override
	public void error(String msg, Object... params) {
		if (isError())
			log.log(Level.SEVERE, msg, params);
	}

	@Override
	public void error(Throwable t) {
		error(C.S.EMPTY, t);
	}

	@Override
	public void error(String msg, Throwable t) {
		if (isError())
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
		error(t);
		error(msg, params);
	}
}
