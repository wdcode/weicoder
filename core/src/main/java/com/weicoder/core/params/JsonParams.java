package com.weicoder.core.params;

import com.weicoder.common.params.Params;

/**
 * WdCore包所用参数读取类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-01-05
 */
public final class JsonParams {
	/**
	 * JsonFast解析类
	 */
	public final static String	PARSE_FAST	= "fast";
	/**
	 * JsonLib解析类
	 */
	public final static String	PARSE_LIB	= "lib";
	/**
	 * JsonSmart解析类
	 */
	public final static String	PARSE_SMART	= "smart";
	/**
	 * JsonGson解析类
	 */
	public final static String	PARSE_GSON	= "gson";
	/**
	 * 解析JSON所需要的包
	 */
	public final static String	PARSE		= Params.getString("json.parse", PARSE_GSON);

	/**
	 * 私有构造
	 */
	private JsonParams() {}
}
