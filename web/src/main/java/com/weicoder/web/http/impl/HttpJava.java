package com.weicoder.web.http.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.core.log.Logs;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.constants.HttpConstants;
import com.weicoder.web.http.base.BaseHttp;

// import com.sonalb.net.http.cookie.Client;
// import com.sonalb.net.http.cookie.Cookie;
// import com.sonalb.net.http.cookie.CookieJar;
// import com.sonalb.net.http.cookie.MalformedCookieException;

/**
 * 使用JDK 实现HTTP模拟浏览器提交的实现类
 * @author WD
 * @since JDK7
 * @version 1.0 2011-03-14
 */
public final class HttpJava extends BaseHttp {
	// HttpURLConnection
	private HttpURLConnection	connection;

	// Cookie 客户端
	// private Client cookieClient;

	/**
	 * 构造方法
	 */
	public HttpJava(String encoding) {
		super(encoding);
		// cookieClient = new Client();
	}

	/**
	 * 添加Cookie
	 * @param name Cookie名
	 * @param value Cookie值
	 */
	public void addCookie(String name, String value) {
		// cookieClient.setCookies(connection, new CookieJar(Lists.getList(new Cookie(name, value,
		// connection.getURL()))));
	}

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @param referer referer地址
	 * @return byte[] 提交后的流
	 */
	public byte[] download(String url) {
		try {
			// 获得连接
			HttpURLConnection connection = getConnection(url, null);
			// 连接
			connection.connect();
			// 返回字节数组
			return IOUtil.read(connection.getInputStream());
		} catch (IOException e) {
			Logs.warn(e);
		} finally {
			close();
		}
		// 返回空字节数组
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 模拟post提交
	 * @param url post提交地址
	 * @param data 提交参数
	 * @param encoding 编码
	 * @param referer referer地址
	 * @return byte[] 提交后的流
	 */
	public String post(String url, Map<String, Object> data, String encoding, String referer) {
		try {
			// 获得连接
			HttpURLConnection connection = getConnection(url, referer);
			// 设置允许output
			connection.setDoOutput(true);
			// 设置为post方式
			connection.setRequestMethod(HttpConstants.METHOD_POST);
			// 判断有参数提交
			if (!EmptyUtil.isEmpty(data)) {
				// 声明字符串缓存
				StringBuilder sb = new StringBuilder();
				// 循环参数
				for (Map.Entry<String, Object> e : data.entrySet()) {
					// 添加条件
					sb.append(e.getKey()).append("=").append(e.getValue());
					// 添加间隔符
					sb.append(StringConstants.AMP);
				}
				// 写数据流
				IOUtil.write(connection.getOutputStream(), sb.substring(0, sb.length() - 1), encoding);
			}
			// 连接
			connection.connect();
			// 返回字节数组
			return StringUtil.toString(IOUtil.read(connection.getInputStream()), encoding);
		} catch (IOException e) {
			return e.getMessage();
		} finally {
			close();
		}
	}

	/**
	 * 获得所有Cookie列表
	 * @return Cookie列表
	 */
	public List<Map<String, String>> getCookies() {
		// 获得Cookie列表
		// try {
		// // 获得CookieJar
		// CookieJar cookieJar = cookieClient.getCookies(connection);
		// // 获得Cookie数量
		// int size = cookieJar.size();
		// // /获得CookieList
		// List<Map<String, String>> list = Lists.getList(size);
		// // 循环获得Cookie
		// for (Iterator<Object> it = cookieJar.iterator(); it.hasNext();) {
		// // 获得Cookie
		// Cookie cookie = (Cookie) it.next();
		// // 声明Map
		// Map<String, String> map = Maps.getMap(4);
		// // 设置属性
		// map.put("name", cookie.getName());
		// map.put("value", cookie.getValue());
		// map.put("domain", cookie.getDomain());
		// map.put("path", cookie.getPath());
		// // 添加到列表
		// list.add(map);
		// }
		// // 返回列表
		// return list;
		// } catch (MalformedCookieException e) {
		// Logs.error(e);
		// }
		// 返回空列表
		return Lists.emptyList();
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		close(connection);
	}

	/**
	 * 关闭资源
	 */
	public void close(HttpURLConnection connection) {
		// 判断不为空 关闭资源
		if (!EmptyUtil.isEmpty(connection)) {
			connection.disconnect();
		}
		// 设置为null
		connection = null;
	}

	/**
	 * 获得连接
	 * @param url URL连接
	 * @return HttpURLConnection
	 */
	private HttpURLConnection getConnection(String url, String referer) {
		try {
			// 关闭上次资源
			// close();
			// 获得连接
			connection = (HttpURLConnection) new URL(url).openConnection();
			// // 设置属性
			connection.addRequestProperty(USER_AGENT_KEY, USER_AGENT_VAL);
			connection.addRequestProperty(ACCEPT_KEY, ACCEPT_VAL);
			connection.addRequestProperty(ACCEPT_LANGUAGE_KEY, ACCEPT_LANGUAGE_VAL);
			connection.addRequestProperty(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VAL);
			connection.addRequestProperty(ACCEPT_CHARSET_KEY, ACCEPT_CHARSET_VAL);
			connection.addRequestProperty(CONTENT_TYPE_KEY, CONTENT_TYPE_VAL);
			connection.addRequestProperty(CONNECTION_KEY, CONNECTION_VAL);
			connection.addRequestProperty(CONTENT_CHARSET, encoding);
			connection.addRequestProperty(REFERER_KEY, referer);
		} catch (Exception e) {}
		// 返回连接connection
		return connection;
	}
}
