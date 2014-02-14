package com.weicoder.web.http.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HostParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.core.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.http.base.BaseHttp;
import com.weicoder.web.params.HttpParams;

/**
 * Apache HttpComponents 3 实现HTTP模拟浏览器提交的实现类
 * @author WD
 * @since JDK7
 * @version 1.0 2011-03-08
 */
public final class HttpApache3 extends BaseHttp {
	// 连接管理
	private MultiThreadedHttpConnectionManager	connectionManager;
	// 客户端
	private HttpClient							client;

	/**
	 * 构造方法
	 */
	public HttpApache3(String encoding) {
		super(encoding);
		// 初始化连接管理
		connectionManager = new MultiThreadedHttpConnectionManager();
		// 获得参数设置
		HttpConnectionManagerParams params = connectionManager.getParams();
		// 设置连接超时时间
		params.setConnectionTimeout(HttpParams.TIMEOUT);
		// 设置数据传送连接时间
		params.setSoTimeout(HttpParams.TIMEOUT);
		// 设置最大连接数
		params.setMaxTotalConnections(HttpParams.POOL);
		// 设置默认最大活动数
		params.setDefaultMaxConnectionsPerHost(HttpParams.POOL);
		// 设置发送缓存数
		params.setSendBufferSize(HttpParams.BUFFER);
		// 设置接受缓存数
		params.setReceiveBufferSize(HttpParams.BUFFER);
		// 设置编码
		params.setParameter(CONTENT_CHARSET, encoding);
		// 设置保存cookie头
		params.setParameter(COOKIE_HEADER, true);
		// 设置参数
		params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		// 设置提交头
		List<Header> headers = Lists.getList(7);
		headers.add(new Header(CONTENT_TYPE_KEY, CONTENT_TYPE_VAL));
		headers.add(new Header(USER_AGENT_KEY, USER_AGENT_VAL));
		headers.add(new Header(ACCEPT_KEY, ACCEPT_VAL));
		headers.add(new Header(CONNECTION_KEY, CONNECTION_VAL));
		headers.add(new Header(ACCEPT_LANGUAGE_KEY, ACCEPT_LANGUAGE_VAL));
		headers.add(new Header(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VAL));
		headers.add(new Header(ACCEPT_CHARSET_KEY, ACCEPT_CHARSET_VAL));
		// 添加头
		params.setParameter(HostParams.DEFAULT_HEADERS, headers);
		// 设置参数
		connectionManager.setParams(params);
		// 初始化客户端
		client = new HttpClient(connectionManager);
	}

	/**
	 * 添加Cookie
	 * @param name Cookie名
	 * @param value Cookie值
	 */
	public void addCookie(String name, String value) {
		client.getState().addCookie(new Cookie(currentURL, name, value));
	}

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @param referer referer地址
	 * @return byte[] 提交后的流
	 */
	public byte[] download(String url) {
		// 声明Get方法
		GetMethod get = null;
		try {
			// 实例化Get方法
			get = new GetMethod(url);
			// 提交获得状态码
			int status = client.executeMethod(get);
			// 更新Url
			updateCurrentUrl(get);
			// 判断状态
			if (status == 302 || status == 301) {
				// 如果是302重定向 那么重新提交
				return download(get.getURI().toString());
			} else if (status == 200 || (status > 200 && status < 300)) {
				// 返回字节数组
				return IOUtil.read(get.getResponseBodyAsStream());
			}
		} catch (Exception e) {
			Logs.warn(e);
		} finally {
			// 关闭连接
			if (get != null) {
				get.releaseConnection();
				get.abort();
			}
		}
		// 返回空字节数组
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 模拟post提交
	 * @param url post提交地址
	 * @param data 提交参数
	 * @param encoding 提交参数的编码格式
	 * @param referer referer地址
	 * @return byte[] 提交失败
	 */
	public String post(String url, Map<String, Object> data, String encoding, String referer) {
		// 声明Post方法
		PostMethod post = null;
		try {
			// 实例化Post方法
			post = new PostMethod(url);
			// 添加头Referer信息
			if (!EmptyUtil.isEmpty(referer)) {
				post.addRequestHeader(REFERER_KEY, referer);
			}
			// 如果参数列表为空 data为空map
			if (!EmptyUtil.isEmpty(data)) {
				// 声明参数列表
				NameValuePair[] param = new NameValuePair[data.size()];
				// 设置标识
				int i = 0;
				// 设置参数
				for (Map.Entry<String, Object> entry : data.entrySet()) {
					// 添加参数
					param[i] = new NameValuePair(entry.getKey(), Conversion.toString(entry.getValue()));
					// 标识自加
					i++;
				}
				// 设置参数与 编码格式
				post.setRequestBody(param);
			}
			// 提交获得状态码
			int status = client.executeMethod(post);
			// 更新Url
			updateCurrentUrl(post);
			// 判断状态
			if (status == 302 || status == 301) {
				// 如果是302重定向 那么重新提交
				return post(post.getURI().toString(), data, referer);
			} else if (status == 200 || (status > 200 && status < 300)) {
				// 返回字节数组
				return StringUtil.toString(IOUtil.read(post.getResponseBodyAsStream()), encoding);
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			// 关闭连接
			if (post != null) {
				post.releaseConnection();
				post.abort();
			}
		}
		// 返回空字节数组
		return StringConstants.EMPTY;
	}

	/**
	 * 获得所有Cookie列表
	 * @return Cookie列表
	 */
	public List<Map<String, String>> getCookies() {
		// 获得Cookie数组
		Cookie[] cookies = client.getState().getCookies();
		// 判断数组不为空
		if (!EmptyUtil.isEmpty(cookies)) {
			// 获得列表
			List<Map<String, String>> list = Lists.getList(cookies.length);
			// 声明Map
			Map<String, String> map = null;
			// 声明Cookie
			Cookie cookie = null;
			// 循环Cookie数组
			for (int i = 0; i < cookies.length; i++) {
				// 获得Cookie
				cookie = cookies[i];
				// 获得Map
				map = Maps.getMap();
				// 添加名称
				map.put("name", cookie.getName());
				// 添加值
				map.put("value", cookie.getValue());
				// 添加到列表中
				list.add(map);
			}
			// 返回列表
			return list;
		}
		// 返回空列表
		return Lists.emptyList();
	}

	/**
	 * 关闭资源方法
	 */
	public void close() {
		// 删除所以连接
		connectionManager.deleteClosedConnections();
		// 释放所以资源
		connectionManager.shutdown();
		// 管理所以静态资源
		MultiThreadedHttpConnectionManager.shutdownAll();
	}

	/**
	 * 更新当前Url
	 * @param method HttpMethod
	 */
	private void updateCurrentUrl(HttpMethod method) {
		// 获得当前Url
		try {
			currentURL = method.getURI().toString();
		} catch (URIException e) {
			Logs.warn(e);
		}
	}
}
