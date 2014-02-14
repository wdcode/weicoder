package com.weicoder.web.http;

import java.util.Map;

import com.weicoder.web.http.factory.HttpFactory;

/**
 * HTTP处理器 使用默认工厂对象
 * @author WD
 * @since JDK7
 * @version 1.0 2012-08-28
 */
public final class HttpEngine {
	// HTTP接口
	private final static Http	HTTP;

	/**
	 * 私有构造
	 */
	static {
		HTTP = HttpFactory.getHttp();
	}

	/**
	 * 下载二进制文件
	 * @param url get提交地址
	 * @return 二进制数据
	 */
	public static byte[] download(String url) {
		return HTTP.download(url);
	}

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @return InputStream 提交后的流
	 */
	public static String get(String url) {
		return HTTP.get(url);
	}

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @param encoding 提交参数的编码格式
	 * @return InputStream 提交后的流
	 */
	public static String get(String url, String encoding) {
		return HTTP.get(url, encoding);
	}

	/**
	 * 模拟post提交 默认使用UTF-8格式
	 * @param url post提交地址
	 * @param data 提交参数
	 * @param encoding 提交参数的编码格式
	 * @return byte[] 提交后的流
	 */
	public static String post(String url, Map<String, Object> data, String encoding) {
		return HTTP.post(url, data, encoding);
	}

	/**
	 * 模拟post提交 默认使用UTF-8格式
	 * @param url post提交地址
	 * @param data 提交参数
	 * @return byte[] 提交后的流
	 */
	public static String post(String url, Map<String, Object> data) {
		return HTTP.post(url, data);
	}

	/**
	 * 私有构造
	 */
	private HttpEngine() {}
}
