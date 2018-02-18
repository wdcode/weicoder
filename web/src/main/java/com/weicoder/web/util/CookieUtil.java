package com.weicoder.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.web.params.WebParams;

/**
 * Cookie相关操作
 * @author WD
 */
public final class CookieUtil {
	/**
	 * 添加Cookie 浏览器进程
	 * @param response Response
	 * @param name 名
	 * @param value 值
	 */
	public static void add(HttpServletResponse response, String name, String value) {
		add(response, name, value, WebParams.DOMAIN, -1);
	}

	/**
	 * 添加Cookie 浏览器进程
	 * @param response Response
	 * @param name 名
	 * @param value 值
	 * @param domain 域名
	 */
	public static void add(HttpServletResponse response, String name, String value, String domain) {
		add(response, name, value, domain, -1);
	}

	/**
	 * 添加Cookie 保存maxAge秒
	 * @param response Response
	 * @param name 名
	 * @param value 值
	 * @param maxAge 保存多少秒
	 */
	public static void add(HttpServletResponse response, String name, String value, int maxAge) {
		add(response, name, value, WebParams.DOMAIN, maxAge);
	}

	/**
	 * 添加Cookie 保存maxAge秒
	 * @param response Response
	 * @param name 名
	 * @param value 值
	 * @param domain 域名
	 * @param maxAge 保存多少秒
	 */
	public static void add(HttpServletResponse response, String name, String value, String domain, int maxAge) {
		// 实例化Cookie
		Cookie cookie = new Cookie(name, value);
		// 设置Cookie过期时间
		cookie.setMaxAge(maxAge);
		// 设置目录
		cookie.setPath(StringConstants.BACKSLASH);
		// 设置域
		if (!EmptyUtil.isEmpty(domain)) {
			cookie.setDomain(domain);
		}
		// 添加Cookie
		response.addCookie(cookie);
	}

	/**
	 * 删除Cookie
	 * @param response Response
	 * @param name 名
	 */
	public static void remove(HttpServletResponse response, String name) {
		remove(response, name, WebParams.DOMAIN);
	}

	/**
	 * 删除Cookie
	 * @param response Response
	 * @param name 名
	 * @param domain 域名
	 */
	public static void remove(HttpServletResponse response, String name, String domain) {
		add(response, name, null, WebParams.DOMAIN, 0);
	}

	/**
	 * 根据name获得Cookie 没找到返回null
	 * @param request Request
	 * @param name CookieName
	 * @return Cookie
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		// 获得所有Cookie
		Cookie[] cookies = request.getCookies();
		// 判断有Cookie
		if (EmptyUtil.isEmpty(cookies)) {
			return null;
		}
		// 声明一个Cookie,用户保存临时Cookie
		Cookie cookie = null;
		// 循环Cookie
		for (int i = 0; i < cookies.length; i++) {
			// 获得Cookie
			cookie = cookies[i];
			// 判断Cookie
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}
		// 返回Cookie
		return null;
	}

	/**
	 * 根据name获得CookieValue 没找到返回""
	 * @param request Request
	 * @param name CookieName
	 * @return CookieValue
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		// 根据name获得Cookie
		Cookie cookie = getCookie(request, name);
		// 如果Cookie为空返回空串,不为空返回Value
		return EmptyUtil.isEmpty(cookie) ? StringConstants.EMPTY : cookie.getValue();
	}

	private CookieUtil() {}
}