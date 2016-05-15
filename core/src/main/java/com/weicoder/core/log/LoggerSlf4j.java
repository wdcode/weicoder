package com.weicoder.core.log;

import org.slf4j.LoggerFactory;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.log.Log;

/**
 * slf4j日志接口实现
 * @author WD 
 * @version 1.0
 */
public class LoggerSlf4j implements Log {
	// Logger日志对象
	private org.slf4j.Logger log = LoggerFactory.getLogger(StringConstants.EMPTY);

	@Override
	public void debug(Object info) {
		if (log.isDebugEnabled()) {
			if (info instanceof Throwable) {
				log.debug(StringConstants.EMPTY, info);
			} else {
				log.debug(Conversion.toString(info));
			}
		}
	}

	@Override
	public void info(Object info) {
		if (log.isInfoEnabled()) {
			if (info instanceof Throwable) {
				log.info(StringConstants.EMPTY, info);
			} else {
				log.info(Conversion.toString(info));
			}
		}
	}

	@Override
	public void warn(Object info) {
		if (log.isWarnEnabled()) {
			if (info instanceof Throwable) {
				log.warn(StringConstants.EMPTY, info);
			} else {
				log.warn(Conversion.toString(info));
			}
		}
	}

	@Override
	public void error(Object info) {
		if (log.isErrorEnabled()) {
			if (info instanceof Throwable) {
				log.error(StringConstants.EMPTY, info);
			} else {
				log.error(Conversion.toString(info));
			}
		}
	}
}
