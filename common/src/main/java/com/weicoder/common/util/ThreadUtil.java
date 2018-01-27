package com.weicoder.common.util;

import com.weicoder.common.log.Logs;

/**
 * Thread 相关方法
 * @author WD
 */
public final class ThreadUtil {
	/**
	 * 封装sleep异常处理
	 * @param millis
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Logs.error(e);
		}
	}

	/**
	 * 执行线程
	 * @param target 线程接口
	 */
	public static void start(Runnable target) {
		new Thread(target).start();
	}

	private ThreadUtil() {}
}
