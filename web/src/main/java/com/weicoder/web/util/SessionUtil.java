package com.weicoder.web.util;

import java.util.Enumeration;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import com.weicoder.common.lang.W;
import com.weicoder.common.util.U;

/**
 * Session一些相关操作类
 * @author WD
 */
public final class SessionUtil {
	/**
	 * 销毁session
	 * @param session 用户session
	 */
	public static void close(HttpSession session) {
		// 判断不为空
		if (U.E.isNotEmpty(session))
			try {
				// 获得session中的所有属性集合
				Enumeration<?> e = session.getAttributeNames();
				// 判断属性集合不为空
				if (U.E.isNotEmpty(e))
					// 循环删除属性
					while (e.hasMoreElements())
						// 删除
						session.removeAttribute(W.C.toString(e.nextElement()));
				// 销毁Session
				session.invalidate();
			} catch (Exception e) {}
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
	 * @param <E> 泛型
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	public static <E> E getAttribute(HttpSession session, String key, E defaultValue) {
		return U.E.isEmpty(session) ? defaultValue : (E) session.getAttribute(key);
	}

	/**
	 * 设置session的属性
	 * @param session ServletRequest
	 * @param key 属性值
	 * @param value 属性值
	 * @param maxAge 保存多少秒
	 */
	public static void setAttribute(HttpSession session, String key, Object value, int maxAge) {
		if (U.E.isNotEmpty(session)) {
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
		if (U.E.isNotEmpty(session))
			session.removeAttribute(key);
	}

	/**
	 * 获得ServletContext
	 * @param session HttpSession
	 * @return ServletContext
	 */
	public static ServletContext getServletContext(HttpSession session) {
		return U.E.isEmpty(session) ? null : session.getServletContext();
	}

	private SessionUtil() {}
}
