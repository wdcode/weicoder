package com.weicoder.common.lang;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.interfaces.CallbackList;
import com.weicoder.common.util.AsynQueue;
import com.weicoder.common.util.AsynQueueList;
import com.weicoder.common.util.MathUtil;
import com.weicoder.common.util.OnlyQueue;

/**
 * 队列相关方法
 * @author WD
 */
public class Queues {
	/**
	 * 生成新的队列
	 * @return 并发列表队列
	 */
	public static <E> ConcurrentLinkedQueue<E> newConcurrentQueue() {
		return new ConcurrentLinkedQueue<E>();
	}

	/**
	 * 生成新的堵塞队列
	 * @return 列表堵塞队列
	 */
	public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue() {
		return new LinkedBlockingQueue<E>();
	}

	/**
	 * 生产唯一元素并发队列
	 * @return 唯一元素并发队列
	 */
	public static <E> OnlyQueue<E> newOnlyQueue() {
		return new OnlyQueue<E>();
	}

	/**
	 * 生产异步更新并发队列
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueue<E> newAsynQueue(Queue<E> queue, Callback<E> callback, long time) {
		return new AsynQueue<E>(queue, callback, time);
	}

	/**
	 * 生产异步更新并发队列
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueue<E> newAsynQueue(Queue<E> queue, Callback<E> callback, int time) {
		return newAsynQueue(queue, callback, MathUtil.multiply(time, 1000).longValue());
	}
	
	/**
	 * 生产异步更新并发队列
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueueList<E> newAsynQueueList(Queue<E> queue, CallbackList<E> callback, long time) {
		return new AsynQueueList<E>(queue, callback, time);
	}

	/**
	 * 生产异步更新并发队列
	 * @return 异步更新并发队列
	 */
	public static <E> AsynQueueList<E> newAsynQueueList(Queue<E> queue, CallbackList<E> callback, int time) {
		return newAsynQueueList(queue, callback, MathUtil.multiply(time, 1000).longValue());
	}

	private Queues() {}
}
