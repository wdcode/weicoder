package com.weicoder.common.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.common.zip.ZipEngine;

/**
 * http客户端通讯
 * @author WD
 */
public final class HttpEngine {

	/**
	 * 使用get提交url
	 * @param url 网址
	 * @return 返回的结果
	 */
	public static byte[] download(String url) {
		HttpURLConnection conn = null;
		try {
			// 获得连接
			conn = getConnection(url);
			// 设置为post方式
			conn.setRequestMethod("GET");
			// 设置超时
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			// 连接
			conn.connect();
			// 使用GZIP一般服务器支持解压获得的流 然后转成字符串 一般为UTF-8
			return ZipEngine.GZIP.extract(IOUtil.read(conn.getInputStream()));
		} catch (IOException e) {
			Logs.error(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 使用get提交url
	 * @param url 网址
	 * @return 返回的结果
	 */
	public static String get(String url) {
		// 使用GZIP一般服务器支持解压获得的流 然后转成字符串 一般为UTF-8
		return StringUtil.toString(download(url));
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
			conn.setRequestMethod("POST");
			// 设置超时
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			// 设置允许Input
			conn.setDoInput(true);
			// 设置允许output
			conn.setDoOutput(true);
			// 连接
			conn.connect();
			// 判断有参数提交
			if (!EmptyUtil.isEmpty(data)) {
				// 声明字符串缓存
				StringBuilder sb = new StringBuilder();
				// 循环参数
				for (Map.Entry<String, Object> e : data.entrySet()) {
					// 添加条件
					sb.append(e.getKey()).append("=").append(e.getValue());
					// 添加间隔符
					sb.append("&");
				}
				// 写数据流
				IOUtil.write(conn.getOutputStream(), sb.substring(0, sb.length() - 1));
			}
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
			conn.addRequestProperty(HttpConstants.ACCEPT_LANGUAGE_KEY,
					HttpConstants.ACCEPT_LANGUAGE_VAL);
			conn.addRequestProperty("Accept-Encoding", "gzip,deflate");
			conn.addRequestProperty(HttpConstants.ACCEPT_CHARSET_KEY,
					HttpConstants.ACCEPT_CHARSET_VAL);
			conn.addRequestProperty(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.CONTENT_TYPE_VAL);
			conn.addRequestProperty("Connection", "Keep-Alive");
		} catch (Exception e) {
			Logs.error(e);
		}
		// 返回连接connection
		return conn;
	}

	private HttpEngine() {}
}
