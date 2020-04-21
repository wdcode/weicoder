package com.weicoder.common.lang;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.weicoder.common.util.EmptyUtil;

/**
 * Set的帮助类,获得Set的一些操作
 * @author WD
 */
public final class Sets {
	/**
	 * 判断是否Set
	 * @param obj 对象
	 * @return 是否Set
	 */
	public static boolean isSet(Object obj) {
		return EmptyUtil.isNotEmpty(obj) && obj instanceof Set<?>;
	}

	/**
	 * 获得Set实例 实现类是HashSet 默认初始化大小为10
	 * @param <E> 泛型
	 * @return Set
	 */
	public static <E> HashSet<E> newSet() {
		return new HashSet<>();
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param size 初始化大小
	 * @param <E> 泛型
	 * @return Set
	 */
	public static <E> HashSet<E> newSet(int size) {
		return new HashSet<>(size);
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param es 初始化的集合
	 * @param <E> 泛型
	 * @return Set
	 */
	@SafeVarargs
	public static <E> HashSet<E> newSet(E... es) {
		return newSet(Lists.newList(es));
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param c 初始化的集合
	 * @param <E> 泛型
	 * @return Set
	 */
	public static <E> HashSet<E> newSet(Collection<E> c) {
		return new HashSet<>(c);
	}

	/**
	 * 获得一个不可变的空Set
	 * @param <E> 泛型
	 * @return 一个不可变的空Set
	 */
	public static <E> Set<E> emptySet() {
		return Collections.emptySet();
	}

	/**
	 * 获得列表数量
	 * @param set 数据列表
	 * @return 数量
	 */
	public static int size(Set<?> set) {
		return EmptyUtil.isEmpty(set) ? 0 : set.size();
	}

	private Sets() {}
}
