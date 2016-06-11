package com.weicoder.common.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.log.Logs;
import com.weicoder.common.zip.ZipEngine;

/**
 * http客户端通讯
 * @author WD
 */
public final class HttpUtil {

	/**
	 * 使用get提交url
	 * @param url 网址
	 * @return 返回的结果
	 */
	public static String get(String url) {
		HttpURLConnection conn = null;
		try {
			// 获得连接
			conn = getConnection(url);
			// 设置为post方式
			conn.setRequestMethod(HttpConstants.METHOD_GET);
			//设置超时
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			// 连接
			conn.connect();
			// 使用GZIP一般服务器支持解压获得的流 然后转成字符串 一般为UTF-8
			return StringUtil.toString(ZipEngine.GZIP.extract(IOUtil.read(conn.getInputStream())));
		} catch (IOException e) {
			Logs.error(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return StringConstants.EMPTY;
	}

	/**
	 * 使用post提交url
	 * @param url 网址
	 * @param data 参数
	 * @return 返回的结果
	 */
	public static String post(String url, Map<String, Object> data) {
		HttpURLConnection conn = null;
		try {
			// 获得连接
			conn = getConnection(url);
			// 设置为post方式
			conn.setRequestMethod(HttpConstants.METHOD_POST);
			//设置允许Input
			conn.setDoInput(true);
			// 判断有参数提交
			if (!EmptyUtil.isEmpty(data)) {
				// 设置允许output
				conn.setDoOutput(true);
				// 声明字符串缓存
				StringBuilder sb = new StringBuilder();
				// 循环参数
				for (Map.Entry<String, Object> e : data.entrySet()) {
					// 添加条件
					sb.append(e.getKey()).append(StringConstants.EQ).append(e.getValue());
					// 添加间隔符
					sb.append(StringConstants.AMP);
				}
				// 写数据流
				IOUtil.write(conn.getOutputStream(), sb.substring(0, sb.length() - 1));
			}
			//设置超时
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			// 连接
			conn.connect();
			// 使用GZIP一般服务器支持解压获得的流 然后转成字符串 一般为UTF-8
			return StringUtil.toString(ZipEngine.GZIP.extract(IOUtil.read(conn.getInputStream())));
		} catch (IOException e) {
			Logs.error(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return StringConstants.EMPTY;
	}

	/**
	 * 获得连接
	 * @param url URL连接
	 * @return HttpURLConnection
	 */
	private static HttpURLConnection getConnection(String url) {
		HttpURLConnection conn = null;
		try {
			// 获得连接
			conn = (HttpURLConnection) new URL(url).openConnection();
			// // 设置属性
			conn.addRequestProperty(HttpConstants.USER_AGENT_KEY, HttpConstants.USER_AGENT_VAL);
			conn.addRequestProperty(HttpConstants.ACCEPT_KEY, HttpConstants.ACCEPT_VAL);
			conn.addRequestProperty(HttpConstants.ACCEPT_LANGUAGE_KEY, HttpConstants.ACCEPT_LANGUAGE_VAL);
			conn.addRequestProperty(HttpConstants.ACCEPT_ENCODING_KEY, HttpConstants.ACCEPT_ENCODING_VAL);
			conn.addRequestProperty(HttpConstants.ACCEPT_CHARSET_KEY, HttpConstants.ACCEPT_CHARSET_VAL);
			conn.addRequestProperty(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.CONTENT_TYPE_VAL);
			conn.addRequestProperty(HttpConstants.CONNECTION_KEY, HttpConstants.CONNECTION_VAL);
			//			conn.addRequestProperty(HttpConstants.CONTENT_CHARSET, EncodingConstants.UTF_8);
			//			conn.addRequestProperty(REFERER_KEY, referer);
		} catch (Exception e) {
			Logs.error(e);
		}
		// 返回连接connection
		return conn;
	}

	private HttpUtil() {}
}
