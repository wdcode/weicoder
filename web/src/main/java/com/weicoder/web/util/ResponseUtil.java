package com.weicoder.web.util;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.U;
import com.weicoder.common.W;
import com.weicoder.common.params.CommonParams;  
import com.weicoder.json.JsonEngine; 

/**
 * Response一些相关操作类
 * 
 * @author WD
 */
public final class ResponseUtil {
	/**
	 * 写数据到前端
	 * 
	 * @param  response HttpServletResponse
	 * @param  str      要写的字符串
	 * @return          String
	 */
	public static String write(HttpServletResponse response, String str) {
		return write(response, str, CommonParams.ENCODING);
	}

	/**
	 * 写数据到前端
	 * 
	 * @param  response    HttpServletResponse
	 * @param  str         要写的字符串
	 * @param  charsetName 编码
	 * @return             String
	 */
	public static String write(HttpServletResponse response, String str, String charsetName) {
		// 清除缓存
		noCache(response);
		// 设置头
		setContentType(response, HttpConstants.CONTENT_TYPE_JSON);
		// "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'"
		response.setHeader("P3P", "CP='CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR'");
		// 设置编码
		response.setCharacterEncoding(charsetName);
		// 写入到前端
		try (PrintWriter pw = response.getWriter()) {
			pw.write(str);
		} catch (Exception e) {
		}
		return str;
	}

	/**
	 * 写数据到前端
	 * 
	 * @param response HttpServletResponse
	 * @param str      要写的字符串
	 */
	public static void out(HttpServletResponse response, String str) {
		out(response, str, CommonParams.ENCODING);
	}

	/**
	 * 写数据到前端
	 * 
	 * @param response    HttpServletResponse
	 * @param str         要写的字符串
	 * @param charsetName 编码
	 */
	public static void out(HttpServletResponse response, String str, String charsetName) {
		// 清除缓存
		ResponseUtil.noCache(response);
		// 写入到前端
		try {
			IOUtil.write(response.getOutputStream(), str, charsetName, false);
		} catch (IOException e) {
		}
	}

	/**
	 * 输出数据到客户端方法
	 * 
	 * @param  response HttpServletResponse
	 * @param  data     数据对象
	 * @return          String
	 */
	public static String json(HttpServletResponse response, Object data) {
		return json(response, StringConstants.EMPTY, data);
	}

	/**
	 * 把对象转换成json
	 * 
	 * @param  response HttpServletResponse
	 * @param  callback 跨域用
	 * @param  data     对象
	 * @return          String
	 */
	public static String json(HttpServletResponse response, String callback, Object data) {
		// 返回数据为空
		if (data == null)
			return StringConstants.EMPTY;
		// 声明返回字符串
		StringBuilder s = new StringBuilder();
		// 如果callback不为空 填补左括号
		if (U.E.isNotEmpty(callback))
			s.append(callback).append("(");
		// 添加json数据
		s.append(data instanceof String || data instanceof Number ? W.C.toString(data) : JsonEngine.toJson(data));
		// 如果callback不为空 填补右括号
		if (U.E.isNotEmpty(callback))
			s.append(")");
		// 写入前端
		return write(response, s.toString());
	}

	/**
	 * 设置页面不缓存
	 * 
	 * @param response Response
	 */
	public static void noCache(HttpServletResponse response) {
		if (U.E.isNotEmpty(response)) {
			response.setHeader("Pragma", HttpConstants.HEADER_VAL_NO_CACHE);
			response.setHeader(HttpConstants.HEADER_KEY_CACHE_CONTROL, HttpConstants.HEADER_VAL_NO_CACHE);
			response.setDateHeader("Expires", 0);
		}
	}

	/**
	 * 设置ContentType类型
	 * 
	 * @param response Response
	 * @param type     ContentType
	 */
	public static void setContentType(ServletResponse response, String type) {
		if (U.E.isNotEmpty(response) && U.E.isNotEmpty(type))
			response.setContentType(type);
	}

	private ResponseUtil() {
	}
}
