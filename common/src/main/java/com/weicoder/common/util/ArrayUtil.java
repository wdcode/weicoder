package com.weicoder.common.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import com.weicoder.common.constants.C; 
import com.weicoder.common.lang.W;

/**
 * 数组帮助类
 * 
 * @author WD
 */
public sealed class ArrayUtil permits U.A {
	/**
	 * 根据传入的字符串转换成字符数组
	 * 
	 * @param s 字符串
	 * @param c 要转换的类型
	 * @return 字符数组
	 */
	public static Object array(String s, Class<?> c) {
		return array(s, C.S.AMP, c);
	}

	/**
	 * 根据传入的字符串转换成字符数组
	 * 
	 * @param s    字符串
	 * @param sepa 分隔符
	 * @param c    要转换的类型
	 * @return 字符数组
	 */
	public static Object array(String s, String sepa, Class<?> c) {
		// 判断参数不对返回Null
		if (U.E.isEmpty(s) || c == null)
			return null;
		// 分隔出数组
		String[] t = U.S.split(s, sepa);
		Class<?> type = c.isArray() ? c.getComponentType() : c;
		// 如果是字符串数组 直接返回
		if (String.class.equals(type))
			return t;
		// 根据类型声明数组对象
		Object arr = Array.get(type, t.length);
		// 循环转换类型
		for (int i = 0; i < t.length; i++)
			Array.set(arr, i, W.C.to(t[i], type));
		// 返回数组对象
		return arr;
	}

	/**
	 * 判断是否数组
	 * 
	 * @param obj 对象
	 * @return 是否数组
	 */
	public static boolean isArray(Object obj) {
		return U.E.isNotEmpty(obj) && (obj.getClass().isArray());
	}

	/**
	 * 把数组转换成列表
	 * 
	 * @param e   元素数组
	 * @param <E> 泛型
	 * @return 元素列表
	 */
	@SafeVarargs
	public static <E> List<E> toList(E... e) {
		return Arrays.asList(e);
	}

	/**
	 * 拷贝数组 已dest长度为准
	 * 
	 * @param src  原数组
	 * @param dest 目标数组
	 * @param <E>  泛型
	 */
	public static <E> void copy(E[] src, E[] dest) {
		copy(src, dest, 0);
	}

	/**
	 * 拷贝数组 已dest长度为准
	 * 
	 * @param src  原数组
	 * @param dest 目标数组
	 * @param pos  从第几位开始
	 * @param <E>  泛型
	 */
	public static <E> void copy(E[] src, E[] dest, int pos) {
		copy(src, pos, dest, pos, src.length);
	}

	/**
	 * 拷贝数组
	 * 
	 * @param src     原数组
	 * @param srcPos  从原数组第几位开始
	 * @param dest    目标数组
	 * @param destPos 从目标组第几位开始
	 * @param length  拷贝长度
	 * @param <E>     泛型
	 */
	public static <E> void copy(E[] src, int srcPos, E[] dest, int destPos, int length) {
		System.arraycopy(src, srcPos, dest, destPos, length);
	}

	/**
	 * 把int数组包装成Integer数组
	 * 
	 * @param n int数组
	 * @return Integer数组
	 */
	public static Integer[] toInteger(int[] n) {
		// 声明Integer数组
		Integer[] t = new Integer[n.length];
		// 循环int数组
		for (int i = 0; i < n.length; i++)
			t[i] = n[i];
		// 返回Integer数组
		return t;
	}

	/**
	 * 把String数组包装成Integer数组
	 * 
	 * @param s String数组
	 * @return Integer数组
	 */
	public static Integer[] toInteger(String[] s) {
		// 声明Integer数组
		Integer[] t = new Integer[s.length];
		// 循环int数组
		for (int i = 0; i < s.length; i++)
			t[i] = W.C.toInt(s[i]);
		// 返回Integer数组
		return t;
	}

	/**
	 * 把Integer数组转换成int数组
	 * 
	 * @param n Integer数组
	 * @return int数组
	 */
	public static int[] toInt(Integer[] n) {
		// 声明Integer数组
		int[] t = new int[n.length];
		// 循环int数组
		for (int i = 0; i < n.length; i++)
			t[i] = n[i];
		// 返回Integer数组
		return t;
	}

	/**
	 * 把Integer数组转换成String数组
	 * 
	 * @param n Integer数组
	 * @return String数组
	 */
	public static String[] toStringArray(Integer[] n) {
		// 声明Integer数组
		String[] t = new String[n.length];
		// 循环int数组
		for (int i = 0; i < n.length; i++)
			t[i] = W.C.toString(n[i]);
		// 返回Integer数组
		return t;
	}

	/**
	 * 把数组对象转换成字符串
	 * 
	 * @param a 对象数组
	 * @return 字符串
	 */
	public static String[] toStringArray(Serializable[] a) {
		// 声明Integer数组
		String[] t = new String[a.length];
		// 循环序列化数组
		for (int i = 0; i < a.length; i++)
			t[i] = W.C.toString(a[i]);
		// 返回String数组
		return t;
	}

	/**
	 * 把数组对象转换成字符串
	 * 
	 * @param a 对象数组
	 * @return 字符串
	 */
	public static String[] toStringArray(Object[] a) {
		// 声明Integer数组
		String[] t = new String[a.length];
		// 循环序列化数组
		for (int i = 0; i < a.length; i++)
			t[i] = W.C.toString(a[i]);
		// 返回String数组
		return t;
	}

	/**
	 * 把数组对象转换成字符串
	 * 
	 * @param a 对象数组
	 * @return 字符串
	 */
	public static String toString(Object[] a) {
		return Arrays.toString(a).replaceAll("[\\[\\]]", C.S.EMPTY);
	}

	/**
	 * 数组相加
	 * 
	 * @param one 第一个数组因数
	 * @param two 第二个数组因数
	 * @param <E> 泛型
	 * @return 相加后的数组
	 */
	public static <E> E[] add(E[] one, E[] two) {
		return add(one, two, two.length);
	}

	/**
	 * 数组相加
	 * 
	 * @param one       第一个数组因数
	 * @param two       第二个数组因数
	 * @param twoLength 第二个数组长度 一共相加多少长度
	 * @param <E>       泛型
	 * @return 相加后的数组
	 */
	public static <E> E[] add(E[] one, E[] two, int twoLength) {
		return add(one, 0, one.length, two, 0, twoLength);
	}

	/**
	 * 数组相加
	 * 
	 * @param one       第一个数组因数
	 * @param onePos    第一个数组偏移 从第几个元素开始
	 * @param oneLength 第一个数组长度 一共相加多少长度
	 * @param two       第二个数组因数
	 * @param twoPos    第二个数组偏移 从第几个元素开始
	 * @param twoLength 第二个数组长度 一共相加多少长度
	 * @param <E>       泛型
	 * @return 相加后的数组
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] add(E[] one, int onePos, int oneLength, E[] two, int twoPos, int twoLength) {
		// 声明一个数组,长度是第一个和第二个数组长度的和
		E[] result = (E[]) getArray(one.getClass(), oneLength + twoLength);
		// 拷贝第一个数组到和数组
		copy(one, onePos, result, 0, oneLength);
		// 拷贝第二个数组到和数组
		copy(two, twoPos, result, oneLength, twoLength);
		// 返回和数组
		return result;
	}

	/**
	 * 获得数据
	 * 
	 * @param clazz  数组类型
	 * @param length 数组长度
	 * @param <E>    泛型
	 * @return 数组
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] getArray(Class<E> clazz, int length) {
		return (E[]) Array.newInstance(clazz, length);
	}

	/**
	 * 获得数组
	 * 
	 * @param e   要组成数据的参数
	 * @param <E> 泛型
	 * @return e数组
	 */
	@SafeVarargs
	public static <E> E[] getArray(E... e) {
		return e;
	}
}