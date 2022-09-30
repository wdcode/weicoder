package com.weicoder.common.thread.concurrent.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.params.P;

/**
 * 线程池工厂
 * @author WD
 */
public class ExecutorFactory extends FactoryKey<String, ExecutorService> {

	@Override
	public ExecutorService newInstance(String key) {
		return newPool(P.C.getExecutorPool(key), P.C.getExecutorDaemon(key));
	}

	/**
	 * 获得新的缓存线程池
	 * @param pool 线程池数量
	 * @param daemon 是否守护线程
	 * @return 缓存线程池
	 */
	public ExecutorService newPool(int pool, boolean daemon) {
		return daemon ? Executors.newFixedThreadPool(pool, DaemonThreadFactory.INSTANCE)
				: Executors.newFixedThreadPool(pool);
	}
}
