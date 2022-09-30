package com.weicoder.common.lang;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.weicoder.common.interfaces.Calls; 
import com.weicoder.common.queue.AsynQueue;
import com.weicoder.common.queue.AsynQueueList; 
import com.weicoder.common.util.U;
import com.weicoder.common.queue.OnlyQueue;

/**
 * 队列相关方法
 * 
 * @author WD
 */
public sealed class Queues permits W.Q {
	/**
	 * 生成新的队列
	 * 
	 * @return 并发列表队列
	 */
	public static <E> ConcurrentLinkedQueue<E> concurrent() {
		return new ConcurrentLinkedQueue<E>();
	}

	/**
	 * 生成新的堵塞队列
	 * 
	 * @return 列表堵塞队列
	 */
	public static <E> LinkedBlockingQueue<E> linked() {
		return new LinkedBlockingQueue<E>();
	}

	/**
	 * 生产唯一元素并发队列
	 * 
	 * @return 唯一元素并发队列
	 */
	public static <E> OnlyQueue<E> only() {
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
	public static <E> AsynQueue<E> asyn(Calls.EoV<E> callback, long time) {
		return asyn(linked(), callback, time);
	}

	/**
	 * 声明异步更新队列 默认使用堵塞队列 @see LinkedBlockingQueue
	 * 
	 * @param <E>      泛型
	 * @param callback 异步获取队列数据回调处理
	 * @param time     间隔检测队列时间 秒
	 * @return 异步队列
	 */
	public static <E> AsynQueue<E> asyn(Calls.EoV<E> callback, int time) {
		return asyn(linked(), callback, U.M.multiply(time, 1000).longValue());
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
	public static <E> AsynQueue<E> asyn(Queue<E> queue, Calls.EoV<E> callback, long time) {
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
	public static <E> AsynQueue<E> asyn(Queue<E> queue, Calls.EoV<E> callback, int time) {
		return asyn(queue, callback, U.M.multiply(time, 1000).longValue());
	}

	/**
	 * 生产异步更新并发队列 默认ConcurrentLinkedQueue队列
	 * 
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueueList<E> asynList(Calls.LoV<E> callback, long time) {
		return asynList(concurrent(), callback, time);
	}

	/**
	 * 生产异步更新并发队列
	 * 
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueueList<E> asynList(Queue<E> queue, Calls.LoV<E> callback, long time) {
		return new AsynQueueList<E>(queue, callback, time);
	}

	/**
	 * 生产异步更新并发队列
	 * 
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueueList<E> asynList(Calls.LoV<E> callback, int time) {
		return asynList(concurrent(), callback, time);
	}

	/**
	 * 生产异步更新并发队列
	 * 
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueueList<E> asynList(Queue<E> queue, Calls.LoV<E> callback, int time) {
		return asynList(queue, callback, U.M.multiply(time, 1000).longValue());
	}
}
