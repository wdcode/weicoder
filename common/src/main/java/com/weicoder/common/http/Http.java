package com.weicoder.common.http;

import java.util.Map;

/**
 * HTTP client 接口
 * 
 * @author wudi
 */
public interface Http {
	/**
	 * 使用get提交url
	 * 
	 * @param  url 网址
	 * @return     返回的结果
	 */
	byte[] download(String url);

	/**
	 * 使用get提交url
	 * 
	 * @param  url 网址
	 * @return     返回的结果
	 */
	String get(String url);

	/**
	 * 使用get提交url
	 * 
	 * @param  url    网址
	 * @param  header http头列表
	 * @return        返回的结果
	 */
	String get(String url, Map<String, Object> header);

	/**
	 * 使用get提交url
	 * 
	 * @param  url    网址
	 * @param  header http头列表
	 * @return        返回的结果
	 */
	byte[] download(String url, Map<String, Object> header);

	/**
	 * 模拟post提交 定制提交 参数对象与提交参数相同 返回结果为json对象
	 * 
	 * @param  url  post提交地址
	 * @param  data 提交参数
	 * @param  c    返回类类型
	 * @return      提交结果
	 */
	String post(String url, Object data);

	/**
	 * 使用post提交url
	 * 
	 * @param  url  网址
	 * @param  data 参数
	 * @return      返回的结果
	 */
	String post(String url, Map<String, Object> data);

	/**
	 * 使用post提交url
	 * 
	 * @param  url    网址
	 * @param  data   参数
	 * @param  header http头列表
	 * @return        返回的结果
	 */
	String post(String url, Map<String, Object> data, Map<String, Object> header);
}
