package com.weicoder.common.log;

/**
 * 日志接口
 * @author WD 
 * 
 */
public interface Log {
	/**
	 * 使用debug打印日志
	 * @param info 日志信息
	 */
	public void debug(Object info);

	/**
	 * 使用info打印日志
	 * @param info 日志信息
	 */
	public void info(Object info);

	/**
	 * 使用warn打印日志
	 * @param info 日志信息
	 */
	public void warn(Object info);

	/**
	 * 使用error打印日志
	 * @param info 日志信息
	 */
	public void error(Object info);
}
