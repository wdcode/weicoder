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
	// 并发定时任务池
	private final static ScheduledExecutorService	POOL		= newPool(SystemConstants.CPU_NUM * 2, false);
	// 守护线程并发定时任务池
	private final static ScheduledExecutorService	DAEMON_POOL	= newPool(SystemConstants.CPU_NUM * 2, true);

	/**
	 * 获得新的线程任务池
	 * @param size 池数量
	 * @param daemon 是否守护线程
	 * @return 线程任务池
	 */
	public static ScheduledExecutorService newPool(int size, boolean daemon) {
		return daemon ? Executors.newScheduledThreadPool(size, DaemonThreadFactory.INSTANCE)
				: Executors.newScheduledThreadPool(size);
	}

	/**
	 * 获得定时任务池 此方法返回守护线程的池
	 * @return 定时任务池
	 */
	public static ScheduledExecutorService pool() {
		return pool(true);
	}

	/**
	 * 获得定时任务池
	 * @param daemon 是否守护线程
	 * @return 定时任务池
	 */
	public static ScheduledExecutorService pool(boolean daemon) {
		return daemon ? DAEMON_POOL : POOL;
	}

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
		Logs.debug("ScheduledUtile rate command={},initialDelay={},period={},unit={}", command, initialDelay, period,
				unit);
		return pool().scheduleAtFixedRate(command, initialDelay, period, unit);
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
		Logs.debug("ScheduledUtile rate command={},initialDelay={},delay={},unit={}", command, initialDelay, delay,
				unit);
		return pool().scheduleWithFixedDelay(command, initialDelay, delay, unit);
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
