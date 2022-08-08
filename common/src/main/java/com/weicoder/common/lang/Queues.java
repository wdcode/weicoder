package com.weicoder.common.lang;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.weicoder.common.interfaces.CallbackList;
import com.weicoder.common.interfaces.CallbackVoid;
import com.weicoder.common.queue.AsynQueue;
import com.weicoder.common.queue.AsynQueueList;
import com.weicoder.common.util.MathUtil;
import com.weicoder.common.queue.OnlyQueue;

/**
 * 队列相关方法
 * 
 * @author WD
 */
public class Queues {
	/**
	 * 生成新的队列
	 * 
	 * @return 并发列表队列
	 */
	public static <E> ConcurrentLinkedQueue<E> newConcurrentQueue() {
		return new ConcurrentLinkedQueue<E>();
	}

	/**
	 * 生成新的堵塞队列
	 * 
	 * @return 列表堵塞队列
	 */
	public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue() {
		return new LinkedBlockingQueue<E>();
	}

	/**
	 * 生产唯一元素并发队列
	 * 
	 * @return 唯一元素并发队列
	 */
	public static <E> OnlyQueue<E> newOnlyQueue() {
		return new OnlyQueue<E>();
	}

	/**
	 * 声明异步更新队列 默认使用堵塞队列 @see LinkedBlockingQueue
	 * 
	 * @param <E>      泛型
	 * @param callback 异步获取队列数据回调处理
	 * @param time     间隔检测队列时间 毫秒
	 * @return 异步队列
	 */
	public static <E> AsynQueue<E> newAsynQueue(CallbackVoid<E> callback, long time) {
		return newAsynQueue(newLinkedBlockingQueue(), callback, time);
	}

	/**
	 * 声明异步更新队列 默认使用堵塞队列 @see LinkedBlockingQueue
	 * 
	 * @param <E>      泛型
	 * @param callback 异步获取队列数据回调处理
	 * @param time     间隔检测队列时间 秒
	 * @return 异步队列
	 */
	public static <E> AsynQueue<E> newAsynQueue(CallbackVoid<E> callback, int time) {
		return newAsynQueue(newLinkedBlockingQueue(), callback, MathUtil.multiply(time, 1000).longValue());
	}

	/**
	 * 声明异步更新队列
	 * 
	 * @param <E>      泛型
	 * @param queue    异步处理的队列 是否确保线程安全看传入的实现
	 * @param callback 异步获取队列数据回调处理
	 * @param time     间隔检测队列时间 毫秒
	 * @return 异步队列
	 */
	public static <E> AsynQueue<E> newAsynQueue(Queue<E> queue, CallbackVoid<E> callback, long time) {
		return new AsynQueue<E>(queue, callback, time);
	}

	/**
	 * 声明异步更新队列
	 * 
	 * @param <E>      泛型
	 * @param queue    异步处理的队列 是否确保线程安全看传入的实现
	 * @param callback 异步获取队列数据回调处理
	 * @param time     间隔检测队列时间 秒
	 * @return 异步队列
	 */
	public static <E> AsynQueue<E> newAsynQueue(Queue<E> queue, CallbackVoid<E> callback, int time) {
		return newAsynQueue(queue, callback, MathUtil.multiply(time, 1000).longValue());
	}

	/**
	 * 生产异步更新并发队列 默认ConcurrentLinkedQueue队列
	 * 
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueueList<E> newAsynQueueList(CallbackList<E> callback, long time) {
		return newAsynQueueList(newConcurrentQueue(), callback, time);
	}

	/**
	 * 生产异步更新并发队列
	 * 
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueueList<E> newAsynQueueList(Queue<E> queue, CallbackList<E> callback, long time) {
		return new AsynQueueList<E>(queue, callback, time);
	}

	/**
	 * 生产异步更新并发队列
	 * 
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueueList<E> newAsynQueueList(CallbackList<E> callback, int time) {
		return newAsynQueueList(newConcurrentQueue(), callback, time);
	}

	/**
	 * 生产异步更新并发队列
	 * 
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueueList<E> newAsynQueueList(Queue<E> queue, CallbackList<E> callback, int time) {
		return newAsynQueueList(queue, callback, MathUtil.multiply(time, 1000).longValue());
	}
}
