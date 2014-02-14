package com.weicoder.base.cache.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.weicoder.base.cache.base.BaseCache;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.weicoder.base.entity.Entity;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.EmptyUtil;

/**
 * 标准的缓存Map实现
 * @author WD
 * @since JDK7
 * @version 1.0 2012-07-13
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class CacheMap<E extends Entity> extends BaseCache<E> {
	// 保存缓存的Map
	private ConcurrentMap<Serializable, E>	mapCache;
	// 空缓存V的List
	private List<E>							emptyValueList;

	/**
	 * 构造函数
	 */
	public CacheMap() {
		this.mapCache = Maps.getConcurrentMap();
		this.emptyValueList = Lists.emptyList();
	}

	/**
	 * 获得所有缓存Value
	 * @return
	 */
	public List<E> list() {
		return isValid() ? Lists.sort(Lists.getList(mapCache.values())) : emptyValueList;
	}

	/**
	 * 获得缓存数据
	 * @param key 缓存Key
	 * @return 缓存Value
	 */
	public E get(Serializable key) {
		return isValid() ? mapCache.get(key) : null;
	}

	/**
	 * 获得缓存数据
	 * @param keys 缓存Key
	 * @return 缓存Value
	 */
	public List<E> get(Serializable... keys) {
		return get(Lists.getList(keys));
	}

	/**
	 * 获得缓存数据
	 * @param keys 缓存Key
	 * @return 缓存Value
	 */
	public List<E> get(List<Serializable> keys) {
		// 判断是否生效
		if (isValid() && !EmptyUtil.isEmpty(keys)) {
			// 获得列表数量
			int size = keys.size();
			// 声明列表
			List<E> list = Lists.getList(size);
			// 循环获得值
			for (int i = 0; i < size; i++) {
				// 添加到列表
				list.add(mapCache.get(keys.get(i)));
			}
			// 返回列表
			return list;
		}
		// 返回
		return emptyValueList;
	}

	/**
	 * 添加缓存
	 * @param key 缓存的Key
	 * @param value 缓存的Value
	 */
	public E set(Serializable key, E value) {
		// 判断是否生效
		if (isValid()) {
			// 如果Key或则value为空 直接返回value
			if (EmptyUtil.isEmpty(key) || EmptyUtil.isEmpty(value)) {
				return value;
			}
			// 设置缓存
			value.toString();
			mapCache.put(key, value);
		}
		// 添加并返回实体
		return value;
	}

	/**
	 * 添加缓存r
	 * @param cachees 缓存
	 * @return 添加的缓存
	 */
	public List<E> set(Map<Serializable, E> caches) {
		// 判断是否生效
		if (isValid()) {
			// 如果添加的缓存为空直接返回
			if (EmptyUtil.isEmpty(caches)) {
				return emptyValueList;
			}
			// 把数据添加到缓存Map中
			mapCache.putAll(caches);
		}
		// 返回缓存
		return Lists.getList(caches.values());
	}

	/**
	 * 删除缓存
	 * @param key 缓存Key
	 */
	public E remove(Serializable key) {
		// 声明实体
		E v = null;
		// 判断是否游戏
		if (isValid() && !EmptyUtil.isEmpty(key)) {
			// 删除缓存并添加到值列表中
			v = mapCache.remove(key);
		}
		// 返回实体
		return v;
	}

	/**
	 * 删除缓存
	 * @param keys 缓存Key
	 */
	public List<E> remove(Serializable... keys) {
		// 判断是否有效
		if (isValid() && !EmptyUtil.isEmpty(keys)) {
			// 获得列表数量
			int size = keys.length;
			// 获得值列表
			List<E> lsValue = Lists.getList(size);
			// 循环Key数组
			for (int i = 0; i < size; i++) {
				// 删除缓存并添加到值列表中
				lsValue.add(remove(keys[i]));
			}
			// 返回值列表
			return lsValue;
		}
		// 返回
		return emptyValueList;
	}

	/**
	 * 获得实际缓存的大小
	 * @return 实际缓存的大小
	 */
	public int size() {
		return isValid() ? mapCache.size() : 0;
	}

	/**
	 * 清除所有缓存
	 */
	public void clear() {
		// 判断缓存是否生效
		if (isValid()) {
			// 清除数据缓存
			mapCache.clear();
		}
	}

	/**
	 * 判断是否为空
	 */
	public boolean isEmpty() {
		return EmptyUtil.isEmpty(mapCache);
	}

	@Override
	public String toString() {
		return mapCache.toString();
	}

	/**
	 * 添加缓存
	 * @param key 缓存的Key
	 * @param value 缓存的Value
	 * @return 缓存的Value
	 */
	public E set(E value) {
		return set(value.getKey(), value);
	}
}
