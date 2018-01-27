package com.weicoder.common.codec;

import java.net.URLDecoder;
import java.net.URLEncoder;

import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.EmptyUtil;

/**
 * 基础编码类
 * @author WD
 */
public final class URLCode {
	/**
	 * url编码
	 * @param url 要编码的URL
	 * @return 编码后字符串
	 */
	public static String encode(String url) {
		return encode(url, CommonParams.ENCODING);
	}

	/**
	 * url编码
	 * @param url 要编码的URL
	 * @param encoding 编码
	 * @return 编码后字符串
	 */
	public static String encode(String url, String encoding) {
		try {
			Logs.trace("URLCode decode");
			return EmptyUtil.isEmpty(url) ? url : URLEncoder.encode(url, encoding);
		} catch (Exception e) {
			Logs.error(e);
			return url;
		}
	}

	/**
	 * url解码
	 * @param url 要解码的URL
	 * @return 解码后字符串
	 */
	public static String decode(String url) {
		return decode(url, CommonParams.ENCODING);
	}

	/**
	 * url解码
	 * @param url 要解码的URL
	 * @param encoding 解码
	 * @return 解码后字符串
	 */
	public static String decode(String url, String encoding) {
		try {
			Logs.trace("URLCode decode");
			return EmptyUtil.isEmpty(url) ? url : URLDecoder.decode(url, EmptyUtil.isEmpty(encoding) ? CommonParams.ENCODING : encoding);
		} catch (Exception e) {
			Logs.error(e);
			return url;
		}
	}

	private URLCode() {}
}
