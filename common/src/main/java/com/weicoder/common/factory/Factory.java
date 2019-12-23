package com.weicoder.common.factory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 抽象工厂抽象实现
 * 
 * @author WD
 */
public abstract class Factory<E> {
	// 对象锁
	protected Lock lock = new ReentrantLock(true);
	// 产品
	protected E e;

	/**
	 * 获得实例 单例模式
	 * 
	 * @return 获得实例
	 */
	public E getInstance() {
		// 判断是否为空
		if (e == null) {
			// 同步琐
			lock.lock();
			// 判断是否为空
			if (e == null)
				// 生成新的实例
				try {
					e = newInstance();
				} catch (Exception e) {
				}
			// 解锁
			lock.unlock();
		}
		// 返回
		return e;
	}

	/**
	 * 实例化新实例
	 * 
	 * @return 新实例
	 */
	public abstract E newInstance();
}
