package com.weicoder.web.params;

import com.weicoder.common.params.Params;

/**
 * Servlet读取配置
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-07
 */
public final class ServletParams {
	/** 前缀 */
	private final static String	PREFIX	= "servlet";
	/** Servlet服务器开关 */
	public final static boolean	POWER	= Params.getBoolean(PREFIX + ".power", false);
	/** 是否支持get */
	public final static boolean	GET		= Params.getBoolean(PREFIX + ".get", true);

	/** 私有构造 */
	private ServletParams() {}
}
