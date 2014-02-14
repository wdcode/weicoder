package com.weicoder.web.http;

import java.util.List;
import java.util.Map;

import com.weicoder.common.interfaces.Close;

/**
 * 实现HTTP模拟浏览器提交的接口 使用HttpFactory获得实例
 * @see com.weicoder.web.http.factory.HttpFactory
 * @author WD
 * @since JDK7
 * @version 1.0 2009-06-03
 */
public interface Http extends Close {
	/**
	 * 获得当前的URL
	 * @return URL地址
	 */
	String getCurrentURL();

	/**
	 * 获得所有Cookie列表
	 * @return Cookie列表
	 */
	List<Map<String, String>> getCookies();

	/**
	 * 添加Cookie
	 * @param name Cookie名
	 * @param value Cookie值
	 */
	void addCookie(String name, String value);

	/**
	 * 根据name获得Cookie
	 * @param name cookie名
	 * @return Cookie 如果没有找到返回null
	 */
	Map<String, String> getCookie(String name);

	/**
	 * 根据name获得Cookie值
	 * @param name cookie名
	 * @return Cookie值 如果没有找到返回""
	 */
	String getCookieValue(String name);

	/**
	 * 下载二进制文件
	 * @param url get提交地址
	 * @return 二进制数据
	 */
	byte[] download(String url);

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @return 响应字符串
	 */
	String get(String url);

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @param encoding 提交参数的编码格式
	 * @return 响应字符串
	 */
	String get(String url, String encoding);

	/**
	 * 模拟post提交 默认使用UTF-8格式
	 * @param url post提交地址
	 * @param data 提交参数
	 * @return byte[] 提交后的流
	 */
	String post(String url, Map<String, Object> data);

	/**
	 * 模拟post提交
	 * @param url post提交地址
	 * @param data 提交参数
	 * @param encoding 编码
	 * @return byte[] 提交后的流
	 */
	String post(String url, Map<String, Object> data, String encoding);

	/**
	 * 模拟post提交
	 * @param url post提交地址
	 * @param data 提交参数
	 * @param encoding 提交参数的编码格式
	 * @param referer referer地址
	 * @return byte[] 提交失败
	 */
	String post(String url, Map<String, Object> data, String encoding, String referer);
}
