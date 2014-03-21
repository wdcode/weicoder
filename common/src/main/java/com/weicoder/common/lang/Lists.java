package com.weicoder.common.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.weicoder.common.util.ArrayUtil;
import com.weicoder.common.util.EmptyUtil;

/**
 * List的帮助类
 * @author WD
 * @since JDK7
 * @version 1.0 2009-09-08
 */
public final class Lists {
	/**
	 * 判断是否列表
	 * @param obj 对象
	 * @return 是否列表
	 */
	public static boolean isList(Object obj) {
		return !EmptyUtil.isEmpty(obj) && obj instanceof List<?>;
	}

	/**
	 * 获得List实例 默认初始化大小为10
	 * @return List
	 */
	public static <E> List<E> getList() {
		return new ArrayList<E>();
	}

	/**
	 * 获得List实例
	 * @param size 初始化大小
	 * @return List
	 */
	public static <E> List<E> getList(int size) {
		return new ArrayList<E>(size < 1 ? 1 : size);
	}

	/**
	 * 获得List实例
	 * @param es 初始化的数组
	 * @return List
	 */
	public static <E> List<E> getList(E... e) {
		return getList(ArrayUtil.toList(e));
	}

	/**
	 * 获得List实例
	 * @param c 初始化的集合
	 * @return List
	 */
	public static <E> List<E> getList(Collection<E> c) {
		return c == null ? new ArrayList<E>() : new ArrayList<E>(c);
	}

	/**
	 * 获得List实例
	 * @param c 初始化的集合
	 * @return List
	 */
	public static <E> List<E> getList(Collection<E>... c) {
		// 获得一个列表
		List<E> list = getList();
		// 循环集合
		for (int i = 0; i < c.length; i++) {
			// 添加到列表中
			if (!EmptyUtil.isEmpty(c[i])) {
				list.addAll(c[i]);
			}
		}
		// 返回列表
		return list;
	}

	/**
	 * 获得List实例 默认初始化大小为10
	 * @return List
	 */
	public static <E> ArrayList<E> getArrayList() {
		return new ArrayList<E>();
	}

	/**
	 * 获得List实例
	 * @param size 初始化大小
	 * @return List
	 */
	public static <E> ArrayList<E> getArrayList(int size) {
		return new ArrayList<E>(size < 1 ? 1 : size);
	}

	/**
	 * 获得List实例
	 * @param es 初始化的数组
	 * @return List
	 */
	public static <E> ArrayList<E> getArrayList(E... e) {
		return getArrayList(ArrayUtil.toList(e));
	}

	/**
	 * 获得List实例
	 * @param c 初始化的集合
	 * @return List
	 */
	public static <E> ArrayList<E> getArrayList(Collection<E> c) {
		return c == null ? new ArrayList<E>() : new ArrayList<E>(c);
	}

	/**
	 * 获得并发的List实例 实现类是CopyOnWriteArrayList
	 * @return 同步的List
	 */
	public static <E> CopyOnWriteArrayList<E> getConcurrenrList() {
		return new CopyOnWriteArrayList<E>();
	}

	/**
	 * 获得并发的List实例 实现类是CopyOnWriteArrayList
	 * @param e 初始化数组
	 * @return 同步的List
	 */
	public static <E> CopyOnWriteArrayList<E> getConcurrenrList(E... e) {
		return new CopyOnWriteArrayList<E>(e);
	}

	/**
	 * 获得并发的List实例 实现类是CopyOnWriteArrayList
	 * @param c 初始化的集合
	 * @return 同步的List
	 */
	public static <E> CopyOnWriteArrayList<E> getConcurrenrList(Collection<E> c) {
		return new CopyOnWriteArrayList<E>(c);
	}

	/**
	 * 获得同步的List实例 实现类是LinkedList
	 * @return 同步的List
	 */
	public static <E> LinkedList<E> getLinkedList() {
		return new LinkedList<E>();
	}

	/**
	 * 获得同步的List实例 实现类是LinkedList
	 * @param c 初始化的集合
	 * @return 同步的List
	 */
	public static <E> LinkedList<E> getLinkedList(Collection<E> c) {
		return new LinkedList<E>(c);
	}

	/**
	 * 获得List实例 实现类是LinkedList
	 * @param es 初始化的数组
	 * @return List
	 */
	public static <E> List<E> getLinkedList(E... e) {
		return getLinkedList(ArrayUtil.toList(e));
	}

	/**
	 * 返回列表从begin开始返回end个元素
	 * @param list 元素列表
	 * @param begin 开始包含
	 * @param end 结束不包含
	 * @return 返回获得元素列表
	 */
	public static <E> List<E> subList(List<E> list, int begin, int end) {
		// 如果列表为空返回一个空列表
		if (EmptyUtil.isEmpty(list)) {
			return list;
		}
		// 获得元列表大小
		int size = list.size();
		// 如果开始为小于1 介绍大于列表长度
		if (begin < 1 && end >= size) {
			return list;
		}
		// 判断如果begin大于等于元素列表大小 返回原列表
		if (begin > size) {
			return emptyList();
		}
		// 判断begin
		begin = begin < 0 ? 0 : begin;
		// 如果begin>end
		end = begin > end ? begin + end : end;
		// 判断end
		end = end < 1 || end > size ? size : end;
		// 返回列表
		return list.subList(begin, end);
		// // 获得循环长度
		// int len = begin + end;
		// // 声明一个保存结果的列表
		// List<E> ls = getList(end);
		// // 循环获得元素
		// for (int i = begin; i < len; i++) {
		// // 添加元素
		// ls.add(list.get(i));
		// }
		// // 返回列表
		// return ls;
	}

	/**
	 * 给List排序
	 * @param list 要排序的List
	 * @return 排完序的List
	 */
	public static <E extends Comparable<? super E>> List<E> sort(List<E> list) {
		// 排序
		Collections.sort(list);
		// 返回list
		return list;
	}

	/**
	 * 调用每个元素的toString()方法
	 * @param list
	 * @return
	 */
	public static String toString(List<?> list) {
		return ArrayUtil.toString(toArray(list));
	}

	/**
	 * 把一个列表变成数组
	 * @return 一个不可变的空List
	 */
	public static <E> E[] toArray(List<E> list) {
		// 判断列表不为空
		if (EmptyUtil.isEmpty(list)) {
			return ArrayUtil.getArray();
		}
		// 返回数组
		return (E[]) list.toArray(ArrayUtil.getArray(list.get(0).getClass(), list.size()));
	}

	/**
	 * 把一个列表变成数组
	 * @return 一个不可变的空List
	 */
	public static <E> E[] toArray(List<Object> list, Class<E> clazz) {
		// 判断列表不为空
		if (EmptyUtil.isEmpty(list)) {
			return ArrayUtil.getArray(clazz, 0);
		}
		// 返回数组
		return list.toArray(ArrayUtil.getArray(clazz, list.size()));
	}

	/**
	 * 获得一个不可变的空List
	 * @return 一个不可变的空List
	 */
	public static <E> List<E> emptyList() {
		return Collections.emptyList();
	}

	/**
	 * 是否包含在list中 如果list为空或则o为null直接返回false 如果list中类型与o不同 那么转换为String 在对比
	 * @param list
	 * @param o
	 * @return
	 */
	public static boolean contains(List<Object> list, Object o) {
		// 判断不为空
		if (!EmptyUtil.isEmpty(list) && o != null) {
			for (Object obj : list) {
				if (o.getClass().equals(obj.getClass()) ? o.equals(obj) : Conversion.toString(o).equals(Conversion.toString(obj))) {
					return true;
				}
			}
		}
		// 返回false
		return false;
	}

	/**
	 * 获得列表数量
	 * @param list 数据列表
	 * @return 数量
	 */
	public static int size(List<?> list) {
		return EmptyUtil.isEmpty(list) ? 0 : list.size();
	}

	/**
	 * 私有构造 禁止外部实例化
	 */
	private Lists() {}
}
