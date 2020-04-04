package com.weicoder.common.constants;

/**
 * HTTP常量
 * 
 * @author WD
 */
public class HttpConstants {
	/** Header 清除缓存 KEY "Expires" */
	public final static String HEADER_KEY_EXPIRES           = "Expires";
	/** HTTP头 KEY "Cache-Control" */
	public final static String HEADER_KEY_CACHE_CONTROL     = "Cache-Control";
	/** HTTP头 VAL "no-cache" */
	public final static String HEADER_VAL_NO_CACHE          = "no-cache";
	/** 头 User-Agent 信息 KEY */
	public final static String USER_AGENT_KEY               = "User-Agent";
	/** 头 User-Agent 信息 VALUE */
	public final static String USER_AGENT_VAL               = "Mozilla/5.0 (Windows; U; Windows NT 5.1; nl; rv:1.8.1.13) Gecko/20080311 Firefox/2.0.0.13";
	/** 头 Accept 信息 KEY */
	public final static String ACCEPT_KEY                   = "Accept";
	/** 头 Accept 信息 VALUE */
	public final static String ACCEPT_VAL                   = "text/xml,text/javascript,application/json,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5";
	/** 头Accept-Language信息 KEY */
	public final static String ACCEPT_LANGUAGE_KEY          = "Accept-Language";
	/** 头Accept-Language信息 VALUE */
	public final static String ACCEPT_LANGUAGE_VAL          = "zh-cn,zh;q=0.5";
	/** 头Accept-Charset信息 KEY */
	public final static String ACCEPT_CHARSET_KEY           = "Accept-Charset";
	/** 头Accept-Charset信息 VALUE */
	public final static String ACCEPT_CHARSET_VAL           = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
	/** 头Content-Type信息 KEY */
	public final static String CONTENT_TYPE_KEY             = "Content-Type";
	/** 头Content-Type信息 VALUE */
	public final static String CONTENT_TYPE_VAL             = "application/x-www-form-urlencoded";
	/** 头Content-Type信息 json ;charset=utf-8 */
	public final static String CONTENT_TYPE_JSON            = "application/json";
	/** 头Content-Type信息 上传 */
	public final static String CONTENT_TYPE_UPLOAD          = "multipart/form-data";
	/** http提交方法get */
	public final static String METHOD_GET                   = "GET";
	/** http提交方法post */
	public final static String METHOD_POST                  = "POST";
	/** ContentType CSV格式 */
	public final static String CONTENT_TYPE_CSV             = "application/vnd.ms-excel";
	/** ContentType TXT格式 */
	public final static String CONTENT_TYPE_TXT             = "text/plain";
	/** ContentType VCF格式 */
	public final static String CONTENT_TYPE_VCF             = "text/x-vcard";
	/** ContentType js格式 */
	public final static String CONTENT_TYPE_JS              = "text/javascript";
	/** ContentType css格式 */
	public final static String CONTENT_TYPE_CSS             = "text/css";
	/** ContentType html格式 */
	public final static String CONTENT_TYPE_HTML            = "text/html";
	/** ContentType gif格式 */
	public final static String CONTENT_TYPE_GIF             = "image/gif";
	/** ContentType png格式 */
	public final static String CONTENT_TYPE_PNG             = "image/png";
	/** ContentType xml格式 */
	public final static String CONTENT_TYPE_XML             = "text/xml";
	/** XHTML文档 .xhtml */
	public final static String CONTENT_TYPE_XHTML           = "application/xhtml+xml";
	/** AVI文件 .avi */
	public final static String CONTENT_TYPE_AVI             = "video/x-msvideo";
	/** GZIP文件 .gz */
	public final static String CONTENT_TYPE_GZIP            = "application/x-gzip";
	/** TAR文件 .tar */
	public final static String CONTENT_TYPE_TAR             = "application/x-tar";
	/** 任意的二进制数据 */
	public final static String CONTENT_TYPE_BYE             = "application/octet-stream";
	/** Header 获得 User-Agent "User-Agent" */
	public final static String HEADER_USER_AGENT            = "User-Agent";
	/** Header 获得 accept-language "accept-language" */
	public final static String HEADER_ACCEPT_LANGUAGE       = "accept-language";
	/** Header 获得 IP "X-Forwarded-For" */
	public final static String HEADER_IP_X_FORWARDED_FOR    = "X-Forwarded-For";
	/** Header 获得 IP "X-Real-IP" */
	public final static String HEADER_IP_X_REAL_IP          = "X-Real-IP";
	/** Header 清除缓存 KEY "Pragma" */
	public final static String HEADER_KEY_PRAGMA            = "Pragma";
	/** HTTP头 KEY "Last-Modified" */
	public final static String HEADER_KEY_LAST_MODIFIED     = "Last-Modified";
	/** HTTP头 KEY "If-Modified-Since" */
	public final static String HEADER_KEY_IF_MODIFIED_SINCE = "If-Modified-Since";
	/** HTTP头 KEY "If-None-Match" */
	public final static String HEADER_KEY_IF_NONE_MATCH     = "If-None-Match";
	/** HTTP头 KEY "ETag" */
	public final static String HEADER_KEY_ETAG              = "ETag";
	/** HTTP头 KEY "Accept-Encoding" */
	public final static String HEADER_KEY_ACCEPT_ENCODING   = "Accept-Encoding";
	/** HTTP头 VAL "max-age=" */
	public final static String HEADER_VAL_MAX_AGE           = "max-age=";
	/** ContentType img格式 */
	public final static String CONTENT_TYPE_JPEG            = "image/jpeg";
	/** HTTP http:// */
	public final static String HTTP                         = "http://";
	/** HTTPS https:// */
	public final static String HTTPS                        = "https://";
	/** FTP ftp:// */
	public final static String FTP                          = "ftp://";
	/** 上传文件 */
	public final static String CONTENT_TYPE_FILE            = "multipart/form-data";
	/** HTML格式 text/html; */
	public final static String TEXT_HTML                    = "text/html;";
	/** HTML格式 text/html; charset=UTF-8 */
	public final static String TEXT_HTML_UTF_8              = "text/html; charset=UTF-8";
	/** HTML格式 text/html; charset=GBK */
	public final static String TEXT_HTML_GBK                = "text/html; charset=GBK";
	/** "&"的转义字符"&amp;" */
	public final static String ESC_AMP                      = "&amp;";
	/** "<"的转义字符"&lt;" */
	public final static String ESC_LT                       = "&lt;";
	/** ">"的转义字符"&gt;" */
	public final static String ESC_GT                       = "&gt;";
	/** "\""的转义字符"&quot;" */
	public final static String ESC_QUOT                     = "&quot;";
}
