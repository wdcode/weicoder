package com.weicoder.common.concurrent;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.W;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.U;
import com.weicoder.common.constants.C;
import com.weicoder.common.interfaces.CallbackVoid;

/**
 * 并发线程任务处理
 * 
 * @author WD
 */
public class ExecutorUtil {
	// 线程池
	private final static ExecutorFactory		FACTORY		= new ExecutorFactory();
	// 保存线程
	private final static List<Runnable>			RUNNABLES	= Lists.newList();
	private final static List<Callable<Object>>	CALLABLES	= Lists.newList();

	/**
	 * 获得核心数为1新的缓存线程池执行线程
	 * 
	 * @return 缓存线程池
	 */
	public static void newExecute(Runnable command) {
		newSingle().execute(command);
	}

	/**
	 * 获得核心数为1新的缓存线程池
	 * 
	 * @return 缓存线程池
	 */
	public static ExecutorService newSingle() {
		return Executors.newSingleThreadExecutor(DaemonThreadFactory.INSTANCE);
	}

	/**
	 * 获得新的缓存线程池
	 * 
	 * @param pool   线程池数量
	 * @param daemon 是否守护线程
	 * @return 缓存线程池
	 */
	public static ExecutorService newPool(int pool, boolean daemon) {
		return FACTORY.newPool(pool, daemon);
	}

	/**
	 * 使用pool缓存线程池执行线程
	 * 
	 * @return 缓存线程池
	 */
	public static void execute(Runnable command) {
		pool().execute(command);
	}

	/**
	 * 获得线程池 此方法返回守护线程的池
	 * 
	 * @return 线程池
	 */
	public static ExecutorService pool() {
		return pool(C.S.EMPTY);
	}

	/**
	 * 获得线程池
	 * 
	 * @param name 名称
	 * @return 线程池
	 */
	public static ExecutorService pool(String name) {
		return FACTORY.getInstance(name);
	}

	/**
	 * 添加线程Runnable
	 * 
	 * @param task 任务
	 */
	public static void addR(Runnable task) {
		RUNNABLES.add(task);
	}

	/**
	 * 添加线程Callable
	 * 
	 * @param task 任务
	 */
	public static void addC(Callable<Object> task) {
		CALLABLES.add(task);
	}

	/**
	 * 执行列表中的任务
	 */
	public static void execute() {
		execute(W.L.copy(RUNNABLES));
	}

	/**
	 * 执行列表中的任务
	 * 
	 * @return 列表
	 */
	public static List<Object> submit() {
		return submit(W.L.copy(CALLABLES));
	}

	/**
	 * 执行任务 等待任务结束
	 * 
	 * @param tasks 任务
	 */
	public static void execute(List<Runnable> tasks) {
		CountDownLatch latch = new CountDownLatch(tasks.size());
		tasks.forEach(r -> pool().execute(() -> {
			r.run();
			latch.countDown();
		}));
		try {
			latch.await();
		} catch (InterruptedException e) {
		}
//		// 声明结果列表
//		List<Future<?>> list = W.L.newList(tasks.size());
//		// 执行任务
//		ExecutorService es = Executors.newFixedThreadPool(8,U.DTF.INSTANCE);
//		tasks.forEach(task -> list.add(es.submit(task)));
////		 循环等待
//		while (true) {
//			// 是否全部完成
//			for (Iterator<Future<?>> it = list.iterator(); it.hasNext();)
//				if (it.next().isDone())
//					it.remove();
//			// 暂停10毫秒
////			U.T.sleep(10L);
//			try {
//				es.awaitTermination(10L, TimeUnit.MILLISECONDS);
//			} catch (InterruptedException e) {
//			}
//			// 如果列表为空
//			if (U.E.isEmpty(list))
//				break;
//		}
	}

	/**
	 * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
	 * 
	 * @param tasks Runnable 任务
	 * @param <T>   泛型
	 * @return 表示该任务的 Future
	 */
	public static <T> List<T> submit(List<Callable<T>> tasks) {
		return submit(tasks, 0);
	}

	/**
	 * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
	 * 
	 * @param tasks   Runnable 任务
	 * @param timeout 如果可以最多等待的时间
	 * @param <T>     泛型
	 * @return 表示该任务的 Future
	 */
	public static <T> List<T> submit(List<Callable<T>> tasks, long timeout) {
		// 获得列表长度
		int len = tasks.size();
//		try {
//			pool().invokeAll(tasks);
//		} catch (InterruptedException e1) { 
//			e1.printStackTrace();
//		}
		// 声明结果列表
		List<Future<T>> list = Lists.newList(len);
		// 声明返回列表
		List<T> ls = Lists.newList(len);
		// 执行任务
		tasks.forEach(task -> list.add(pool().submit(task)));
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

	/**
	 * 指定新数量线程池驻留工作区 循环执行command
	 * 
	 * @param command 工作线程
	 */
	public static void works(CallbackVoid<Long> call) {
		works(C.O.CPU_NUM, new AtomicLong(), call);
	}

	/**
	 * 指定新数量线程池驻留工作区 循环执行command
	 * 
	 * @param pool    线程池
	 * @param command 工作线程
	 */
	public static void works(int pool, CallbackVoid<Long> call) {
		works(pool, new AtomicLong(), call);
	}

	/**
	 * 指定新数量线程池驻留工作区 循环执行command
	 * 
	 * @param pool     线程池
	 * @param call     工作线程
	 * @param sequence 工作序列
	 */
	public static void works(int pool, AtomicLong sequence, CallbackVoid<Long> call) {
		ExecutorService es = newPool(pool, true);
		for (int i = 0; i < pool; i++)
			es.execute(() -> {
				while (true) {
					U.D.dura();
					call.callback(sequence.getAndAdd(1));
					Logs.debug("works pool={} sequence={} time={}", pool, sequence, U.D.dura());
				}
			});
	}
}