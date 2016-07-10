package com.weicoder.web.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.json.JsonEngine;
import com.weicoder.common.log.Logs;

/**
 * Response一些相关操作类
 * @author WD
 */
public final class ResponseUtil {
	/**
	 * 写数据到前端
	 * @param response HttpServletResponse
	 * @param str 要写的字符串
	 * @return String
	 */
	public static String write(HttpServletResponse response, String str) {
		return write(response, str, CommonParams.ENCODING);
	}

	/**
	 * 写数据到前端
	 * @param response HttpServletResponse
	 * @param str 要写的字符串
	 * @param charsetName 编码
	 * @return String
	 */
	public static String write(HttpServletResponse response, String str, String charsetName) {
		// 清除缓存
		ResponseUtil.noCache(response);
		// 设置编码
		response.setCharacterEncoding(charsetName);
		// 声明PrintWriter
		PrintWriter pw = null;
		// 写入到前端
		try {
			pw = response.getWriter();
			pw.write(str);
		} catch (Exception e) {
			Logs.error(e);
		} finally {
			CloseUtil.close(pw);
		}
		return str;
	}

	/**
	 * 写数据到前端
	 * @param response HttpServletResponse
	 * @param str 要写的字符串
	 */
	public static void out(HttpServletResponse response, String str) {
		out(response, str, CommonParams.ENCODING);
	}

	/**
	 * 写数据到前端
	 * @param response HttpServletResponse
	 * @param str 要写的字符串
	 * @param charsetName 编码
	 */
	public static void out(HttpServletResponse response, String str, String charsetName) {
		// 清除缓存
		ResponseUtil.noCache(response);
		// 写入到前端
		try {
			IOUtil.write(response.getOutputStream(), str, charsetName, false);
		} catch (IOException e) {}
	}

	/**
	 * 输出数据到客户端方法
	 * @param response HttpServletResponse
	 * @param data 数据对象
	 * @return String
	 */
	public static String json(HttpServletResponse response, Object data) {
		return json(response, StringConstants.EMPTY, data);
	}

	/**
	 * 把对象转换成json
	 * @param response HttpServletResponse
	 * @param callback 跨域用
	 * @param data 对象
	 * @return String
	 */
	public static String json(HttpServletResponse response, String callback, Object data) {
		// 返回数据为空
		if (data == null) {
			return StringConstants.EMPTY;
		}
		// 声明返回字符串
		StringBuilder s = new StringBuilder();
		// 如果callback不为空 填补左括号
		if (!EmptyUtil.isEmpty(callback)) {
			s.append(callback).append(StringConstants.LEFT_PARENTHESIS);
		}
		// 添加json数据
		s.append(data instanceof String || data instanceof Number ? Conversion.toString(data) : JsonEngine.toJson(data));
		// 如果callback不为空 填补右括号
		if (!EmptyUtil.isEmpty(callback)) {
			s.append(StringConstants.RIGHT_PARENTHESIS);
		}
		// 写入前端
		return write(response, s.toString());
	}

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

	private ResponseUtil() {}
}
