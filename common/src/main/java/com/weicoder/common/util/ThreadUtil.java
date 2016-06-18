package com.weicoder.common.util;

import com.weicoder.common.log.Logs;

/**
 * 线程工具类
 * @author WD
 */
public final class ThreadUtil {
	/**
	 * 启动线程
	 * @param target 线程类实现
	 */
	public static void start(Runnable target) {
		// 声明线程
		Thread t = new Thread(target);
		// 设置成守护线程
		t.setDaemon(true);
		// 启动线程
		t.start();
	}

	/**
	 * 线程暂停毫秒数
	 * @param millis 毫秒
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Logs.warn(e);
		}
	}

	private ThreadUtil() {
	}
}
