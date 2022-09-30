package com.weicoder.web.params;

import com.weicoder.common.params.P;

/**
 * web包参数读取类
 * 
 * @author WD
 */
public final class WebParams {
	private WebParams() {
	}

	/** 是否支持get */
	public final static boolean	GET		= P.getBoolean("servlet.get", true);
	/** 是否支持get */
	public final static boolean	IPS		= P.getBoolean("servlet.ips", false);
	/** 返回结果 */
	public final static boolean	STATE	= P.getBoolean("servlet.state", true);
	/** 域名 */
	public final static String	DOMAIN	= P.getString("domain");
}