package com.weicoder.log;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.lookup.MainMapLookup;

import com.weicoder.common.constants.StringConstants; 
import com.weicoder.common.W;
import com.weicoder.common.log.Log;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.StringUtil;
import com.weicoder.log.params.Log4j2Params;

/**
 * Log4j2 实现
 * 
 * @author WD
 */
public class Log4j2 implements Log {
	// 日志
	private Logger log;

	public Log4j2() {
		MainMapLookup.setMainArguments(Log4j2Params.DIR, Log4j2Params.LEVEL,
				Log4j2Params.TEST ? Log4j2Params.LEVEL : "OFF");
	}

	@Override
	public void setClass(Class<?> c) {
		log = c == null ? LogManager.getLogger(getClass()) : LogManager.getLogger(c);
	}

	@Override
	public void trace(String msg, Object... params) {
		log.trace(msg, params(params));
	}

	@Override
	public void trace(Throwable t) {
		log.trace(StringConstants.EMPTY, t);
	}

	@Override
	public void debug(String msg, Object... params) {
		log.debug(msg, params(params));
	}

	@Override
	public void debug(Throwable t) {
		log.debug(StringConstants.EMPTY, t);
	}

	@Override
	public void info(String msg, Object... params) {
		log.info(msg, params(params));
	}

	@Override
	public void info(Throwable t) {
		log.info(StringConstants.EMPTY, t);
	}

	@Override
	public void warn(String msg, Object... params) {
		log.warn(msg, params(params));
	}

	@Override
	public void warn(Throwable t) {
		log.warn(StringConstants.EMPTY, t);
	}

	@Override
	public void error(String msg, Object... params) {
		log.error(msg, params(params));
	}

	@Override
	public void error(Throwable t) {
		error(StringConstants.EMPTY, t);
	}

	@Override
	public void error(String msg, Throwable t) {
		log.error(msg, t);
	}

	@Override
	public void error(Throwable t, String msg, Object... params) {
		error(String.format(StringUtil.replaceAll(msg, "\\{}", "%s"), params(params)), t);
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

	/**
	 * 转换日志 1 把字符串长于一定程度的信息截取 2把数组变成字符串 并截取一定长度
	 * 
	 * @param  params 写日志参数
	 * @return        参数
	 */
	private static Object[] params(Object... params) {
		// 开启日志截取
		if (CommonParams.LOGS_LEN > 0)
			// 循环处理日志
			for (int i = 0; i < params.length; i++) {
				// 转换对象
				Object obj = params[i];
				// 判断类型 byte[]
				if (obj instanceof byte[])
					obj = Arrays.toString((byte[]) obj);
				else if (obj instanceof String[])
					obj = Arrays.toString((String[]) obj);
				// 获得对象
				params[i] = StringUtil.subString(W.C.toString(obj), 0, CommonParams.LOGS_LEN);
			}
		// 返回对象
		return params;
	}
}
