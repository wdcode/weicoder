package com.weicoder.common.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 获得锁相关对象
 * @author wudi
 */
public final class Locks {
	/**
	 * 获得对象锁
	 * @param fair 是否公平锁
	 * @return 锁
	 */
	public static Lock newLock(boolean fair) {
		return new ReentrantLock(fair);
	}

	/**
	 * 获得读写锁
	 * @param fair 是否公平锁
	 * @return 读写锁
	 */
	public static ReadWriteLock newReadWriteLock(boolean fair) {
		return new ReentrantReadWriteLock(fair);
	}

	/**
	 * 获得读锁
	 * @param fair 是否公平锁
	 * @return 读锁
	 */
	public static Lock newReadLock(boolean fair) {
		return newReadWriteLock(fair).readLock();
	}

	/**
	 * 获得写锁
	 * @param fair 是否公平锁
	 * @return 写锁
	 */
	public static Lock newWriteLock(boolean fair) {
		return newReadWriteLock(fair).writeLock();
	}

	private Locks() {
	}
}
