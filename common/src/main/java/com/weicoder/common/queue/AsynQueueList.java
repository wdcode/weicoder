package com.weicoder.common.queue;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

import com.weicoder.common.U;
import com.weicoder.common.interfaces.CallbackList;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;

/**
 * 异步回调处理队列数据
 * 
 * @author WD
 */
public class AsynQueueList<E> {
	// 日志
	private final static Log	LOG	= LogFactory.getLog(AsynQueueList.class);
	// 队列
	private Queue<E>			queue;

	/**
	 * 构造
	 * 
	 * @param queue    队列
	 * @param callback 回调
	 * @param time     时间 毫秒
	 */
	public AsynQueueList(Queue<E> queue, CallbackList<E> callback, long time) {
		this.queue = queue;
		// 定时任务
		U.SES.delay(() -> {
			// 队列为空 直接返回
			if (queue.isEmpty())
				return;
			// 运行次数
			int n = 0;
			// 开始时间
			long c = U.D.now();
			List<E> result = Lists.newList();
			// 队列不为空
			while (U.E.isNotEmpty(queue) && n < 10000) {
				LOG.trace("AsynQueueList add i={} add={}", ++n, result.add(queue.poll()));
			}
			// 执行
			if (n > 0) {
				callback.callback(result);
				LOG.debug("AsynQueueList run size={} time={}", n, U.D.now() - c);
			}
		}, time);
	}

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
}
