package com.weicoder.common.lang;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Set的帮助类,获得Set的一些操作
 * 
 * @author WD
 */
public sealed class Sets permits W.S {
	/**
	 * 判断是否Set
	 * 
	 * @param obj 对象
	 * @return 是否Set
	 */
	public static boolean is(Object obj) {
		return obj != null && obj instanceof Set<?>;
	}

	/**
	 * 获得Set实例 实现类是HashSet 默认初始化大小为10
	 * 
	 * @param <E> 泛型
	 * @return Set
	 */
	public static <E> HashSet<E> set() {
		return hash();
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * 
	 * @param size 初始化大小
	 * @param <E>  泛型
	 * @return Set
	 */
	public static <E> HashSet<E> set(int size) {
		return hash(size);
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * 
	 * @param <E>
	 * @param es  初始化数组
	 * @return Set
	 */
	public static <E> Set<E> set(E[] es) {
		return set(W.L.list(es));
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * 
	 * @param c   初始化的集合
	 * @param <E> 泛型
	 * @return Set
	 */
	public static <E> Set<E> set(Collection<E> c) {
		return hash(c);
	}

	/**
	 * 获得TreeSet实例
	 * 
	 * @param <E> 泛型
	 * @return TreeSet
	 */
	public static <E> TreeSet<E> tree() {
		return new TreeSet<>();
	}

	/**
	 * 获得HashSet实例
	 * 
	 * @param <E>  泛型
	 * @param size 容量
	 * @return HashSet
	 */
	public static <E> HashSet<E> hash() {
		return new HashSet<>();
	}

	/**
	 * 获得HashSet实例
	 * 
	 * @param <E>  泛型
	 * @param size 容量
	 * @return HashSet
	 */
	public static <E> HashSet<E> hash(int size) {
		return new HashSet<>(size);
	}

	/**
	 * 获得HashSet实例
	 * 
	 * @param <E> 泛型
	 * @param c   集合
	 * @return HashSet
	 */
	public static <E> HashSet<E> hash(Collection<E> c) {
		return c == null ? new HashSet<>() : new HashSet<>(c);
	}

	/**
	 * 获得一个不可变的空Set
	 * 
	 * @param <E> 泛型
	 * @return 一个不可变的空Set
	 */
	public static <E> Set<E> empty() {
		return Collections.emptySet();
	}

	/**
	 * 获得列表数量
	 * 
	 * @param set 数据列表
	 * @return 数量
	 */
	public static int size(Set<?> set) {
		return set == null ? 0 : set.size();
	}
}
