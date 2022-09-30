package com.weicoder.common.http.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.weicoder.common.constants.C;
import com.weicoder.common.http.Http;
import com.weicoder.common.io.I;
import com.weicoder.common.lang.W;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.P;
import com.weicoder.common.statics.S;
import com.weicoder.common.util.U;
import com.weicoder.common.zip.Zip;

/**
 * http的jdk8实现
 * 
 * @author wudi
 */
public class Jdk8Http implements Http {
	// gzip压缩解压工具
	private Zip gzip = S.Z.get("gzip");

	@Override
	public byte[] download(String url, Map<String, Object> header) {
		HttpURLConnection conn = null;
		try {
			// 获得连接
			conn = getConnection(url);
			// 头不为空，添加头
			if (U.E.isNotEmpty(header))
				for (Map.Entry<String, Object> h : header.entrySet())
					conn.setRequestProperty(h.getKey(), W.C.toString(h.getValue()));

			// 设置为post方式
			conn.setRequestMethod(C.H.METHOD_GET);
			// 连接
			conn.connect();
			// 使用GZIP一般服务器支持解压获得的流 然后转成字符串 一般为UTF-8
			return gzip.extract(I.read(conn.getInputStream()));
		} catch (IOException e) {
			Logs.error(e, "HttpEngine get url={}", url);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return C.A.BYTES_EMPTY;
	}

	@Override
	public String post(String url, Map<String, Object> data, Map<String, Object> header) {
		HttpURLConnection conn = null;
		try {
			// 获得连接
			conn = getConnection(url);
			// 头不为空，添加头
			if (U.E.isNotEmpty(header))
				for (Map.Entry<String, Object> h : header.entrySet())
					conn.setRequestProperty(h.getKey(), W.C.toString(h.getValue()));

			// 设置为post方式
			conn.setRequestMethod(C.H.METHOD_POST);
			// 设置允许Input
			conn.setDoInput(true);
			// 设置允许output
			conn.setDoOutput(true);
			// 连接
			conn.connect();
			// 判断有参数提交
			if (U.E.isNotEmpty(data)) {
				// 声明字符串缓存
				StringBuilder sb = new StringBuilder();
				// 循环参数
				data.entrySet().forEach(e -> {
					// 添加条件与分隔符
					sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
				});
				// 写数据流
				I.write(conn.getOutputStream(), sb.substring(0, sb.length() - 1));
			}
			// 使用GZIP一般服务器支持解压获得的流 然后转成字符串 一般为UTF-8
			String res = U.S.toString(gzip.extract(I.read(conn.getInputStream())));
			Logs.debug("HttpEngine post url={} data={} header={} res={}", url, data, header, res);
			return res;
		} catch (IOException e) {
			Logs.error(e, "HttpEngine post url={} data={} header={}", url, data, header);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return C.S.EMPTY;
	}

	/**
	 * 获得连接
	 * 
	 * @param url URL连接
	 * @return HttpURLConnection
	 */
	private static HttpURLConnection getConnection(String url) {
		HttpURLConnection conn = null;
		try {
			// 获得连接
			conn = (HttpURLConnection) new URL(url).openConnection();
			// // 设置属性
			conn.setRequestProperty(C.H.USER_AGENT_KEY, C.H.USER_AGENT_VAL);
			conn.setRequestProperty(C.H.ACCEPT_KEY, C.H.ACCEPT_VAL);
			conn.setRequestProperty(C.H.ACCEPT_LANGUAGE_KEY, C.H.ACCEPT_LANGUAGE_VAL);
			conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
			conn.setRequestProperty(C.H.ACCEPT_CHARSET_KEY, C.H.ACCEPT_CHARSET_VAL);
			// conn.addRequestProperty(C.H.CONTENT_TYPE_KEY, C.H.CONTENT_TYPE_VAL);
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置超时
			conn.setConnectTimeout(P.H.CONNECT_TIMEOUT);
			conn.setReadTimeout(P.H.READ_TIMEOUT);
		} catch (Exception e) {
			Logs.error(e, "HttpEngine getConnection url={}", url);
		}
		// 返回连接connection
		return conn;
	}

//	@Override
//	public String get(String url, Map<String, Object> header) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
