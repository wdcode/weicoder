package com.weicoder.common.constants;

/**
 * HTML页面常量
 * @author WD
 * @since JDK7
 * @version 1.0 2009-11-21
 */
public final class HtmlConstants {
	/**
	 * HTML格式 text/html;
	 */
	public final static String	TEXT_HTML		= "text/html;";
	/**
	 * HTML格式 text/html; charset=UTF-8
	 */
	public final static String	TEXT_HTML_UTF_8	= "text/html; charset=UTF-8";
	/**
	 * HTML格式 text/html; charset=GBK
	 */
	public final static String	TEXT_HTML_GBK	= "text/html; charset=GBK";
	/**
	 * "&"的转义字符"&amp;"
	 */
	public final static String	ESC_AMP			= "&amp;";
	/**
	 * "<"的转义字符"&lt;"
	 */
	public final static String	ESC_LT			= "&lt;";
	/**
	 * ">"的转义字符"&gt;"
	 */
	public final static String	ESC_GT			= "&gt;";
	/**
	 * "\""的转义字符"&quot;"
	 */
	public final static String	ESC_QUOT		= "&quot;";

	/**
	 * 私有构造禁止外部实例化
	 */
	private HtmlConstants() {}
}
