package com.weicoder.web.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.weicoder.common.lang.W;
import com.weicoder.common.util.U;

/**
 * 保存属性工具类
 * @author WD 
 */
public final class AttributeUtil {
	/**
	 * 设置属性 如果是Cookie保存是浏览器进程
	 * @param request Request
	 * @param response Response
	 * @param key 属性键
	 * @param value 属性值
	 */
	public static void set(HttpServletRequest request, HttpServletResponse response, String key, Object value) {
		set(request, response, key, value, -1);
	}

	/**
	 * 设置属性
	 * @param request Request
	 * @param response Response
	 * @param key 属性键
	 * @param value 属性值
	 * @param maxAge 如果是Cookie的话保存多长时间
	 */
	public static void set(HttpServletRequest request, HttpServletResponse response, String key, Object value, int maxAge) {
		// 判断使用什么方式保存属性
		// 使用Cookie保存
		CookieUtil.add(response, key, W.C.toString(value), maxAge);
		// 使用Session保存
		SessionUtil.setAttribute(RequestUtil.getSession(request), key, value, maxAge);
	}

	/**
	 * 获得属性 如果没找到返回null
	 * @param request Request
	 * @param key 属性键
	 * @return 属性值
	 */
	public static Object get(HttpServletRequest request, String key) {
		// 先获得cookie保存
		String value = CookieUtil.getCookieValue(request, key);
		// 如果值为空 获得Session保存
		return U.E.isEmpty(value) ? SessionUtil.getAttribute(RequestUtil.getSession(request), key) : value;
	}

	/**
	 * 删除属性
	 * @param request Request
	 * @param response Response
	 * @param key 属性键
	 */
	public static void remove(HttpServletRequest request, HttpServletResponse response, String key) {
		// 使用Cookie保存
		CookieUtil.remove(response, key);
		// 使用Session保存
		SessionUtil.removeAttribute(RequestUtil.getSession(request), key);
	}

	/**
	 * 私有构造
	 */
	private AttributeUtil() {}
}
