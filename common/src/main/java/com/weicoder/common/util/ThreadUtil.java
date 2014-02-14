package com.weicoder.common.util;

/**
 * 线程工具类
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-31
 */
public final class ThreadUtil {

	/**
	 * 启动线程
	 * @param target 线程类实现
	 */
	public static void start(Runnable target) {
		new Thread(target).start();
	}

	/**
	 * 线程暂停毫秒数
	 * @param millis 毫秒
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}

	private ThreadUtil() {}
}
