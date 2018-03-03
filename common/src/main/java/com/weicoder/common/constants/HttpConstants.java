package com.weicoder.common.constants;

/**
 * HTTP常量
 * @author WD
 */
public final class HttpConstants {
	/** Header 清除缓存 KEY "Expires" */
	public final static String	HEADER_KEY_EXPIRES			= "Expires";
	/** HTTP头 KEY "Cache-Control" */
	public final static String	HEADER_KEY_CACHE_CONTROL	= "Cache-Control";
	/** HTTP头 VAL "no-cache" */
	public final static String	HEADER_VAL_NO_CACHE			= "no-cache";
	/** 头 User-Agent 信息 KEY */
	public final static String	USER_AGENT_KEY				= "User-Agent";
	/** 头 User-Agent 信息 VALUE */
	public final static String	USER_AGENT_VAL				= "Mozilla/5.0 (Windows; U; Windows NT 5.1; nl; rv:1.8.1.13) Gecko/20080311 Firefox/2.0.0.13";
	/** 头 Accept 信息 KEY */
	public final static String	ACCEPT_KEY					= "Accept";
	/** 头 Accept 信息 VALUE */
	public final static String	ACCEPT_VAL					= "text/xml,text/javascript,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5";
	/** 头Accept-Language信息 KEY */
	public final static String	ACCEPT_LANGUAGE_KEY			= "Accept-Language";
	/** 头Accept-Language信息 VALUE */
	public final static String	ACCEPT_LANGUAGE_VAL			= "zh-cn,zh;q=0.5";
	/** 头Accept-Charset信息 KEY */
	public final static String	ACCEPT_CHARSET_KEY			= "Accept-Charset";
	/** 头Accept-Charset信息 VALUE */
	public final static String	ACCEPT_CHARSET_VAL			= "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
	/** 头Content-Type信息 KEY */
	public final static String	CONTENT_TYPE_KEY			= "Content-Type";
	/** 头Content-Type信息 VALUE */
	public final static String	CONTENT_TYPE_VAL			= "application/x-www-form-urlencoded";
	/** 头Content-Type信息 json */
	public final static String	CONTENT_TYPE_JSON			= "application/json;charset=utf-8";

	private HttpConstants() {}
}
