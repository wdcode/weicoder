package com.weicoder.common.thread;

/**
 * 线程操作类引用
 * 
 * @author wudi
 */
public final class T extends ThreadUtil {
	private T() {
	}

	/**
	 * @see ExecutorUtil 并发线程池类引用
	 * @author wudi
	 */
	public static final class E extends ExecutorUtil {
	}

	/**
	 * @see ScheduledUtil 并发线程定时类引用
	 * @author wudi
	 */
	public static final class S extends ScheduledUtil {
	}
}
