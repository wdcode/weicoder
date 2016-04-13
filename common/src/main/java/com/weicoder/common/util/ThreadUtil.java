package com.weicoder.common.util;

import com.weicoder.common.log.Logs;

/**
 * 线程工具类
 * @author WD 
 * @version 1.0  
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
		} catch (InterruptedException e) {
			Logs.warn(e);
		}
	}

	private ThreadUtil() {}
}
