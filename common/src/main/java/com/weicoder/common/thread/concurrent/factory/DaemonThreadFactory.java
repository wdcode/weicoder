package com.weicoder.common.thread.concurrent.factory;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 守护线程工厂
 * 
 * @author WD
 */
public sealed class DaemonThreadFactory implements ThreadFactory permits DTF {
	/** 单例 */
	public final static DaemonThreadFactory	INSTANCE	= new DaemonThreadFactory();
	// 默认线程工厂
	private ThreadFactory					factory		= Executors.defaultThreadFactory();

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = factory.newThread(r);
		thread.setDaemon(true);
		return thread;
	}
}
