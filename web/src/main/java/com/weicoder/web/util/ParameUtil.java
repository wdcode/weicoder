package com.weicoder.web.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.EmptyUtil;

/**
 * 获得提交参数方法
 * @author WD
 * @since JDK7
 * @version 1.0 2010-03-03
 */
public final class ParameUtil {
	/**
	 * 获得request的提交参数 如果没有返回""
	 * @param request Request
	 * @param key 属性值
	 * @return value
	 */
	public static String getParameter(ServletRequest request, String key) {
		return getParameter(request, key, StringConstants.EMPTY);
	}

	/**
	 * 获得request的提交参数 如果没有返回defaultValue
	 * @param request Request
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static String getParameter(ServletRequest request, String key, String defaultValue) {
		return RequestUtil.getParameter(request, key, defaultValue);
	}

	/**
	 * 获得request的提交参数 如果没有返回0
	 * @param request Request
	 * @param key 属性值
	 * @return value
	 */
	public static int getIntParameter(ServletRequest request, String key) {
		return getIntParameter(request, key, 0);
	}

	/**
	 * 获得request的提交参数 如果没有返回defaultValue
	 * @param request Request
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static int getIntParameter(ServletRequest request, String key, int defaultValue) {
		return Conversion.toInt(RequestUtil.getParameter(request, key), defaultValue);
	}

	/**
	 * 获得request的提交参数 如果没有返回0
	 * @param request Request
	 * @param key 属性值
	 * @return value
	 */
	public static long getLongParameter(ServletRequest request, String key) {
		return getLongParameter(request, key, 0);
	}

	/**
	 * 获得request的提交参数 如果没有返回defaultValue
	 * @param request Request
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static long getLongParameter(ServletRequest request, String key, long defaultValue) {
		return Conversion.toLong(RequestUtil.getParameter(request, key), defaultValue);
	}

	/**
	 * 获得request的提交参数 如果没有返回defaultValue
	 * @param request ServletRequest
	 * @param key 属性值
	 * @return value
	 */
	public static double getDoubleParameter(ServletRequest request, String key) {
		return getDoubleParameter(request, key, 0);
	}

	/**
	 * 获得request的提交参数 如果没有返回defaultValue
	 * @param request ServletRequest
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static double getDoubleParameter(ServletRequest request, String key, double defaultValue) {
		return Conversion.toDouble(RequestUtil.getAttribute(request, key), defaultValue);
	}

	/**
	 * 获得request的属性 如果没有返回null
	 * @param request Request
	 * @param key 属性值
	 * @return value
	 */
	public static Object getAttribute(ServletRequest request, String key) {
		return getAttribute(request, key, null);
	}

	/**
	 * 获得request的属性 如果没有返回defaultValue
	 * @param request Request
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static <E> E getAttribute(ServletRequest request, String key, E defaultValue) {
		return RequestUtil.getAttribute(request, key, defaultValue);
	}

	/**
	 * 获得session的属性 如果没有返回null
	 * @param session HttpSession
	 * @param key 属性值
	 * @return value
	 */
	public static Object getAttribute(HttpSession session, String key) {
		return getAttribute(session, key, null);
	}

	/**
	 * 获得session的属性 如果没有返回defaultValue
	 * @param session HttpSession
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static <E> E getAttribute(HttpSession session, String key, E defaultValue) {
		return SessionUtil.getAttribute(session, key, defaultValue);
	}

	/**
	 * 获得application的属性 如果没有返回null
	 * @param context ServletContext
	 * @param key 属性值
	 * @return value
	 */
	public static Object getAttribute(ServletContext context, String key) {
		return getAttribute(context, key, null);
	}

	/**
	 * 获得application的属性 如果没有返回defaultValue
	 * @param context ServletContext
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static <E> E getAttribute(ServletContext context, String key, E defaultValue) {
		return ApplicationUtil.getAttribute(context, key, defaultValue);
	}

	/**
	 * 从低到高的域属性读取 request -> session -> application 如果没有返回null
	 * @param request Request
	 * @param key 属性值
	 * @return value
	 */
	public static Object getAttributeScope(HttpServletRequest request, String key) {
		return getAttributeScope(request, key, null);
	}

	/**
	 * 从低到高的域属性读取 request -> session -> application 如果没有返回defaultValue
	 * @param request Request
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static <E> E getAttributeScope(HttpServletRequest request, String key, E defaultValue) {
		// 获得E
		E e = getAttribute(request, key, defaultValue);
		// 判断为空 读取Session属性
		if (EmptyUtil.isEmpty(e)) {
			e = getAttribute(RequestUtil.getSession(request), key, defaultValue);
		}
		// 如果e为null 返回Session中属性 否则返回e
		return EmptyUtil.isEmpty(e) ? getAttribute(SessionUtil.getServletContext(RequestUtil.getSession(request)), key, defaultValue) : e;
	}

	/**
	 * 先获得getParameter如果值为空,依次获得 request -> session -> application的getAttribute
	 * @param request Request
	 * @param key 属性值
	 * @return value
	 */
	public static Object get(HttpServletRequest request, String key) {
		return get(request, key, null);
	}

	/**
	 * 先获得getParameter如果值为空,依次获得 request -> session -> application的getAttribute
	 * @param request Request
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static <E> E get(HttpServletRequest request, String key, E defaultValue) {
		// 获得value
		String value = getParameter(request, key, Conversion.toString(defaultValue));
		// 如果e为null 返回Session中属性 否则返回e
		return EmptyUtil.isEmpty(value) ? getAttributeScope(request, key, defaultValue) : (E) value;
	}

	/**
	 * 私有构造
	 */
	private ParameUtil() {}
}
