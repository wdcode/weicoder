package com.weicoder.common.util;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.params.CommonParams;

/**
 * 并发线程任务处理
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-10
 */
public final class ExecutorUtil {
	/** 并发线程池 */
	public final static ExecutorService			POOL		= Executors.newFixedThreadPool(CommonParams.THREAD_POOL);
	// 保存线程
	private final static List<Runnable>			RUNNABLES	= Lists.getList();
	private final static List<Callable<Object>>	CALLABLES	= Lists.getList();

	/**
	 * 添加线程
	 * @param task
	 */
	public static void add(Runnable task) {
		RUNNABLES.add(task);
	}

	/**
	 * 添加线程
	 * @param task
	 */
	public static void add(Callable<Object> task) {
		CALLABLES.add(task);
	}

	/**
	 * 执行列表中的任务
	 */
	public static void execute() {
		// 声明列表
		List<Runnable> tasks = Lists.getList(RUNNABLES);
		// 清空任务
		RUNNABLES.clear();
		// 执行线程
		execute(tasks);
	}

	/**
	 * 执行列表中的任务
	 */
	public static List<Object> submit() {
		// 声明列表
		List<Callable<Object>> calls = Lists.getList(CALLABLES);
		// 清空任务
		CALLABLES.clear();
		// 执行线程
		return submit(calls);
	}

	/**
	 * 执行任务 不需要等待
	 * @param tasks 任务
	 */
	public static void execute(Runnable task) {
		POOL.execute(task);
	}

	/**
	 * 执行任务 不需要等待
	 * @param tasks 任务
	 */
	public static <T> Future<T> submit(Callable<T> task) {
		return POOL.submit(task);
	}

	/**
	 * 执行任务 等待任务结束
	 * @param tasks 任务
	 */
	public static void execute(List<Runnable> tasks) {
		// 声明结果列表
		List<Future<?>> list = Lists.getList(tasks.size());
		// 执行任务
		for (Runnable task : tasks) {
			list.add(POOL.submit(task));
		}
		// 循环等待
		while (true) {
			// 是否全部完成
			for (Iterator<Future<?>> it = list.iterator(); it.hasNext();) {
				if (it.next().isDone()) {
					it.remove();
				}
			}
			// 如果列表为空
			if (EmptyUtil.isEmpty(list)) {
				break;
			}
			// 等待
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}

	/**
	 * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
	 * @param task Runnable 任务
	 * @return 表示该任务的 Future
	 */
	public static <T> List<T> submit(List<Callable<T>> tasks) {
		return submit(tasks, 0);
	}

	/**
	 * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
	 * @param task Runnable 任务
	 * @param timeout 如果可以最多等待的时间
	 * @return 表示该任务的 Future
	 */
	public static <T> List<T> submit(List<Callable<T>> tasks, long timeout) {
		// 获得列表长度
		int len = tasks.size();
		// 声明结果列表
		List<Future<T>> list = Lists.getList(len);
		// 声明返回列表
		List<T> ls = Lists.getList(len);
		// 执行任务
		for (Callable<T> task : tasks) {
			list.add(POOL.submit(task));
		}
		// 循环获得结果
		for (Future<T> f : list) {
			try {
				if (timeout > 0) {
					ls.add(f.get(timeout, TimeUnit.MILLISECONDS));
				} else {
					ls.add(f.get());
				}
			} catch (Exception e) {}
		}
		// 返回列表
		return ls;
	}

	/** 私有构造 */
	private ExecutorUtil() {}
}
