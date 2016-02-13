package com.weicoder.core.log;

import org.slf4j.LoggerFactory;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.log.Logger;

/**
 * slf4j日志接口实现
 * @author WD
 * @since JDK7
 * @version 1.0
 */
public class LoggerSlf4j implements Logger {
	// Logger日志对象
	private org.slf4j.Logger	log	= LoggerFactory.getLogger(StringConstants.EMPTY);

	@Override
	public void debug(Object info) {
		if (log.isDebugEnabled()) {
			log.debug(Conversion.toString(info), info);
		}
	}

	@Override
	public void info(Object info) {
		if (log.isInfoEnabled()) {
			log.info(Conversion.toString(info), info);
		}
	}

	@Override
	public void warn(Object info) {
		if (log.isWarnEnabled()) {
			log.warn(Conversion.toString(info), info);
		}
	}

	@Override
	public void error(Object info) {
		if (log.isErrorEnabled()) {
			log.error(Conversion.toString(info), info);
		}
	}
}
