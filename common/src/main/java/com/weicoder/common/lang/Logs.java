package com.weicoder.common.lang;

import com.weicoder.common.log.Logger;
import com.weicoder.common.util.ClassUtil;

/**
 * 打印日志工具类
 * @author WD
 * @since JDK7
 * @version 1.0
 */
public final class Logs {
	// loggin日志对象
	private final static Logger	LOG;
	static {
		Class<Logger> cls = (Class<Logger>) ClassUtil.forName("com.weicoder.core.log.LoggerSlf4j"); // ClassUtil.getAssignedClass(Logger.class, 0);
		// 如果没有实现 赋值空实现
		if (cls == null) {
			LOG = new Logger() {
				@Override
				public void warn(Object info) {}

				@Override
				public void info(Object info) {}

				@Override
				public void error(Object info) {}

				@Override
				public void debug(Object info) {}
			};
		} else {
			LOG = ClassUtil.newInstance(cls);
		}
	}

	/**
	 * 使用debug打印日志
	 * @param info 日志信息
	 */
	public static void debug(Object info) {
		LOG.debug(info);
	}

	/**
	 * 使用info打印日志
	 * @param info 日志信息
	 */
	public static void info(Object info) {
		LOG.info(info);
	}

	/**
	 * 使用warn打印日志
	 * @param info 日志信息
	 */
	public static void warn(Object info) {
		LOG.warn(info);
	}

	/**
	 * 使用error打印日志
	 * @param info 日志信息
	 */
	public static void error(Object info) {
		LOG.error(info);
	}
}
