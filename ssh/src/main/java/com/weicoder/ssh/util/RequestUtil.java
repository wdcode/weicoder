package com.weicoder.ssh.util;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.IpUtil;
import com.weicoder.common.util.StringUtil;

/**
 * Request一些相关操作类
 * @author WD
 */
public final class RequestUtil {
	/**
	 * 获得客户连接IP
	 * @param request Request
	 * @return 客户连接IP
	 */
	public static String getIp(HttpServletRequest request) {
		// 获得ip列表
		String[] ips = getIps(request);
		// 返回第一个ip
		return EmptyUtil.isEmpty(ips) ? StringConstants.EMPTY : ips[0];
	}

	/**
	 * 获得客户连接IP数组 一般通过代理的可或则所以IP
	 * @param request Request
	 * @return 客户连接IP
	 */
	public static String[] getIps(HttpServletRequest request) {
		// 判断不为空
		if (EmptyUtil.isNotEmpty(request)) {
			// 获得IP
			String ip = request.getHeader("X-Forwarded-For");
			// 判断如果为空继续获得
			if (EmptyUtil.isEmpty(ip))
				// 为空换方法获得
				ip = request.getHeader("X-Real-IP");
			// 判断如果为空继续获得
			if (EmptyUtil.isEmpty(ip))
				// 为空换方法获得
				ip = request.getRemoteAddr();
			// 返回IP
			return EmptyUtil.isEmpty(ip) ? ArrayConstants.STRING_EMPTY : ip.indexOf(StringConstants.COMMA) == -1 ? new String[] { ip } : ip.split(StringConstants.COMMA);
		}
		// 返回""
		return ArrayConstants.STRING_EMPTY;
	}

	/**
	 * 获得域名路径
	 * @param request HttpServletRequest
	 * @return 域名路径
	 */
	public static String getServer(HttpServletRequest request) {
		// 获得path
		String path = request.getServerName();
		// 返回域名路径
		return IpUtil.LOCAL_IP.equals(path) ? IpUtil.getIp() : path;
	}

	/**
	 * 获得域名路径
	 * @param request HttpServletRequest
	 * @return 域名路径
	 */
	public static String getDomain(HttpServletRequest request) {
		// 获得域名
		String domain = StringUtil.add("http://", getServer(request), getBase(request));
		// 返回域名
		return domain.endsWith(StringConstants.BACKSLASH) ? domain : domain + StringConstants.BACKSLASH;
	}

	/**
	 * 获得项目路径
	 * @param request HttpServletRequest
	 * @return 项目路径
	 */
	public static String getBase(HttpServletRequest request) {
		return request.getContextPath();
	}

	/**
	 * 获得程序路径
	 * @param request HttpServletRequest
	 * @param name 文件名
	 * @return 程序路径
	 */
	public String getRealPath(HttpServletRequest request, String name) {
		return request.getServletContext().getRealPath(StringConstants.BACKSLASH) + name;
	}

	/**
	 * 获得request的提交参数 如果没有返回空Map
	 * @param request ServletRequest
	 * @return Map参数
	 */
	public static Map<String, String> getParameters(HttpServletRequest request) {
		// 声明空Map
		Map<String, String> map = Maps.newMap();
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
	 * 获得request的提交头参数 如果没有返回空Map
	 * @param request HttpServletRequest
	 * @return Map参数
	 */
	public static Map<String, String> getHeaders(HttpServletRequest request) {
		// 声明空Map
		Map<String, String> map = Maps.newMap();
		// 获得提交的所以参数名
		for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
			// 获得参数Key
			String key = e.nextElement();
			// 获得参数值并添加到Map中
			map.put(key, request.getHeader(key));
		}
		// 返回Map
		return map;
	}

	/**
	 * 获得request的提交Cookie参数 如果没有返回空Map
	 * @param request HttpServletRequest
	 * @return Map参数
	 */
	public static Map<String, String> getCookies(HttpServletRequest request) {
		// 声明空Map
		Map<String, String> map = Maps.newMap();
		try {
			// 获得提交的所以参数名
			for (Cookie c : request.getCookies())
				// 获得参数值并添加到Map中
				map.put(c.getName(), c.getValue());
		} catch (Exception e) {}
		// 返回Map
		return map;
	}

	/**
	 * 获得request的提交Attribute参数 如果没有返回空Map
	 * @param request HttpServletRequest
	 * @return Map参数
	 */
	public static Map<String, String> getAttributes(HttpServletRequest request) {
		// 声明空Map
		Map<String, String> map = Maps.newMap();
		// 获得提交的所以参数名
		for (Enumeration<String> e = request.getAttributeNames(); e.hasMoreElements();) {
			// 获得参数Key
			String key = e.nextElement();
			// 获得参数值并添加到Map中
			map.put(key, request.getHeader(key));
		}
		// 返回Map
		return map;
	}

	/**
	 * 获得request的提交所有参数 包括cookie和header
	 * @param request HttpServletRequest
	 * @return Map参数
	 */
	public static Map<String, String> getAll(HttpServletRequest request) {
		return Maps.newMaps(getAttributes(request), getCookies(request), getHeaders(request), getParameters(request));
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
	 * @param <E> 泛型
	 * @return value
	 */
	@SuppressWarnings("unchecked")
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
		if (EmptyUtil.isNotEmpty(request))
			request.setAttribute(key, value);
	}

	/**
	 * 获得浏览器类型
	 * @param request ServletRequest
	 * @return 浏览器类型
	 */
	public static String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	/**
	 * 获得浏览器语言
	 * @param request ServletRequest
	 * @return 浏览器语言
	 */
	public static String getLanguage(HttpServletRequest request) {
		return request.getHeader("accept-language");
	}

	private RequestUtil() {}
}
