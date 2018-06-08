package com.weicoder.common.log;

import java.util.Arrays;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 打印日志工具类
 * @author WD
 */
public final class Logs {
	// loggin日志对象
	private final static Log LOG = ClassUtil.newInstance(CommonParams.LOG_CLASS,
			new LogJdk());

	/**
	 * 使用trace打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void trace(String msg, Object... params) {
		if (LOG.isTrace())
			LOG.trace(msg, params(params));
	}

	/**
	 * 使用trace打印日志
	 * @param t 异常
	 */
	public static void trace(Throwable t) {
		if (LOG.isTrace())
			LOG.trace(t);
	}

	/**
	 * 使用debug打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void debug(String msg, Object... params) {
		if (LOG.isDebug())
			LOG.debug(msg, params(params));
	}

	/**
	 * 使用debug打印日志
	 * @param t 异常
	 */
	public static void debug(Throwable t) {
		if (LOG.isDebug())
			LOG.debug(t);
	}

	/**
	 * 使用info打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void info(String msg, Object... params) {
		if (LOG.isInfo())
			LOG.info(msg, params(params));
	}

	/**
	 * 使用info打印日志
	 * @param t 异常
	 */
	public static void info(Throwable t) {
		if (LOG.isInfo())
			LOG.info(t);
	}

	/**
	 * 使用warn打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void warn(String msg, Object... params) {
		if (LOG.isWarn())
			LOG.warn(msg, params(params));
	}

	/**
	 * 使用warn打印日志
	 * @param t 异常
	 */
	public static void warn(Throwable t) {
		if (LOG.isWarn())
			LOG.warn(t);
	}

	/**
	 * 使用error打印日志
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void error(String msg, Object... params) {
		if (LOG.isError())
			LOG.error(msg, params(params));
	}

	/**
	 * 使用error打印日志
	 * @param t 异常
	 */
	public static void error(Throwable t) {
		if (LOG.isError())
			LOG.error(t);
	}

	/**
	 * 使用error打印日志
	 * @param msg 信息
	 * @param t 异常
	 */
	public static void error(String msg, Throwable t) {
		if (LOG.isError())
			LOG.error(msg, t);
	}

	/**
	 * 使用error打印日志
	 * @param t 异常
	 * @param msg 信息 可以是字符串xxx{}xxx
	 * @param params 字符串格式化参数
	 */
	public static void error(Throwable t, String msg, Object... params) {
		if (LOG.isError())
			LOG.error(String.format(StringUtil.replaceAll(msg, "\\{}", "%s"),
					params(params)), t);
	}

	/**
	 * 转换日志 1 把字符串长于一定程度的信息截取 2把数组变成字符串 并截取一定长度
	 * @param params 写日志参数
	 * @return 参数
	 */
	private static Object[] params(Object... params) {
		// 开启日志截取
		if (CommonParams.LOGS_LEN > 0) {
			// 循环处理日志
			for (int i = 0; i < params.length; i++) {
				// 转换对象
				Object obj = params[i];
				// 判断类型 byte[]
				if (obj instanceof byte[]) {
					obj = Arrays.toString((byte[]) obj);
				}
				// 是String类型
				if (obj instanceof String) {
					obj = StringUtil.subString(Conversion.toString(obj), 0,
							CommonParams.LOGS_LEN);
				}
				// 获得对象
				params[i] = obj;
			}
		}
		// 返回对象
		return params;
	}

	private Logs() {}
}
