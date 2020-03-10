package com.weicoder.common.util;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * 一些公用的方法类
 * 
 * @author WD
 */
public class EmptyUtil {
	/**
	 * 判断对象是否非空 !isEmptys(objs...);
	 * 
	 * @param  objs 对象
	 * @return      true为空,false非空
	 */
	public static boolean isNotEmptys(Object... objs) {
		return !isEmptys(objs);
	}

	/**
	 * 判断对象是否空 传入的对象有一个为空就返回 false
	 * 
	 * @param  objs 对象
	 * @return      true为空,false非空
	 */
	public static boolean isEmptys(Object... objs) {
		// 循环判断 如果有一个对象为空 返回 true
		for (Object o : objs)
			if (isEmpty(o))
				return true;
		// 全部通过返回 false
		return false;
	}

	/**
	 * 判断对象数组是否空 判断 objects == null || objects.length == 0
	 * 
	 * @param  objects 数组对象
	 * @return         true为空,false非空
	 */
	public static boolean isEmpty(Object[] objects) {
		return objects == null || objects.length == 0;
	}

	/**
	 * 判断对象是否非空 !isEmpty(obj);
	 * 
	 * @param  obj 对象
	 * @return     true为空,false非空
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 判断对象是否空 判断 object == null
	 * 
	 * @param  obj 对象
	 * @return     true为空,false非空
	 */
	public static boolean isEmpty(Object obj) {
		// 判断对象类型
		if (obj == null)
			return true;
		if (obj instanceof String)
			return isEmpty((String) obj);
		if (obj instanceof byte[])
			return isEmpty((byte[]) obj);
		if (obj instanceof Collection<?>)
			return isEmpty((Collection<?>) obj);
		if (obj instanceof Map<?, ?>)
			return isEmpty((Map<?, ?>) obj);
		if (obj instanceof Object[])
			return isEmpty((Object[]) obj);
		if (obj instanceof int[])
			return isEmpty((int[]) obj);
		if (obj instanceof CharSequence)
			return isEmpty((CharSequence) obj);
		return false;
	}

	/**
	 * 判断文件是否空 判断 file == null || file.exists();
	 * 
	 * @param  file 对象
	 * @return      true为空,false非空
	 */
	public static boolean isEmpty(File file) {
		return file == null || !file.exists();
	}

	/**
	 * 判断字节数组是否空 判断 b == null || b.length == 0
	 * 
	 * @param  b 字节数组
	 * @return   true为空,false非空
	 */
	public static boolean isEmpty(byte[] b) {
		return b == null || b.length == 0;
	}

	/**
	 * 返回集合是否为空 判断 c == null || c.size() == 0
	 * 
	 * @param  c 实现Collection接口集合
	 * @return   true为空,false非空
	 */
	public static boolean isEmpty(Collection<?> c) {
		return c == null || c.size() == 0;
	}

	/**
	 * 返回Map是否为空 判断 m == null || m.size() == 0
	 * 
	 * @param  m 实现Map接口集合
	 * @return   true为空,false非空
	 */
	public static boolean isEmpty(Map<?, ?> m) {
		return m == null || m.size() == 0;
	}

	/**
	 * 判断int数组是否空 判断 objects == null || objects.length == 0
	 * 
	 * @param  objects 数组对象
	 * @return         true为空,false非空
	 */
	public static boolean isEmpty(int[] objects) {
		return objects == null || objects.length == 0;
	}

	/**
	 * 返回字符串是否为空 判断 s== null || s.length() == 0;
	 * 
	 * @param  s 字符串
	 * @return   true为空,false非空
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	/**
	 * 返回字符串是否为空 判断cs == null || cs.length() == 0;
	 * 
	 * @param  cs CharSequence接口与子对象
	 * @return    true为空,false非空
	 */
	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}
}
