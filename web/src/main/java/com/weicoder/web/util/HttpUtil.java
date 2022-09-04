package com.weicoder.web.util;

import java.io.IOException;
import java.io.OutputStream; 
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.FileConstants;
import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.StringConstants;
 
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.StringUtil;
import com.weicoder.common.util.U; 

/**
 * HTTP一些相关操作类
 * 
 * @author WD
 */
public final class HttpUtil {
	/**
	 * 根据url和Map获得URL提交 连接 如果值为空不连接 对Key进行排序
	 * 
	 * @param  url        要提交的url
	 * @param  parameters 参数列表
	 * @return            参数
	 */
	public static String toUrl(String url, Map<String, String> parameters) {
		return StringUtil.add(url, "?", toParameters(parameters));
	}
	
	/**
	 * 根据传进来的url判断ContentType
	 * @param url URL路径
	 * @return ContentType
	 */
	public static String getContentType(String url) {
		// 如果有?把?去掉 并获得后缀
		String suf = StringUtil
				.subStringLast(StringUtil.subStringEnd(url, StringConstants.QUESTION_MARK),
						StringConstants.POINT)
				.toLowerCase();
		// 判断是什么类型的文件
		if (FileConstants.SUFFIX_JS.equals(suf)) {
			return HttpConstants.CONTENT_TYPE_JS;
		} else if (FileConstants.SUFFIX_CSS.equals(suf)) {
			return HttpConstants.CONTENT_TYPE_CSS;
		} else if (FileConstants.SUFFIX_HTML.equals(suf)) {
			return HttpConstants.CONTENT_TYPE_HTML;
		} else if (FileConstants.SUFFIX_TXT.equals(suf)) {
			return HttpConstants.CONTENT_TYPE_TXT;
		} else if (FileConstants.SUFFIX_GIF.equals(suf)) {
			return HttpConstants.CONTENT_TYPE_GIF;
		} else if (FileConstants.SUFFIX_JPG.equals(suf) || FileConstants.SUFFIX_JPEG.equals(suf)) {
			return HttpConstants.CONTENT_TYPE_JPEG;
		} else if (FileConstants.SUFFIX_PNG.equals(suf)) {
			return HttpConstants.CONTENT_TYPE_PNG;
		} else {
			return StringConstants.EMPTY;
		}
	}

	/**
	 * 根据url和Map获得表单提交 连接 如果值为空不连接 对Key进行排序
	 * 
	 * @param  url        要提交的url
	 * @param  parameters 参数列表
	 * @param  charset    编码
	 * @return            参数
	 */
	public static String toForm(String url, Map<String, String> parameters, String charset) {
		// 声明表单字符串缓冲
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=");
		sb.append(charset);
		sb.append("\"></head>");
		sb.append("<form id=\"paysubmit\" name=\"paysubmit\" action=\"");
		sb.append(url);
		sb.append("\" method=\"POST\">");
		// 设置参数
		parameters.forEach((k, v) -> {
			sb.append("<input type=\"hidden\" name=\"");
			sb.append(k);
			sb.append("\" value=\"");
			sb.append(v);
			sb.append("\"/>");
		});
		// submit按钮控件请不要含有name属性
		sb.append("<input type=\"submit\" value=\"确认\" style=\"display:none;\"></form>");
		sb.append("<script>document.forms['paysubmit'].submit();</script>");
		sb.append("<body></body></html>");
		// 返回字节串
		return sb.toString();
	}

	/**
	 * 判断字符串是否是HTTP请求
	 * 
	 * @param  str 字符串
	 * @return     是否
	 */
	public static boolean isHttp(String str) {
		return U.E.isEmpty(str) ? false : str.startsWith("http://") || str.startsWith("https://");
	}

	/**
	 * 根据Map获得URL后的参数 连接 如果值为空不连接 对Key进行排序
	 * 
	 * @param  map 参数列表
	 * @return     参数
	 */
	public static String toParameters(Map<String, String> map) {
		// 返回组合后的字符串
		return StringUtil.toParameters(map);
	}

	/**
	 * 设置客户端缓存过期时间 Header.
	 * 
	 * @param response       HttpServletResponse
	 * @param expiresSeconds 过期时间
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		// Http 1.0 header
		response.setDateHeader(HttpConstants.HEADER_KEY_EXPIRES,
				System.currentTimeMillis() + expiresSeconds * DateConstants.TIME_SECOND);
		// Http 1.1 header
		response.setHeader(HttpConstants.HEADER_KEY_CACHE_CONTROL, "max-age=" + expiresSeconds);
	}

	/**
	 * 设置客户端无缓存Header.
	 * 
	 * @param response HttpServletResponse
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader(HttpConstants.HEADER_KEY_EXPIRES, 0);
		// Http 1.1 header
		response.setHeader(HttpConstants.HEADER_KEY_CACHE_CONTROL, HttpConstants.HEADER_VAL_NO_CACHE);
	}

	/**
	 * 设置LastModified Header.
	 * 
	 * @param response         HttpServletResponse
	 * @param lastModifiedDate LastModified时间
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header
	 * 
	 * @param response HttpServletResponse
	 * @param etag     Etag
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改. 如果无修改, checkIfModify返回false,设置304 not modify status.
	 * 
	 * @param  request      HttpServletRequest
	 * @param  response     HttpServletResponse
	 * @param  lastModified 内容的最后修改时间.
	 * @return              true false
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		// 获得 If-Modified-Since时间
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		// 判断时间
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + DateConstants.TIME_SECOND)) {
			// 设置没修改
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			// 返回false
			return false;
		}
		// 返回true
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效. 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param  request  HttpServletRequest
	 * @param  response HttpServletResponse
	 * @param  etag     内容的ETag
	 * @return          true false
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		// 获得If-None-Match
		String headerValue = request.getHeader("If-None-Match");
		if (U.E.isNotEmpty(headerValue)) {
			// 声明Boolean变量
			boolean conditionSatisfied = false;
			// 判断headerValue不等于 *
			if (!"*".equals(headerValue)) {
				// 声明StringTokenizer
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, StringConstants.COMMA);
				// 循环处理
				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					// 获得Token
					String currentToken = commaTokenizer.nextToken();
					// 判断Token
					if (currentToken.trim().equals(etag))
						// 返回true
						conditionSatisfied = true;
				}
			} else
				// 返回true
				conditionSatisfied = true;
			// 判断
			if (conditionSatisfied) {
				// 设置无修改
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				// 修改etag
				setEtag(response, etag);
				// 返回false
				return false;
			}
		}
		// 返回true
		return true;
	}

	/**
	 * 检查浏览器客户端是否支持gzip编码.
	 * 
	 * @param  request HttpServletRequest
	 * @return         true false
	 */
	public static boolean checkAccetptGzip(HttpServletRequest request) {
		// 获得Accept-Encoding
		String acceptEncoding = request.getHeader("Accept-Encoding");
		// 判断acceptEncoding是否包含 gzip
		return StringUtil.contains(acceptEncoding, "gzip");
	}

	/**
	 * 设置Gzip Header并返回GZIPOutputStream.
	 * 
	 * @param  response HttpServletResponse
	 * @return          OutputStream
	 */
	public static OutputStream buildGzipOutputStream(HttpServletResponse response) {
		// 设置gizp
		response.setHeader("Content-Encoding", "gzip");
		// 设置Encoding
		response.setHeader("Vary", "Accept-Encoding");
		try {
			// 返回流
			return new GZIPOutputStream(response.getOutputStream());
		} catch (IOException e) {
			// 记录日志
			Logs.error(e);
			// 返回null
			return null;
		}
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param response HttpServletResponse
	 * @param fileName 下载后的文件名
	 */
	public static void setDownloadableHeader(HttpServletResponse response, String fileName) {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	}

	private HttpUtil() {
	}
}