package com.weicoder.common.thread.concurrent.factory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.params.P;

/**
 * 定时工厂
 * @author WD
 */
public class ScheduledFactory extends FactoryKey<String, ScheduledExecutorService> {
	@Override
	public ScheduledExecutorService newInstance(String key) {
		return newPool(P.C.getScheduledPool(key), P.C.getScheduledDaemon(key));
	}

	/**
	 * 获得新的线程任务池
	 * @param size 池数量
	 * @param daemon 是否守护线程
	 * @return 线程任务池
	 */
	public ScheduledExecutorService newPool(int size, boolean daemon) {
		return daemon ? Executors.newScheduledThreadPool(size, DaemonThreadFactory.INSTANCE)
				: Executors.newScheduledThreadPool(size);
	}
}
