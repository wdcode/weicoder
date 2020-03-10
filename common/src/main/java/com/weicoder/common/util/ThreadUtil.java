package com.weicoder.common.util;

import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.log.Logs;

/**
 * Thread 相关方法
 * 
 * @author WD
 */
public class ThreadUtil {
	/**
	 * 封装sleep异常处理
	 * 
	 * @param millis 暂停时间毫秒
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Logs.error(e);
		}
	}

	/**
	 * 封装sleep异常处理
	 * 
	 * @param millis 暂停时间毫秒
	 */
	public static void sleep(int millis) {
		sleep(millis * DateConstants.TIME_SECOND);
	}

	/**
	 * 执行线程
	 * 
	 * @param target 线程接口
	 */
	public static void start(Runnable target) {
		new Thread(target).start();
	}
}
