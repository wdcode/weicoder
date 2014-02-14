package com.weicoder.common.lang;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import com.weicoder.common.util.EmptyUtil;

/**
 * Set的帮助类,获得Set的一些操作
 * @author WD
 * @since JDK7
 * @version 1.0 2009-09-08
 */
public final class Sets {
	/**
	 * 判断是否Set
	 * @param obj 对象
	 * @return 是否Set
	 */
	public static boolean isSet(Object obj) {
		return !EmptyUtil.isEmpty(obj) && obj instanceof Set<?>;
	}

	/**
	 * 获得Set实例 实现类是HashSet 默认初始化大小为10
	 * @return Set
	 */
	public static <E> Set<E> getSet() {
		return getLinkedHashSet();
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param size 初始化大小
	 * @return Set
	 */
	public static <E> Set<E> getSet(int size) {
		return getLinkedHashSet(size);
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param c 初始化的集合
	 * @return Set
	 */
	public static <E> Set<E> getSet(Collection<E> c) {
		return getLinkedHashSet(c);
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param es 初始化的数组
	 * @return Set
	 */
	public static <E> Set<E> getSet(E... es) {
		return getLinkedHashSet(es);
	}

	/**
	 * 获得同步Set实例 实现类是HashSet 默认初始化大小为10
	 * @return Set
	 */
	public static <E> Set<E> getSynchronizedSet() {
		return getSynchronizedSet();
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param size 初始化大小
	 * @return Set
	 */
	public static <E> Set<E> getSynchronizedSet(int size) {
		return Collections.synchronizedSet(new HashSet<E>(size));
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param c 初始化的集合
	 * @return Set
	 */
	public static <E> Set<E> getSynchronizedSet(Collection<E> c) {
		return Collections.synchronizedSet(getHashSet(c));
	}

	/**
	 * 获得并发的List实例 实现类是CopyOnWriteArrayList
	 * @return 同步的List
	 */
	public static <E> CopyOnWriteArrayList<E> getConcurrenrSet() {
		return new CopyOnWriteArrayList<E>();
	}

	/**
	 * 获得并发的Set实例 实现类是CopyOnWriteArraySet
	 * @param c 初始化的集合
	 * @return 同步的List
	 */
	public static <E> CopyOnWriteArraySet<E> getConcurrenrSet(Collection<E> c) {
		return new CopyOnWriteArraySet<E>(c);
	}

	/**
	 * 获得Set实例 实现类是HashSet 默认初始化大小为10
	 * @return Set
	 */
	public static <E> LinkedHashSet<E> getLinkedHashSet() {
		return getLinkedHashSet(16);
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param size 初始化大小
	 * @return Set
	 */
	public static <E> LinkedHashSet<E> getLinkedHashSet(int size) {
		return new LinkedHashSet<E>(size);
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param es 初始化的集合
	 * @return Set
	 */
	public static <E> LinkedHashSet<E> getLinkedHashSet(E... es) {
		return getLinkedHashSet(Lists.getList(es));
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param c 初始化的集合
	 * @return Set
	 */
	public static <E> LinkedHashSet<E> getLinkedHashSet(Collection<E> c) {
		return new LinkedHashSet<E>(c);
	}

	/**
	 * 获得Set实例 实现类是HashSet 默认初始化大小为10
	 * @return Set
	 */
	public static <E> HashSet<E> getHashSet() {
		return getHashSet(16);
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param size 初始化大小
	 * @return Set
	 */
	public static <E> HashSet<E> getHashSet(int size) {
		return new HashSet<E>(size);
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param es 初始化的集合
	 * @return Set
	 */
	public static <E> HashSet<E> getHashSet(E... es) {
		return getHashSet(Lists.getList(es));
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param c 初始化的集合
	 * @return Set
	 */
	public static <E> HashSet<E> getHashSet(Collection<E> c) {
		return new HashSet<E>(c);
	}

	/**
	 * 获得Set实例 实现类是HashSet 默认初始化大小为10
	 * @return Set
	 */
	public static <E> TreeSet<E> getTreeSet() {
		return new TreeSet<E>();
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param es 初始化的集合
	 * @return Set
	 */
	public static <E> TreeSet<E> getTreeSet(E... es) {
		return getTreeSet(Lists.getList(es));
	}

	/**
	 * 获得Set实例 实现类是HashSet
	 * @param c 初始化的集合
	 * @return Set
	 */
	public static <E> TreeSet<E> getTreeSet(Collection<E> c) {
		return new TreeSet<E>(c);
	}

	/**
	 * 获得一个不可变的空Set
	 * @return 一个不可变的空Set
	 */
	public static <E> Set<E> emptyList() {
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

	/**
	 * 私有构造
	 */
	private Sets() {}
}
