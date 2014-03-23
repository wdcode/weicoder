package com.weicoder.base.cache.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.weicoder.base.cache.base.BaseCache;
import com.weicoder.base.entity.Entity;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.json.JsonEngine;
import com.weicoder.core.nosql.NoSQL;
import com.weicoder.core.nosql.factory.NoSQLFactory;

/**
 * 基于memcached的缓存
 * @author WD
 * @since JDK6
 * @version 1.0 2013-09-06
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class CacheNoSQL<E extends Entity> extends BaseCache<E> {
	// memcached
	private final static NoSQL	NOSQL;
	static {
		NOSQL = NoSQLFactory.getNoSQL("cache");
	}

	@Override
	public E set(E entity) {
		// 获得键
		String key = getKey(entity.getKey());
		// 判断键是否存在 不存在更新键 并且添加到memcached成功
		if (!NOSQL.exists(key)) {
			// 获得保存键值
			String k = getKey();
			// 追加到memcace
			if (!NOSQL.append(k, StringConstants.COMMA + key)) {
				// 没追加到 第一个直接set
				NOSQL.set(k, key);
			}
			// 加数量
			NOSQL.set(getKeySize(), size() + 1);
		}
		// 更新缓存
		NOSQL.set(key, entity.toString());
		// 返回实体
		return entity;
	}

	@Override
	public E get(Serializable key) {
		return (E) JsonEngine.toBean(Conversion.toString(NOSQL.get(getKey(key))), clazz);
	}

	@Override
	public List<E> get(List<Serializable> keys) {
		// 判断键为空
		if (EmptyUtil.isEmpty(keys)) {
			return Lists.emptyList();
		}
		// 获得所有对象
		Object[] objs = NOSQL.get(getKey(keys));
		// 判断值为空
		if (EmptyUtil.isEmpty(objs)) {
			return Lists.emptyList();
		}
		// 声明返回列表
		List<E> list = Lists.getList(objs.length);
		// 循环转换对象
		for (Object o : objs) {
			list.add((E) JsonEngine.toBean(Conversion.toString(o), clazz));
		}
		// 返回列表
		return list;
	}

	@Override
	public E remove(Serializable key) {
		// 获得实体
		E e = get(key);
		// 删除键
		NOSQL.remove(getKey(key));
		// 减数量
		NOSQL.set(getKeySize(), size() - 1);
		// 减key
		List<String> keys = Lists.getList(getKeys());
		// 删除key
		keys.remove(key);
		// 重新写入key
		NOSQL.set(getKey(), Lists.toString(keys));
		// 返回实体
		return e;
	}

	@Override
	public List<E> remove(Serializable... keys) {
		// 声明删除列表
		List<E> list = Lists.getList(keys.length);
		// 循环删除
		for (Serializable key : keys) {
			list.add(remove(key));
		}
		// 返回列表
		return list;
	}

	@Override
	public List<E> list() {
		// 获得全部key
		String[] keys = getKeys();
		// 获得键
		List<Serializable> list = Lists.getList(keys.length);
		// 循环键
		for (String key : keys) {
			list.add(key);
		}
		// 返回列表
		return get(list);
	}

	@Override
	public int size() {
		return Conversion.toInt(NOSQL.get(getKeySize()));
	}

	@Override
	public void clear() {
		// 循环删除key
		for (String key : getKeys()) {
			NOSQL.remove(key);
		}
		// 删除数量
		NOSQL.remove(getKeySize());
		// 删除key集合
		NOSQL.remove(getKey());
	}

	@Override
	public boolean isEmpty() {
		return NOSQL.get(getKey()) == null;
	}

	/**
	 * 根据键列表获得memcached使用键列表
	 * @param key 键
	 * @return 键
	 */
	private String[] getKey(List<Serializable> key) {
		// 声明键数组
		String[] keys = new String[key.size()];
		// 组成键数组
		for (int i = 0; i < key.size(); i++) {
			keys[i] = getKey(key.get(i));
		}
		// 返回键
		return keys;
	}

	/**
	 * 获得memcached保存主键key
	 * @return memcached使用键
	 */
	private String getKey() {
		return getKey("key");
	}

	/**
	 * 获得memcached保存笨类型的数量
	 * @return memcached使用键
	 */
	private String getKeySize() {
		return getKey("size");
	}

	/**
	 * 根据单个key获得memcached中保存的键
	 * @param key 原实体键
	 * @return memcached使用键
	 */
	private String getKey(Serializable key) {
		return Conversion.toString(key).startsWith(name) ? Conversion.toString(key) : name + StringConstants.UNDERLINE + key;
	}

	/**
	 * 获得本类型全部Keys
	 * @return keys
	 */
	private String[] getKeys() {
		return StringUtil.split(Conversion.toString(NOSQL.get(getKey())), StringConstants.COMMA);
	}
}
