package com.weicoder.common.factory;

import java.util.Map;

import com.weicoder.common.lang.Maps;

/**
 * 拥有Key功能的工厂基础实现 根据Key生成单例
 * @author WD
 */
public abstract class FactoryKey<K, E> extends Factory<E> {
	// 产品仓库
	protected Map<K, E> map = Maps.newMap();

	/**
	 * 获得实例 单例模式
	 * @param key 根据Key获得实例
	 * @return 获得实例
	 */
	public final E getInstance(K key) {
		// 获得产品
		E e = map.get(key);
		// 判断是否为空
		if (e == null) {
			// 同步琐
			lock.lock();
			// 判断是否为空
			if (e == null)
				// 生成新的实例 添加到仓库中
				map.put(key, e = newInstance(key));
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
	public E newInstance() {
		return newInstance(null);
	}

	/**
	 * 实例化新实例
	 * @param key 根据Key获得实例
	 * @return 新实例
	 */
	public abstract E newInstance(K key);
}
