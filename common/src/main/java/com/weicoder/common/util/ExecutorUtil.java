package com.weicoder.common.util;

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
	// 并发服务类
	private final static ExecutorService	ES;
	static {
		ES = Executors.newFixedThreadPool(CommonParams.THREAD_POOL);
	}

	/**
	 * 执行任务 不需要等待
	 * @param tasks 任务
	 */
	public static void execute(Runnable task) {
		ES.execute(task);
	}

	/**
	 * 执行任务 不需要等待
	 * @param tasks 任务
	 */
	public static <T> Future<T> execute(Callable<T> task) {
		return ES.submit(task);
	}

	/**
	 * 执行任务 等待任务结束
	 * @param tasks 任务
	 */
	public static void execute(List<Runnable> tasks) {
		execute(Lists.toArray(tasks));
	}

	/**
	 * 执行任务 等待任务结束
	 * @param tasks 任务
	 */
	public static void execute(Runnable... tasks) {
		// 声明线程池
		ExecutorService es = Executors.newFixedThreadPool(CommonParams.THREAD_POOL > tasks.length ? tasks.length : CommonParams.THREAD_POOL);
		// 执行任务
		for (Runnable task : tasks) {
			es.execute(task);
		}
		// 关闭线程池
		es.shutdown();
		// 循环等待
		while (true) {
			// 是否全部完成
			if (es.isTerminated()) {
				// 跳槽循环
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
		return submit(Lists.toArray(tasks));
	}

	/**
	 * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
	 * @param task Runnable 任务
	 * @param timeout 如果可以最多等待的时间
	 * @return 表示该任务的 Future
	 */
	public static <T> List<T> submit(long timeout, List<Callable<T>> tasks) {
		return submit(timeout, Lists.toArray(tasks));
	}

	/**
	 * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
	 * @param task Runnable 任务
	 * @return 表示该任务的 Future
	 */
	public static <T> List<T> submit(Callable<T>... tasks) {
		return submit(CommonParams.THREAD_TIME_OUT, tasks);
	}

	/**
	 * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
	 * @param task Runnable 任务
	 * @param timeout 如果可以最多等待的时间
	 * @return 表示该任务的 Future
	 */
	public static <T> List<T> submit(long timeout, Callable<T>... tasks) {
		// 声明线程池
		ExecutorService es = Executors.newFixedThreadPool(CommonParams.THREAD_POOL > tasks.length ? tasks.length : CommonParams.THREAD_POOL);
		// 声明结果列表
		List<Future<T>> list = Lists.getList(tasks.length);
		// 声明返回列表
		List<T> ls = Lists.getList(tasks.length);
		// 执行任务
		for (Callable<T> task : tasks) {
			list.add(es.submit(task));
		}
		// 循环获得结果
		for (Future<T> f : list) {
			try {
				ls.add(f.get(timeout, TimeUnit.MILLISECONDS));
			} catch (Exception e) {}
		}
		// 关闭线程池
		es.shutdown();
		// 返回列表
		return ls;
	}

	/**
	 * 私有构造
	 */
	private ExecutorUtil() {}
}
