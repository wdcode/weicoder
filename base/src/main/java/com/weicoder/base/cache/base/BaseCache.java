package com.weicoder.base.cache.base;

import java.io.Serializable;
import java.util.List;

import com.weicoder.base.cache.Cache;
import com.weicoder.base.entity.Entity;
import com.weicoder.base.params.BaseParams;
import com.weicoder.common.lang.Lists;

/**
 * 基础缓存实体
 * @author WD
 * @since JDK7
 * @version 1.0 2012-07-18
 */
public abstract class BaseCache<E extends Entity> implements Cache<E> {
	// Class
	protected Class<?>	clazz;
	// name
	protected String	name;

	/**
	 * 获得缓存数据
	 * @param keys 缓存Key
	 * @return 缓存Value
	 */
	public List<E> get(Serializable... keys) {
		return get(Lists.getList(keys));
	}

	/**
	 * 添加缓存
	 * @param key 缓存的Key
	 * @param values 缓存的Value
	 * @return 缓存的Value
	 */
	public List<E> set(List<E> values) {
		// 循环添加
		for (E entity : values) {
			set(entity);
		}
		// 返回列表
		return values;
	}

	/**
	 * 添加缓存
	 * @param entity 缓存的Value
	 * @return 缓存的Value
	 */
	public E remove(E entity) {
		return remove(entity.getKey());
	}

	/**
	 * 添加缓存
	 * @param entitys 缓存的Value
	 * @return 缓存的Value
	 */
	public List<E> remove(List<E> entitys) {
		// 获得数量
		int size = entitys.size();
		// 键数组
		Serializable[] keys = new Serializable[size];
		// 循环删除
		for (int i = 0; i < size; i++) {
			keys[i] = entitys.get(i).getKey();
		}
		// 返回列表
		return remove(keys);
	}

	/**
	 * 获得缓存是否有效
	 * @return 是否有效
	 */
	public boolean isValid() {
		return BaseParams.getCache(name);
	}

	/**
	 * 设置Class
	 * @param clazz Class
	 */
	public void setClass(Class<?> clazz) {
		this.clazz = clazz;
		this.name = clazz.getSimpleName();// StringUtil.convert(clazz.getSimpleName());
	}
}
