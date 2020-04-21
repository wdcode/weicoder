package com.weicoder.common.log;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.ClassUtil;

/**
 * 日志工厂
 * @author WD
 */
public final class LogFactory extends FactoryKey<Class<?>, Log> {
	// 工厂
	private final static LogFactory FACTORY = new LogFactory();

	/**
	 * 获得默认日志
	 * @return
	 */
	public final static Log getLog() {
		return FACTORY.getInstance();
	}

	/**
	 * 根据类获得相关类日志
	 * @param key
	 * @return
	 */
	public final static Log getLog(Class<?> key) {
		return FACTORY.getInstance(key);
	}

	@Override
	public Log newInstance(Class<?> key) {
		Log log = ClassUtil.newInstance(CommonParams.LOG_CLASS, new LogJdk());
		log.setClass(key);
		return log;
	}

	private LogFactory() {}
}
