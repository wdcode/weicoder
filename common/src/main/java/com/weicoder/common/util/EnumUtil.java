package com.weicoder.common.util;

import com.weicoder.common.W;

/**
 * enum枚举使用
 * 
 * @author wudi
 */
public class EnumUtil {
	/**
	 * 判断枚举和对比对象是否相等 相同对象或则字符串区分大小写相等 或则 值相等
	 * 
	 * @param  e 枚举对象
	 * @param  o 要对比的对象
	 * @return   是否相等
	 */
	public static boolean equals(Enum<?> e, Object o) {
		// 为null
		if (e == null || o == null)
			return false;
		// 如果是数字
		if (o instanceof Number)
			return W.C.toInt(o) == e.ordinal();
		// 是字符串
		if (o instanceof String)
			return e.name().equalsIgnoreCase(W.C.toString(o));
		// 判断
		return e.equals(o);
	}

	/**
	 * 根据输入的枚举类型与对象转换成对应的枚举 如果是数字按getEnumConstants的下标获得 如果是String 转换对象区分大小写 如果是本对象直接强转
	 * 
	 * @param  <E>  枚举对象的泛型
	 * @param  type 枚举对象的类
	 * @param  o    要转换的枚举
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> Enum<E> value(Class<E> type, Object o) {
		// 为null
		if (type == null || o == null)
			return null;
		// 如果是本枚举
		if (o.getClass().equals(type))
			return (Enum<E>) o;
		// 如果是数字
		if (o instanceof Number)
			return type.getEnumConstants()[W.C.toInt(o)];
		// 是字符串
		if (o instanceof String)
			return Enum.valueOf(type, W.C.toString(o));
		// 没有找到 对应枚举 返回 null
		return null;
	}
}
