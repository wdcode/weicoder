package com.weicoder.base.cache;

import java.io.Serializable;
import java.util.List;

import com.weicoder.base.entity.Entity;
import com.weicoder.common.interfaces.Clear;
import com.weicoder.common.interfaces.Empty;

/**
 * 所有缓存的基础接口
 * @author WD
 * @since JDK7
 * @version 1.0 2012-07-13
 */
public interface Cache<E extends Entity> extends Clear, Empty {
	/**
	 * 添加缓存
	 * @param entity 缓存的Value
	 * @return 缓存的Value
	 */
	E set(E entity);

	/**
	 * 添加缓存
	 * @param entitys 缓存的Value
	 * @return 缓存的Value
	 */
	List<E> set(List<E> entitys);

	/**
	 * 获得缓存数据
	 * @param key 缓存Key
	 * @return 缓存Value
	 */
	E get(Serializable key);

	/**
	 * 获得缓存数据
	 * @param keys 缓存Key
	 * @return 缓存Value
	 */
	List<E> get(Serializable... keys);

	/**
	 * 获得缓存数据
	 * @param keys 缓存Key
	 * @return 缓存Value
	 */
	List<E> get(List<Serializable> keys);

	/**
	 * 添加缓存
	 * @param entity 缓存的Value
	 * @return 缓存的Value
	 */
	E remove(E entity);

	/**
	 * 添加缓存
	 * @param entitys 缓存的Value
	 * @return 缓存的Value
	 */
	List<E> remove(List<E> entitys);

	/**
	 * 删除缓存
	 * @param key 缓存Key
	 * @return 缓存的Value
	 */
	E remove(Serializable key);

	/**
	 * 删除缓存
	 * @param keys 缓存Key
	 * @return value列表
	 */
	List<E> remove(Serializable... keys);

	/**
	 * 获得所有缓存Value
	 * @return
	 */
	List<E> list();

	/**
	 * 获得缓存大小
	 * @return 缓存大小
	 */
	int size();

	/**
	 * 获得缓存是否有效
	 * @return 是否有效
	 */
	boolean isValid();

	/**
	 * 设置Class
	 * @param clazz Class
	 */
	void setClass(Class<?> clazz);
}
