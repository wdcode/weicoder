package com.weicoder.common.util;

import java.util.Collection;
import java.util.Queue;

import com.weicoder.common.concurrent.ScheduledUtil;
import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;

/**
 * 异步回调处理队列数据
 * @author WD
 */
public class AsynQueue<E> {
	// 日志
	private final static Log	LOG	= LogFactory.getLog(AsynQueue.class);
	// 队列
	private Queue<E>			queue;

	/**
	 * 构造
	 * @param queue 队列
	 * @param callback 回调
	 * @param time 时间 毫秒
	 */
	public AsynQueue(Queue<E> queue, Callback<E> callback, long time) {
		this.queue = queue;
		// 定时任务
		ScheduledUtil.delay(() -> {
			int n = 0;
			long c = System.currentTimeMillis();
			// 队列不为空
			while (!queue.isEmpty()) {
				E e = queue.poll();
				callback.callback(e);
				LOG.debug("AsynQueue run obj={}", e);
			}
			LOG.info("AsynQueue run size={} time={}", n, System.currentTimeMillis() - c);
		}, time);
	}

	/**
	 * 构造
	 * @param queue 队列
	 * @param callback 回调
	 * @param time 时间 秒
	 */
	public AsynQueue(Queue<E> queue, Callback<E> callback, int time) {
		this(queue, callback, MathUtil.multiply(time, 1000).longValue());
	}

	/**
	 * 添加元素到队列
	 * @param e
	 * @return 是否成功
	 */
	public boolean add(E e) {
		return queue.add(e);
	}

	/**
	 * 添加列表到队列
	 * @param c 列表
	 * @return 是否成功
	 */
	public boolean addAll(Collection<? extends E> c) {
		return queue.addAll(c);
	}
}
