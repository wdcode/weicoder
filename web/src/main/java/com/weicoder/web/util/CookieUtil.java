package com.weicoder.web.util;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.params.WebParams;

/**
 * Cookie相关操作
 * @author WD
 */
public final class CookieUtil {

	/**
	 * 把结果写cookie
	 * @param response response
	 * @param res 返回结果
	 * @param names 要写cookie的键 如果为空就写全部属性
	 */
	public static void adds(HttpServletResponse response, Object res, String... names) {
		adds(response, -1, res, names);
	}

	/**
	 * 把结果写cookie
	 * @param response response
	 * @param maxAge 过期时间秒
	 * @param res 返回结果
	 * @param names 要写cookie的键 如果为空就写全部属性
	 */
	public static void adds(HttpServletResponse response, int maxAge, Object res, String... names) {
		try {
			// 如果返回结果是map
			if (res instanceof Map<?, ?>) {
				// 转成map
				Map<?, ?> map = (Map<?, ?>) res;
				// 如果键为空
				if (EmptyUtil.isEmpty(names))
					// 写全部属性
					map.forEach((k, v) -> add(response, Conversion.toString(k), Conversion.toString(v), maxAge));
				else
					// 写指定属性
					for (String name : names)
						add(response, name, Conversion.toString(name), maxAge);
			} else {
				// 普通实体按字段返回 如果键为空
				if (EmptyUtil.isEmpty(names))
					// 写全部属性
					BeanUtil.getFields(res.getClass()).forEach(field -> {
						// 值不为空 写cookie
						String val = Conversion.toString(BeanUtil.getFieldValue(res, field));
						if (EmptyUtil.isNotEmpty(val))
							add(response, field.getName(), val, maxAge);
					});
				else {
					// 写指定属性
					for (String name : names) {
						// 值不为空 写cookie
						String val = Conversion.toString(BeanUtil.getFieldValue(res, name));
						if (EmptyUtil.isNotEmpty(val))
							add(response, name, val, maxAge);
					}
				}
			}
			Logs.debug("adds cookie names={} res={}", res);
		} catch (Exception e) {
			Logs.error(e);
		}
	}

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
		try {
			// 实例化Cookie
			Cookie cookie = new Cookie(name, value);
			// 设置Cookie过期时间
			cookie.setMaxAge(maxAge);
			// 设置目录
			cookie.setPath(StringConstants.BACKSLASH);
			// 设置域
			if (EmptyUtil.isEmpty(domain)) {
				// 添加Cookie
				response.addCookie(cookie);
				Logs.debug("add cookie name={} value={} domain={} maxAge={}", name, value, domain, maxAge);
			} else {
				// 写不同的域
				for (String d : StringUtil.split(domain, StringConstants.COMMA)) {
					cookie.setDomain(d);
					// 添加Cookie
					response.addCookie(cookie);
					Logs.debug("add cookie name={} value={} domain={} maxAge={}", name, value, d, maxAge);
				}
			}
		} catch (Exception e) {
			Logs.error(e);
		}
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
		if (EmptyUtil.isEmpty(cookies))
			return null;
		// 声明一个Cookie,用户保存临时Cookie
		Cookie cookie = null;
		// 循环Cookie
		for (int i = 0; i < cookies.length; i++) {
			// 获得Cookie
			cookie = cookies[i];
			// 判断Cookie
			if (name.equals(cookie.getName()))
				return cookie;
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