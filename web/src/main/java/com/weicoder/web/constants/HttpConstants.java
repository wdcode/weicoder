package com.weicoder.web.constants;

/**
 * HTTP常量
 * @author WD
 * @since JDK7
 * @version 1.0 2009-11-21
 */
public final class HttpConstants {
	/**
	 * HTTP http://
	 */
	public final static String	HTTP							= "http://";
	/**
	 * HTTPS https://
	 */
	public final static String	HTTPS							= "https://";
	/**
	 * FTP ftp://
	 */
	public final static String	FTP								= "ftp://";
	/**
	 * HTTP提交方式 GET
	 */
	public final static String	METHOD_GET						= "GET";
	/**
	 * HTTP提交方式 POST
	 */
	public final static String	METHOD_POST						= "POST";
	/**
	 * ContentType img格式
	 */
	public final static String	CONTENT_TYPE_JPEG				= "image/jpeg";
	/**
	 * ContentType CSV格式
	 */
	public final static String	CONTENT_TYPE_CSV				= "application/vnd.ms-excel";
	/**
	 * ContentType TXT格式
	 */
	public final static String	CONTENT_TYPE_TXT				= "text/plain";
	/**
	 * ContentType VCF格式
	 */
	public final static String	CONTENT_TYPE_VCF				= "text/x-vcard";
	/**
	 * ContentType js格式
	 */
	public final static String	CONTENT_TYPE_JS					= "text/javascript";
	/**
	 * ContentType css格式
	 */
	public final static String	CONTENT_TYPE_CSS				= "text/css";
	/**
	 * ContentType html格式
	 */
	public final static String	CONTENT_TYPE_HTML				= "text/html";
	/**
	 * ContentType gif格式
	 */
	public final static String	CONTENT_TYPE_GIF				= "image/gif";
	/**
	 * ContentType png格式
	 */
	public final static String	CONTENT_TYPE_PNG				= "image/png";
	/**
	 * ContentType json格式
	 */
	public final static String	CONTENT_TYPE_JSON				= "application/json";
	/**
	 * ContentType xml格式
	 */
	public final static String	CONTENT_TYPE_XML				= "text/xml";
	/**
	 * XHTML文档 .xhtml
	 */
	public final static String	CONTENT_TYPE_XHTML				= "application/xhtml+xml";
	/**
	 * AVI文件 .avi
	 */
	public final static String	CONTENT_TYPE_AVI				= "video/x-msvideo";
	/**
	 * GZIP文件 .gz
	 */
	public final static String	CONTENT_TYPE_GZIP				= "application/x-gzip";
	/**
	 * TAR文件 .tar
	 */
	public final static String	CONTENT_TYPE_TAR				= "application/x-tar";
	/**
	 * 任意的二进制数据
	 */
	public final static String	CONTENT_TYPE_BYE				= "application/octet-stream";
	/**
	 * 上传文件
	 */
	public final static String	CONTENT_TYPE_FILE				= "multipart/form-data";

	/**
	 * Header 获得 User-Agent "User-Agent"
	 */
	public final static String	HEADER_USER_AGENT				= "User-Agent";
	/**
	 * Header 获得 accept-language "accept-language"
	 */
	public final static String	HEADER_ACCEPT_LANGUAGE			= "accept-language";
	/**
	 * Header 获得 IP "X-Forwarded-For"
	 */
	public final static String	HEADER_IP_X_FORWARDED_FOR		= "X-Forwarded-For";
	/**
	 * Header 获得 IP "X-Real-IP"
	 */
	public final static String	HEADER_IP_X_REAL_IP				= "X-Real-IP";
	/**
	 * Header 清除缓存 KEY "Pragma"
	 */
	public final static String	HEADER_KEY_PRAGMA				= "Pragma";
	/**
	 * Header 清除缓存 KEY "Expires"
	 */
	public final static String	HEADER_KEY_EXPIRES				= "Expires";
	/**
	 * HTTP头 KEY "Cache-Control"
	 */
	public final static String	HEADER_KEY_CACHE_CONTROL		= "Cache-Control";
	/**
	 * HTTP头 KEY "Last-Modified"
	 */
	public final static String	HEADER_KEY_LAST_MODIFIED		= "Last-Modified";
	/**
	 * HTTP头 KEY "If-Modified-Since"
	 */
	public final static String	HEADER_KEY_IF_MODIFIED_SINCE	= "If-Modified-Since";
	/**
	 * HTTP头 KEY "If-None-Match"
	 */
	public final static String	HEADER_KEY_IF_NONE_MATCH		= "If-None-Match";
	/**
	 * HTTP头 KEY "ETag"
	 */
	public final static String	HEADER_KEY_ETAG					= "ETag";
	/**
	 * HTTP头 KEY "Accept-Encoding"
	 */
	public final static String	HEADER_KEY_ACCEPT_ENCODING		= "Accept-Encoding";
	/**
	 * HTTP头 VAL "no-cache"
	 */
	public final static String	HEADER_VAL_NO_CACHE				= "no-cache";
	/**
	 * HTTP头 VAL "max-age="
	 */
	public final static String	HEADER_VAL_MAX_AGE				= "max-age=";

	/**
	 * 私有构造禁止外部实例化
	 */
	private HttpConstants() {}
}
