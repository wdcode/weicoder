package com.weicoder.web.util;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.util.EmptyUtil;
import com.weicoder.web.constants.HttpConstants;

/**
 * Response一些相关操作类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-01-10
 */
public final class ResponseUtil {
	/**
	 * 设置页面不缓存
	 * @param response Response
	 */
	public static void noCache(HttpServletResponse response) {
		if (!EmptyUtil.isEmpty(response)) {
			response.setHeader(HttpConstants.HEADER_KEY_PRAGMA, HttpConstants.HEADER_VAL_NO_CACHE);
			response.setHeader(HttpConstants.HEADER_KEY_CACHE_CONTROL, HttpConstants.HEADER_VAL_NO_CACHE);
			response.setDateHeader(HttpConstants.HEADER_KEY_EXPIRES, 0);
		}
	}

	/**
	 * 设置ContentType类型
	 * @param response Response
	 * @param type ContentType
	 */
	public static void setContentType(ServletResponse response, String type) {
		if (!EmptyUtil.isEmpty(response) && !EmptyUtil.isEmpty(type)) {
			response.setContentType(type);
		}
	}

	/**
	 * 私有构造
	 */
	private ResponseUtil() {}
}
