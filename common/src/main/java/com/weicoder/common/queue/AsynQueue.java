package com.weicoder.common.queue;

import java.util.Collection;
import java.util.Queue;

import com.weicoder.common.U;
import com.weicoder.common.interfaces.CallbackVoid;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;

/**
 * 异步回调处理队列数据
 * 
 * @author WD
 */
public class AsynQueue<E> {
	// 日志
	private final static Log	LOG	= LogFactory.getLog(AsynQueue.class);
	// 队列
	private Queue<E>			queue;

	/**
	 * 构造
	 * 
	 * @param queue    队列
	 * @param callback 回调
	 * @param time     时间 毫秒
	 */
	public AsynQueue(Queue<E> queue, CallbackVoid<E> callback, long time) {
		this.queue = queue;
		// 定时任务
		U.SES.delay(() -> {
			// 队列为空 直接返回
			if (queue.isEmpty())
				return;
			// 运行次数
			int n = 0;
			// 开始时间
//			long c = U.D.now();
			U.D.dura();
			// 队列不为空
			while (U.E.isNotEmpty(queue) && n < 10000) {
//				E e = queue.poll();
//				callback.callback(e);
				callback.callback(queue.poll());
				n++;
//				LOG.trace("AsynQueue run i={} obj={}", n, e);
			}
			if (n > 0)
				LOG.debug("AsynQueue run n={} time={}", n, U.D.dura());
		}, time);
	}

//	/**
//	 * 构造
//	 * 
//	 * @param queue    队列
//	 * @param callback 回调
//	 * @param time     时间 秒
//	 */
//	public AsynQueue(Queue<E> queue, CallbackVoid<E> callback, int time) {
//		this(queue, callback, MathUtil.multiply(time, 1000).longValue());
//	}

	/**
	 * 添加元素到队列
	 * 
	 * @param e
	 * @return 是否成功
	 */
	public boolean add(E e) {
		return queue.add(e);
	}

	/**
	 * 添加列表到队列
	 * 
	 * @param c 列表
	 * @return 是否成功
	 */
	public boolean addAll(Collection<? extends E> c) {
		return queue.addAll(c);
	}

	/**
	 * 获得内部实现队列
	 * 
	 * @return
	 */
	public Queue<E> queue() {
		return queue;
	}
}
