package com.weicoder.core.factory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.weicoder.common.util.EmptyUtil;

/**
 * 抽象工厂抽象实现
 * @author WD
 * @since JDK7
 * @version 1.0 2010-06-04
 */
public abstract class Factory<E> {
	// 对象锁
	protected Lock	lock	= new ReentrantLock();
	// 产品
	protected E		e;

	/**
	 * 获得实例 单例模式
	 * @return 获得实例
	 */
	public E getInstance() {
		// 判断是否为空
		if (EmptyUtil.isEmpty(e)) {
			// 同步琐
			lock.lock();
			// 判断是否为空
			if (EmptyUtil.isEmpty(e)) {
				// 生成新的实例
				e = newInstance();
			}
			// 解锁
			lock.unlock();
		}
		// 返回
		return e;
	}

	/**
	 * 实例化新实例
	 * @return 新实例
	 */
	public abstract E newInstance();
}
