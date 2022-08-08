package com.weicoder.common.util;

import java.util.Map;

import com.weicoder.common.W.M;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.log.Logs;

/**
 * Thread 相关方法
 * 
 * @author WD
 */
public class ThreadUtil {
	// 保存线程属性
	private final static Map<Thread, Map<String, Object>> ATTR = M.newMap();
	
//	public static Map<Thread, Map<String, Object>> attr() {
//		return ATTR;
//	}

	/**
	 * 为当前线程保存属性
	 * 
	 * @param key 键
	 * @param val 值
	 */
	public static Object put(String key, Object val) {
		return M.getMap(ATTR, current()).put(key, val);
	}

	/**
	 * 获得当前线程的值
	 * 
	 * @param  key 键
	 * @return     值
	 */
	public static Object get(String key) {
		return M.getMap(ATTR, current()).get(key);
	}

	/**
	 * 获得当前线程
	 */
	public static Thread current() {
		return Thread.currentThread();
	}

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
