package com.weicoder.common.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.weicoder.common.lang.W.C;
import com.weicoder.common.util.ArrayUtil;
import com.weicoder.common.util.U;
import com.weicoder.common.util.U.B;

/**
 * List的帮助类
 * 
 * @author WD
 */
public sealed class Lists permits W.L {
	/**
	 * 转换类型
	 * 
	 * @param <E>
	 * @param list
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> toList(Collection<?> list, Class<E> cls) {
		return (List<E>) list(list).stream().map(l -> U.C.isBaseType(cls) ? C.to(l, cls) : B.copy(l, cls))
				.collect(Collectors.toList());
	}

	/**
	 * 去除list的所有null
	 * 
	 * @param list 要过滤的list
	 * @param <E>  泛型
	 * @return List
	 */
	public static <E> List<E> notNull(List<E> list) {
		return (U.E.isEmpty(list) || list.indexOf(null) == 0) ? list
				: list.stream().filter(Objects::nonNull).collect(Collectors.toList());
	}

	/**
	 * 获得指定索引的list元素 list为空返回null 返回指定索引的类 如果索引小于0为0 大于列表长度返回最后一个类
	 * 
	 * @param list 元素列表
	 * @param i    索引
	 * @param <E>  泛型
	 * @return 处理后的元素
	 */
	public static <E> E get(List<E> list, int i) {
		return U.E.isEmpty(list) ? null : i < 0 ? list.get(0) : i >= list.size() ? list.get(list.size() - 1) : list.get(i);
	}

	/**
	 * 获得列表最后一个元素
	 * 
	 * @param <E>  泛型
	 * @param list 列表
	 * @return 列表最后一个元素
	 */
	public static <E> E last(List<E> list) {
		return U.E.isEmpty(list) ? null : list.get(list.size() - 1);
	}

	/**
	 * 判断是否列表
	 * 
	 * @param obj 对象
	 * @return 是否列表
	 */
	public static boolean is(Object obj) {
		return U.E.isNotEmpty(obj) && obj instanceof List<?>;
	}

	/**
	 * 获得List实例 默认初始化大小为10
	 * 
	 * @param <E> 泛型
	 * @return List
	 */
	public static <E> List<E> list() {
		return new ArrayList<>();
	}

	/**
	 * 获得线程安全list
	 * 
	 * @param <E> 泛型
	 * @return 线程安全List
	 */
	public static <E> List<E> sync() {
		return sync(list());
	}

	/**
	 * 把传入的List包装成线程安全List
	 * 
	 * @param <E>  泛型
	 * @param list 传入的list
	 * @return 线程安全List
	 */
	public static <E> List<E> sync(List<E> list) {
		return Collections.synchronizedList(list);
	}

	/**
	 * 获得List实例
	 * 
	 * @param size 初始化大小
	 * @param <E>  泛型
	 * @return List
	 */
	public static <E> List<E> list(int size) {
		return new ArrayList<>(size < 1 ? 1 : size);
	}

	/**
	 * 获得List实例
	 * 
	 * @param es  初始化的数组
	 * @param <E> 泛型
	 * @return List
	 */
	@SafeVarargs
	public static <E> List<E> list(E... es) {
		return list(U.A.toList(es));
	}

	/**
	 * 获得List实例
	 * 
	 * @param c   初始化的集合
	 * @param <E> 泛型
	 * @return List
	 */
	public static <E> List<E> list(Collection<E> c) {
		return c == null ? new ArrayList<>() : new ArrayList<>(c);
	}

	/**
	 * 拷贝列表并清除原集合数据 如果不清除原数据使用@see list(Collection<E> c)
	 * 
	 * @param <E> 泛型
	 * @param c   原集合
	 * @return 新复制的列表
	 */
	public static <E> List<E> copy(Collection<E> c) {
		List<E> l = list(c);
		c.clear();
		return l;
	}

	/**
	 * 获得List实例
	 * 
	 * @param c   初始化的集合
	 * @param <E> 泛型
	 * @return List
	 */
	@SafeVarargs
	public static <E> List<E> list(Collection<E>... c) {
		// 获得一个列表
		List<E> list = list();
		// 循环集合
		for (int i = 0; i < c.length; i++)
			// 添加到列表中
			if (U.E.isNotEmpty(c[i]))
				list.addAll(c[i]);
		// 返回列表
		return list;
	}

	/**
	 * 切片List 把list按slice数据切分
	 * 
	 * @param list  要分片的list
	 * @param slice 每片的数量
	 * @param <E>   泛型
	 * @return 分片后的list 列表里为原有对象的list
	 */
	public static <E> List<List<E>> slice(List<E> list, int slice) {
		// 声明返回结果
		List<List<E>> res = list();
		// 对象为空或者分片小于1直接返回列表
		if (U.E.isEmpty(list) || slice < 1 || list.size() <= slice)
			res.add(list);
		else {
			// 开始数
			int n = 0;
			// 循环分解
			while (true) {
				// 分解成list
				int start = n * slice;
				List<E> ls = sub(list, start, start + slice);
				n++;
				// 不为空添加到列表
				if (U.E.isNotEmpty(ls))
					res.add(ls);
				// 如果
				if (ls.size() < slice)
					break;
			}
		}
		// 返回结果
		return res;
	}

	/**
	 * 返回列表从begin开始返回end结束元素
	 * 
	 * @param list  元素列表
	 * @param begin 开始包含
	 * @param end   结束不包含
	 * @param <E>   泛型
	 * @return 返回获得元素列表
	 */
	public static <E> List<E> sub(List<E> list, int begin, int end) {
		// 如果列表为空返回一个空列表
		if (U.E.isEmpty(list))
			return list;
		// 获得元列表大小
		int size = list.size();
		// 如果开始为小于1 介绍大于列表长度
		if (begin < 1 && end >= size)
			return list;
		// 判断如果begin大于等于元素列表大小 返回原列表
		if (begin > size)
			return empty();
		// 判断begin
		begin = begin < 0 ? 0 : begin;
		// 如果begin>end
		end = begin > end ? begin + end : end;
		// 判断end
		end = end < 1 || end > size ? size : end;
		// 返回列表
		return list.subList(begin, end);
	}

	/**
	 * 给List排序
	 * 
	 * @param c   要排序的List
	 * @param <E> 泛型
	 * @return 排完序的List
	 */
	public static <E extends Comparable<? super E>> List<E> sort(List<E> list) {
		list.sort(null);
		// 返回list
		return list;
	}

	/**
	 * 调用每个元素的toString()方法
	 * 
	 * @param list 列表
	 * @return 字符串
	 */
	public static String toString(List<?> list) {
		return ArrayUtil.toString(toArray(list));
	}

	/**
	 * 把一个列表变成数组
	 * 
	 * @param list 列表
	 * @param <E>  泛型
	 * @return 一个不可变的空List
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] toArray(List<E> list) {
		// 判断列表不为空
		if (U.E.isEmpty(list))
			return ArrayUtil.getArray();
		// 返回数组
		return (E[]) list.toArray(ArrayUtil.getArray(list.get(0).getClass(), list.size()));
	}

	/**
	 * 把一个列表变成数组
	 * 
	 * @param list  列表
	 * @param clazz 类
	 * @param <E>   泛型
	 * @return 一个不可变的空List
	 */
	public static <E> E[] toArray(List<Object> list, Class<E> clazz) {
		// 判断列表不为空
		if (U.E.isEmpty(list))
			return ArrayUtil.getArray(clazz, 0);
		// 返回数组
		return list.toArray(ArrayUtil.getArray(clazz, list.size()));
	}

	/**
	 * 获得一个不可变的空List
	 * 
	 * @param <E> 泛型
	 * @return 一个不可变的空List
	 */
	public static <E> List<E> empty() {
		return Collections.emptyList();
	}

	/**
	 * 是否包含在list中 如果list为空或则o为null直接返回false 如果list中类型与o不同 那么转换为String 在对比
	 * 
	 * @param list 列表
	 * @param o    对象
	 * @return 布尔
	 */
	public static boolean contains(List<Object> list, Object o) {
		// 判断不为空
		if (U.E.isNotEmpty(list) && o != null)
			for (Object obj : list)
				if (o.getClass().equals(obj.getClass()) ? o.equals(obj) : W.C.toString(o).equals(W.C.toString(obj)))
					return true;
		// 返回false
		return false;
	}

	/**
	 * 获得列表数量
	 * 
	 * @param list 数据列表
	 * @return 数量
	 */
	public static int size(List<?> list) {
		return U.E.isEmpty(list) ? 0 : list.size();
	}
}
