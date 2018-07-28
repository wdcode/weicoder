package com.weicoder.common.log;

/**
 * 日志接口
 * @author WD
 */
public interface Log {
	/**
	 * 设置类
	 * @param c
	 */
	void setClass(Class<?> c);

	/**
	 * 使用trace打印日志
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	void trace(String msg, Object... params);

	/**
	 * 使用trace打印日志
	 * @param t 异常
	 */
	void trace(Throwable t);

	/**
	 * 使用debug打印日志
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	void debug(String msg, Object... params);

	/**
	 * 使用debug打印日志
	 * @param t 异常
	 */
	void debug(Throwable t);

	/**
	 * 使用info打印日志
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	void info(String msg, Object... params);

	/**
	 * 使用info打印日志
	 * @param t 异常
	 */
	void info(Throwable t);

	/**
	 * 使用debug打印日志
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	void warn(String msg, Object... params);

	/**
	 * 使用debug打印日志
	 * @param t 异常
	 */
	void warn(Throwable t);

	/**
	 * 使用error打印日志
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	void error(String msg, Object... params);

	/**
	 * 使用error打印日志
	 * @param t 异常
	 * @param msg 信息 可以是字符串xxx%sxxx
	 * @param params 字符串格式化参数
	 */
	void error(Throwable t, String msg, Object... params);

	/**
	 * 使用error打印日志
	 * @param msg 信息
	 * @param t 异常
	 */
	void error(String msg, Throwable t);

	/**
	 * 使用debug打印日志
	 * @param t 异常
	 */
	void error(Throwable t);

	/**
	 * 是否trace 级别
	 * @return true 是 false 否
	 */
	boolean isTrace();

	/**
	 * 是否debug 级别
	 * @return true 是 false 否
	 */
	boolean isDebug();

	/**
	 * 是否info 级别
	 * @return true 是 false 否
	 */
	boolean isInfo();

	/**
	 * 是否warn 级别
	 * @return true 是 false 否
	 */
	boolean isWarn();

	/**
	 * 是否trace 级别
	 * @return true 是 false 否
	 */
	boolean isError();
}
