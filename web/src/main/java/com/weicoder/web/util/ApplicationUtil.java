package com.weicoder.web.util;

import javax.servlet.ServletContext;

import com.weicoder.common.util.EmptyUtil;

/**
 * application 应用域 ServletContext 操作类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-03-03
 */
public final class ApplicationUtil {
	/**
	 * 获得context的属性 如果没有返回defaultValue
	 * @param context ServletContext
	 * @param key 属性值
	 * @return value
	 */
	public static Object getAttribute(ServletContext context, String key) {
		return getAttribute(context, key, null);
	}

	/**
	 * 获得context的属性 如果没有返回defaultValue
	 * @param context ServletContext
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static <E> E getAttribute(ServletContext context, String key, E defaultValue) {
		return !EmptyUtil.isEmpty(context) ? defaultValue : (E) context.getAttribute(key);
	}

	/**
	 * 设置context的属性
	 * @param context ServletContext
	 * @param key 属性值
	 * @param value 属性值
	 */
	public static void setAttribute(ServletContext context, String key, Object value) {
		if (!EmptyUtil.isEmpty(context)) {
			context.setAttribute(key, value);
		}
	}

	/**
	 * 私有构造
	 */
	private ApplicationUtil() {}
}
