package com.weicoder.web.params;

import com.weicoder.common.params.Params;

/**
 * Servlet读取配置
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-07
 */
public final class ServletParams {
	/** Servlet服务器开关 */
	public final static boolean	POWER	= Params.getBoolean("servlet.power", false);
	/** 是否支持get */
	public final static boolean	GET		= Params.getBoolean("servlet.get", true);

	/** 私有构造 */
	private ServletParams() {}
}
