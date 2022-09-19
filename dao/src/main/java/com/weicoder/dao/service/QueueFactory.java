package com.weicoder.dao.service;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.lang.Queues;
import com.weicoder.common.params.DaoParams;
import com.weicoder.common.queue.AsynQueueList;

/**
 * SuperService 使用更新队列
 * @author WD
 */
final class QueueFactory extends FactoryKey<Class<?>, AsynQueueList<Object>> {
	// 工厂
	private final static QueueFactory FACTORY = new QueueFactory();

	/**
	 * 根据类名获得更新队列
	 * @param key 类名
	 * @return 队列
	 */
	public static AsynQueueList<Object> get(Class<?> key) {
		return FACTORY.getInstance(key);
	}

	@Override
	public AsynQueueList<Object> newInstance(Class<?> key) {
		return Queues.newAsynQueueList(Queues.newOnlyQueue(), results -> SuperService.DAO.insertOrUpdate(results, DaoParams.QUEUE_SETP), DaoParams.QUEUE_TIME);
	}

	private QueueFactory() {}
}
