package com.weicoder.common.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.log.Logs;

/**
 * 定时任务工具类
 * @author WD
 */
public final class ScheduledUtil {
	/** 并发定时任务池 */
	public final static ScheduledExecutorService POOL = Executors.newScheduledThreadPool(SystemConstants.CPU_NUM, DaemonThreadFactory.INSTANCE);

	/**
	 * 执行定时任务 按初始时间间隔
	 * @param command 线程任务
	 * @param period 间隔时间 毫秒
	 * @return ScheduledFuture
	 */
	public static ScheduledFuture<?> rate(Runnable command, long period) {
		return rate(command, period, period, TimeUnit.MILLISECONDS);
	}

	/**
	 * 执行定时任务 按初始时间间隔
	 * @param command 线程任务
	 * @param initialDelay 初始化时间
	 * @param period 间隔时间
	 * @param unit 时间戳
	 * @return ScheduledFuture
	 */
	public static ScheduledFuture<?> rate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		Logs.debug("ScheduledUtile rate command={},initialDelay={},period={},unit={}", command, initialDelay, period, unit);
		return POOL.scheduleAtFixedRate(command, initialDelay, period, unit);
	}

	/**
	 * 执行定时任务 按初始时间间隔
	 * @param command 线程任务
	 * @param period 间隔时间 秒
	 * @return ScheduledFuture
	 */
	public static ScheduledFuture<?> rate(Runnable command, int period) {
		return rate(command, period * DateConstants.TIME_SECOND);
	}

	/**
	 * 执行定时任务 按执行线程时间间隔
	 * @param command 线程任务
	 * @param delay 间隔时间 毫秒
	 * @return ScheduledFuture
	 */
	public static ScheduledFuture<?> delay(Runnable command, long delay) {
		return delay(command, delay, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 执行定时任务 按执行线程时间间隔
	 * @param command 线程任务
	 * @param initialDelay 初始化时间
	 * @param delay 间隔时间
	 * @param unit 时间戳
	 * @return ScheduledFuture
	 */
	public static ScheduledFuture<?> delay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		Logs.debug("ScheduledUtile rate command={},initialDelay={},delay={},unit={}", command, initialDelay, delay, unit);
		return POOL.scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}

	/**
	 * 执行定时任务 按执行线程间隔
	 * @param command 线程任务
	 * @param delay 间隔时间 秒
	 * @return ScheduledFuture
	 */
	public static ScheduledFuture<?> delay(Runnable command, int delay) {
		return delay(command, delay * DateConstants.TIME_SECOND);
	}

	private ScheduledUtil() {}
}
