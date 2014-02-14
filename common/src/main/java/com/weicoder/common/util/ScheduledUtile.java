package com.weicoder.common.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.weicoder.common.constants.DateConstants;

/**
 * 定时任务工具类
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-30
 */
public final class ScheduledUtile {
	// 保存定时任务执行器
	private final static ScheduledExecutorService	SES;
	static {
		SES = Executors.newSingleThreadScheduledExecutor();
	}

	/**
	 * 执行定时任务 按初始时间间隔
	 * @param command 线程任务
	 * @param period 间隔时间 毫秒
	 * @return
	 */
	public static ScheduledFuture<?> rate(Runnable command, long period) {
		return SES.scheduleAtFixedRate(command, 0, period, TimeUnit.MILLISECONDS);
	}

	/**
	 * 执行定时任务 按初始时间间隔
	 * @param command 线程任务
	 * @param period 间隔时间 秒
	 * @return
	 */
	public static ScheduledFuture<?> rate(Runnable command, int period) {
		return rate(command, period * DateConstants.TIME_SECOND);
	}

	/**
	 * 执行定时任务 按执行线程时间间隔
	 * @param command 线程任务
	 * @param delay 间隔时间 毫秒
	 * @return
	 */
	public static ScheduledFuture<?> delay(Runnable command, long delay) {
		return SES.scheduleWithFixedDelay(command, 0, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 执行定时任务 按执行线程间隔
	 * @param command 线程任务
	 * @param delay 间隔时间 秒
	 * @return
	 */
	public static ScheduledFuture<?> delay(Runnable command, int delay) {
		return delay(command, delay * DateConstants.TIME_SECOND);
	}

	private ScheduledUtile() {}
}
