package com.weicoder.common.http;

import java.util.Map;

import com.weicoder.common.http.factory.HttpFactory;

/**
 * http客户端通讯
 * 
 * @author WD
 */
public class HttpEngine {
	// 全局Http
	private final static Http HTTP = HttpFactory.getHttp();

	/**
	 * 使用get提交url
	 * 
	 * @param url 网址
	 * @return 返回的结果
	 */
	public static byte[] download(String url) {
		return HTTP.download(url);
	}

	/**
	 * 使用get提交url
	 * 
	 * @param url 网址
	 * @return 返回的结果
	 */
	public static String get(String url) {
		return HTTP.get(url, null);
	}

	/**
	 * 使用get提交url
	 * 
	 * @param url    网址
	 * @param header http头列表
	 * @return 返回的结果
	 */
	public static String get(String url, Map<String, Object> header) {
		return HTTP.get(url, header);
	}

	/**
	 * 使用get提交url
	 * 
	 * @param url    网址
	 * @param header http头列表
	 * @return 返回的结果
	 */
	public static byte[] download(String url, Map<String, Object> header) {
		return HTTP.download(url, header);
	}

	/**
	 * 模拟post提交 定制提交 参数对象与提交参数相同 返回结果为json对象
	 * 
	 * @param url  post提交地址
	 * @param data 提交参数
	 * @param c    返回类类型
	 * @return 提交结果
	 */
	public static String post(String url, Object data) {
		return HTTP.post(url, data);
	}

	/**
	 * 使用post提交url
	 * 
	 * @param url  网址
	 * @param data 参数
	 * @return 返回的结果
	 */
	public static String post(String url, Map<String, String> data) {
		return HTTP.post(url, data);
	}
}
