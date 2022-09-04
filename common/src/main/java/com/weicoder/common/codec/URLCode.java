package com.weicoder.common.codec;

import java.net.URLDecoder;
import java.net.URLEncoder;

import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.U;

/**
 * URL基础编码类
 * 
 * @author WD
 */
public class URLCode {
	/**
	 * url编码
	 * 
	 * @param  url 要编码的URL
	 * @return     编码后字符串
	 */
	public static String encode(String url) {
		return encode(url, CommonParams.ENCODING);
	}

	/**
	 * url编码
	 * 
	 * @param  url      要编码的URL
	 * @param  encoding 编码
	 * @return          编码后字符串
	 */
	public static String encode(String url, String encoding) {
		try {
			return U.E.isEmpty(url) ? url : URLEncoder.encode(url, encoding);
		} catch (Exception e) {
			return url;
		}
	}

	/**
	 * url解码
	 * 
	 * @param  url 要解码的URL
	 * @return     解码后字符串
	 */
	public static String decode(String url) {
		return decode(url, CommonParams.ENCODING);
	}

	/**
	 * url解码
	 * 
	 * @param  url      要解码的URL
	 * @param  encoding 解码
	 * @return          解码后字符串
	 */
	public static String decode(String url, String encoding) {
		try {
			return U.E.isEmpty(url) ? url
					: URLDecoder.decode(url, U.E.isEmpty(encoding) ? CommonParams.ENCODING : encoding);
		} catch (Exception e) {
			return url;
		}
	}
}
