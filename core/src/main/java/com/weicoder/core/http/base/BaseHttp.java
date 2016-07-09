package com.weicoder.core.http.base;

import java.util.List;
import java.util.Map;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.http.Http;

/**
 * 实现HTTP模拟浏览器提交的抽象实现类
 * @author WD 
 * @version 1.0 
 */
public abstract class BaseHttp implements Http {
	// 头 User-Agent 信息
	protected final static String	USER_AGENT_KEY		= "User-Agent";
	protected final static String	USER_AGENT_VAL		= "Mozilla/5.0 (Windows; U; Windows NT 5.1; nl; rv:1.8.1.13) Gecko/20080311 Firefox/2.0.0.13";
	// 参数KEY
	protected final static String	CONTENT_CHARSET		= "http.protocol.content-charset";
	protected final static String	COOKIE_HEADER		= "http.protocol.single-cookie-header";
	// 头 Accept 信息
	protected final static String	ACCEPT_KEY			= "Accept";
	protected final static String	ACCEPT_VAL			= "text/xml,text/javascript,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5";
	// 头Accept-Language信息
	protected final static String	ACCEPT_LANGUAGE_KEY	= "Accept-Language";
	protected final static String	ACCEPT_LANGUAGE_VAL	= "zh-cn,zh;q=0.5";
	// 头Accept-Encoding信息
	protected final static String	ACCEPT_ENCODING_KEY	= "Accept-Encoding";
	protected final static String	ACCEPT_ENCODING_VAL	= "gzip,deflate";
	// 头Accept-Charset信息
	protected final static String	ACCEPT_CHARSET_KEY	= "Accept-Charset";
	protected final static String	ACCEPT_CHARSET_VAL	= "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
	// 头Content-Type信息
	protected final static String	CONTENT_TYPE_KEY	= "Content-Type";
	protected final static String	CONTENT_TYPE_VAL	= "application/x-www-form-urlencoded";
	// 头Referer
	protected final static String	REFERER_KEY			= "Referer";
	// 头Connection
	protected final static String	CONNECTION_KEY		= "Connection";
	protected final static String	CONNECTION_VAL		= "Keep-Alive";

	// 当前URL
	protected String				currentURL;
	// 编码
	protected String				encoding;

	/**
	 * 构造方法
	 * @param encoding 编码
	 */
	public BaseHttp(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @return InputStream 提交后的流
	 */
	public String get(String url) {
		return get(url, CommonParams.ENCODING);
	}

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @param encoding 提交参数的编码格式
	 * @return InputStream 提交后的流
	 */
	public String get(String url, String encoding) {
		return StringUtil.toString(download(url), encoding);
	}

	/**
	 * 模拟post提交 默认使用UTF-8格式
	 * @param url post提交地址
	 * @param data 提交参数
	 * @return InputStream 提交后的流
	 */
	public String post(String url, Map<String, Object> data) {
		return post(url, data, encoding, null);
	}

	/**
	 * 模拟post提交
	 * @param url post提交地址
	 * @param data 提交参数
	 * @param encoding 编码
	 * @return InputStream 提交后的流
	 */
	public String post(String url, Map<String, Object> data, String encoding) {
		return post(url, data, encoding, null);
	}

	/**
	 * 根据name获得Cookie值
	 * @param name cookie名
	 * @return Cookie值 如果没有找到返回""
	 */
	public String getCookieValue(String name) {
		// 获得Cookie
		Map<String, String> cookie = getCookie(name);
		// 返回值
		return EmptyUtil.isEmpty(cookie) ? StringConstants.EMPTY : cookie.get("value");
	}

	/**
	 * 根据name获得Cookie
	 * @param name cookie名
	 * @return Cookie 如果没有找到返回null
	 */
	public Map<String, String> getCookie(String name) {
		// 判断Cookie Name
		if (EmptyUtil.isEmpty(name)) { return null; }
		// 获得Cookie列表
		List<Map<String, String>> lsCookie = getCookies();
		// 声明Cookie
		Map<String, String> cookie = null;
		// 循环Cookie
		for (int i = 0; i < lsCookie.size(); i++) {
			// 判断Cookie Name
			if (name.equals(lsCookie.get(i).get("name"))) {
				// 获得Cookie
				cookie = lsCookie.get(i);
				break;
			}
		}
		// 返回Cookie
		return cookie;
	}

	/**
	 * 获得当前的URL
	 * @return URL地址
	 */
	public String getCurrentURL() {
		return currentURL;
	}
}
