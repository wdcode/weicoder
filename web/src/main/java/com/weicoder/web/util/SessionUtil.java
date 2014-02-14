package com.weicoder.web.util;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.EmptyUtil;

/**
 * Session一些相关操作类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-01-24
 */
public final class SessionUtil {
	/**
	 * 销毁session
	 * @param session 用户session
	 * @return 始终返回null
	 */
	public static void close(HttpSession session) {
		// 判断不为空
		if (!EmptyUtil.isEmpty(session)) {
			try {
				// 获得session中的所有属性集合
				Enumeration<?> e = session.getAttributeNames();
				// 判断属性集合不为空
				if (!EmptyUtil.isEmpty(e)) {
					// 循环删除属性
					while (e.hasMoreElements()) {
						// 删除
						session.removeAttribute(Conversion.toString(e.nextElement()));
					}
				}
				// 销毁Session
				session.invalidate();
			} catch (Exception e) {}
		}
	}

	/**
	 * 获得session的属性 如果没有返回defaultValue
	 * @param session ServletRequest
	 * @param key 属性值
	 * @return value
	 */
	public static Object getAttribute(HttpSession session, String key) {
		return getAttribute(session, key, null);
	}

	/**
	 * 获得session的属性 如果没有返回defaultValue
	 * @param session ServletRequest
	 * @param key 属性值
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static <E> E getAttribute(HttpSession session, String key, E defaultValue) {
		return EmptyUtil.isEmpty(session) ? defaultValue : (E) session.getAttribute(key);
	}

	/**
	 * 设置session的属性
	 * @param session ServletRequest
	 * @param key 属性值
	 * @param value 属性值
	 * @param maxAge 保存多少秒
	 */
	public static void setAttribute(HttpSession session, String key, Object value, int maxAge) {
		if (!EmptyUtil.isEmpty(session)) {
			session.setMaxInactiveInterval(maxAge);
			session.setAttribute(key, value);
		}
	}

	/**
	 * 删除session的属性
	 * @param session ServletRequest
	 * @param key 属性值
	 */
	public static void removeAttribute(HttpSession session, String key) {
		if (!EmptyUtil.isEmpty(session)) {
			session.removeAttribute(key);
		}
	}

	/**
	 * 获得ServletContext
	 * @param session HttpSession
	 * @return ServletContext
	 */
	public static ServletContext getServletContext(HttpSession session) {
		return EmptyUtil.isEmpty(session) ? null : session.getServletContext();
	}

	/**
	 * 私有构造
	 */
	private SessionUtil() {}
}
