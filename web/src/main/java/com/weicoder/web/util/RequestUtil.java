package com.weicoder.web.util;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.web.constants.HttpConstants;

/**
 * Request一些相关操作类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-01-20
 */
public final class RequestUtil {
	/**
	 * 获得request的提交参数 如果没有返回空Map
	 * @param request ServletRequest
	 */
	public static Map<String, String> getParameters(ServletRequest request) {
		// 声明空Map
		Map<String, String> map = Maps.getMap();
		// 获得提交的所以参数名
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			// 获得参数Key
			String key = e.nextElement();
			// 获得参数值并添加到Map中
			map.put(key, request.getParameter(key));
		}
		// 返回Map
		return map;
	}

	/**
	 * 获得request的提交参数 如果没有返回""
	 * @param request ServletRequest
	 * @param key 属性值
	 * @return value
	 */
	public static String getParameter(ServletRequest request, String key) {
		return getParameter(request, key, StringConstants.EMPTY);
	}

	/**
	 * 获得request的提交参数 如果没有返回defaultValue
	 * @param request ServletRequest
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static String getParameter(ServletRequest request, String key, String defaultValue) {
		// 获得值
		String value = EmptyUtil.isEmpty(request) ? defaultValue : request.getParameter(key);
		// 如果值为空 返回默认值
		return EmptyUtil.isEmpty(value) ? defaultValue : value;
	}

	/**
	 * 获得request的属性 如果没有返回defaultValue
	 * @param request ServletRequest
	 * @param key 属性值
	 * @return value
	 */
	public static Object getAttribute(ServletRequest request, String key) {
		return getAttribute(request, key, null);
	}

	/**
	 * 获得request的属性 如果没有返回defaultValue
	 * @param request ServletRequest
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static <E> E getAttribute(ServletRequest request, String key, E defaultValue) {
		// 获得值
		E value = EmptyUtil.isEmpty(request) ? defaultValue : (E) request.getAttribute(key);
		// 如果值为空 返回默认值
		return EmptyUtil.isEmpty(value) ? defaultValue : value;
	}

	/**
	 * 获得Session
	 * @param request HttpServletRequest
	 * @return HttpSession
	 */
	public static HttpSession getSession(HttpServletRequest request) {
		return EmptyUtil.isEmpty(request) ? null : request.getSession();
	}

	/**
	 * 获得Session
	 * @param request HttpServletRequest
	 * @param b Boolean
	 * @return HttpSession
	 */
	public static HttpSession getSession(HttpServletRequest request, boolean b) {
		return EmptyUtil.isEmpty(request) ? null : request.getSession(b);
	}

	/**
	 * 设置request的属性
	 * @param request ServletRequest
	 * @param key 属性值
	 * @param value 属性值
	 */
	public static void setAttribute(ServletRequest request, String key, Object value) {
		if (!EmptyUtil.isEmpty(request)) {
			request.setAttribute(key, value);
		}
	}

	/**
	 * 获得浏览器类型
	 * @param request ServletRequest
	 * @return 浏览器类型
	 */
	public static String getUserAgent(HttpServletRequest request) {
		return request.getHeader(HttpConstants.HEADER_USER_AGENT);
	}

	/**
	 * 获得浏览器语言
	 * @param request ServletRequest
	 * @return 浏览器语言
	 */
	public static String getLanguage(HttpServletRequest request) {
		return request.getHeader(HttpConstants.HEADER_ACCEPT_LANGUAGE);
	}

	/**
	 * 私有构造
	 */
	private RequestUtil() {}
}
