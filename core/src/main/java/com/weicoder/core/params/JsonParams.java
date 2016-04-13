package com.weicoder.core.params;

import com.weicoder.common.params.Params;

/**
 * WdCore包所用参数读取类
 * @author WD 
 * @version 1.0 
 */
public final class JsonParams {
	/** JsonFast解析类 */
	public final static String	PARSE_FAST	= "fast";
	/** JsonSmart解析类 */
	public final static String	PARSE_SMART	= "smart";
	/** JsonGson解析类 */
	public final static String	PARSE_GSON	= "gson";
	/** 解析JSON所需要的包 */
	public final static String	PARSE		= Params.getString("json.parse", PARSE_GSON);

	private JsonParams() {}
}
