package com.weicoder.common.concurrent;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;

/**
 * 并发线程任务处理
 * @author WD
 */
public final class ExecutorUtil {
	// 线程池
	private final static ExecutorFactory		FACTORY		= new ExecutorFactory();
	// 保存线程
	private final static List<Runnable>			RUNNABLES	= Lists.newList();
	private final static List<Callable<Object>>	CALLABLES	= Lists.newList();

	/**
	 * 获得新的缓存线程池
	 * @param pool 线程池数量
	 * @param daemon 是否守护线程
	 * @return 缓存线程池
	 */
	public static ExecutorService newPool(int pool, boolean daemon) {
		return FACTORY.newPool(pool, daemon);
	}

	/**
	 * 获得线程池 此方法返回守护线程的池
	 * @return 线程池
	 */
	public static ExecutorService pool() {
		return pool(StringConstants.EMPTY);
	}

	/**
	 * 获得线程池
	 * @param name 名称
	 * @return 线程池
	 */
	public static ExecutorService pool(String name) {
		return FACTORY.getInstance(name);
	}

	/**
	 * 添加线程Runnable
	 * @param task 任务
	 */
	public static void addR(Runnable task) {
		RUNNABLES.add(task);
	}

	/**
	 * 添加线程Callable
	 * @param task 任务
	 */
	public static void addC(Callable<Object> task) {
		CALLABLES.add(task);
	}

	/**
	 * 执行列表中的任务
	 */
	public static void execute() {
		// 声明列表
		List<Runnable> tasks = Lists.newList(RUNNABLES);
		// 清空任务
		RUNNABLES.clear();
		// 执行线程
		execute(tasks);
	}

	/**
	 * 执行列表中的任务
	 * @return 列表
	 */
	public static List<Object> submit() {
		// 声明列表
		List<Callable<Object>> calls = Lists.newList(CALLABLES);
		// 清空任务
		CALLABLES.clear();
		// 执行线程
		return submit(calls);
	}

	/**
	 * 执行任务 等待任务结束
	 * @param tasks 任务
	 */
	public static void execute(List<Runnable> tasks) {
		// 声明结果列表
		List<Future<?>> list = Lists.newList(tasks.size());
		// 执行任务
		tasks.forEach(task -> {
			list.add(pool().submit(task));
		});
		// 循环等待
		while (pool().isTerminated()) {
			// 是否全部完成
			for (Iterator<Future<?>> it = list.iterator(); it.hasNext();) {
				if (it.next().isDone())
					it.remove();
			}
			// 如果列表为空
			if (EmptyUtil.isEmpty(list)) {
				break;
			}
		}
	}

	/**
	 * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
	 * @param tasks Runnable 任务
	 * @param <T> 泛型
	 * @return 表示该任务的 Future
	 */
	public static <T> List<T> submit(List<Callable<T>> tasks) {
		return submit(tasks, 0);
	}

	/**
	 * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
	 * @param tasks Runnable 任务
	 * @param timeout 如果可以最多等待的时间
	 * @param <T> 泛型
	 * @return 表示该任务的 Future
	 */
	public static <T> List<T> submit(List<Callable<T>> tasks, long timeout) {
		// 获得列表长度
		int len = tasks.size();
		// 声明结果列表
		List<Future<T>> list = Lists.newList(len);
		// 声明返回列表
		List<T> ls = Lists.newList(len);
		// 执行任务
		tasks.forEach(task -> {
			list.add(pool().submit(task));
		});
		// 循环获得结果
		list.forEach(f -> {
			try {
				if (timeout > 0)
					ls.add(f.get(timeout, TimeUnit.MILLISECONDS));
				else
					ls.add(f.get());
			} catch (Exception e) {
				Logs.warn(e);
			}
		});
		// 返回列表
		return ls;
	}

	private ExecutorUtil() {}
}