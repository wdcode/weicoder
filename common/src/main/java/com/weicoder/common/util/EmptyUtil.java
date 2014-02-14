package com.weicoder.common.util;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import com.weicoder.common.interfaces.Empty;
import com.weicoder.common.lang.Conversion;

/**
 * 一些公用的方法类
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-01
 */
public final class EmptyUtil {
	/**
	 * 判断对象是否空 判断 object == null
	 * @param obj 对象
	 * @return true为空,false非空
	 */
	public static boolean isEmpty(Object obj) {
		// 判断对象类型
		if (obj == null) {
			return true;
		} else if (obj instanceof Empty) {
			return isEmpty((Empty) obj);
		} else if (obj instanceof byte[]) {
			return isEmpty((byte[]) obj);
		} else if (obj instanceof Collection<?>) {
			return isEmpty((Collection<?>) obj);
		} else if (obj instanceof Map<?, ?>) {
			return isEmpty((Map<?, ?>) obj);
		} else if (obj instanceof Object[]) {
			return isEmpty((Object[]) obj);
		} else if (obj instanceof int[]) {
			return isEmpty((int[]) obj);
		} else if (obj instanceof String) {
			return isEmpty((String) obj);
		} else if (obj instanceof CharSequence) {
			return isEmpty((CharSequence) obj);
		} else if (obj instanceof Integer) {
			return Conversion.toInt(obj) == 0;
		} else if (obj instanceof Boolean) {
			return !Conversion.toBoolean(obj);
		} else {
			return obj == null;
		}
	}

	/**
	 * 判断文件是否空 判断 file == null || file.exists();
	 * @param file 对象
	 * @return true为空,false非空
	 */
	public static boolean isEmpty(File file) {
		return file == null || !file.exists();
	}

	/**
	 * 判断对象是否空 判断 object == null
	 * @param object 对象
	 * @return true为空,false非空
	 */
	public static boolean isEmpty(Empty e) {
		return e == null || e.isEmpty();
	}

	/**
	 * 判断对象是否空 判断 object == null
	 * @param object 对象
	 * @return true为空,false非空
	 */
	public static boolean isEmpty(byte[] b) {
		return b == null || b.length == 0;
	}

	/**
	 * 返回集合是否为空 判断 c == null || c.size() == 0
	 * @param c 实现Collection接口集合
	 * @return true为空,false非空
	 */
	public static boolean isEmpty(Collection<?> c) {
		return c == null || c.size() == 0;
	}

	/**
	 * 返回Map是否为空 判断 m == null || m.size() == 0
	 * @param m 实现Map接口集合
	 * @return true为空,false非空
	 */
	public static boolean isEmpty(Map<?, ?> m) {
		return m == null || m.size() == 0;
	}

	/**
	 * 判断对象数组是否空 判断 objects == null || objects.length == 0
	 * @param objects 数组对象
	 * @return true为空,false非空
	 */
	public static boolean isEmpty(Object[] objects) {
		return objects == null || objects.length == 0;
	}

	/**
	 * 判断int数组是否空 判断 objects == null || objects.length == 0
	 * @param objects 数组对象
	 * @return true为空,false非空
	 */
	public static boolean isEmpty(int[] objects) {
		return objects == null || objects.length == 0;
	}

	/**
	 * 返回字符串是否为空 判断cs == null || cs.length() == 0;
	 * @param cs CharSequence接口与子对象
	 * @return true为空,false非空
	 */
	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	/**
	 * 私有构造，禁止外部实例化
	 */
	private EmptyUtil() {}
}
