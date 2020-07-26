package com.weicoder.web.params;

import com.weicoder.common.params.Params;

/**
 * web包参数读取类
 * 
 * @author WD
 */
public final class WebParams {
	/** 是否支持get */
	public final static boolean	GET		= Params.getBoolean("servlet.get", true);
	/** 是否支持get */
	public final static boolean	IPS		= Params.getBoolean("servlet.ips", false);
//	/** 返回结果 */
//	public final static String	RESULT	= Params.getString("servlet.result", "state");
	/** 返回结果 */
	public final static boolean	STATE	= Params.getBoolean("servlet.state", true);
	/** 域名 */
	public final static String	DOMAIN	= Params.getString("domain");

	private WebParams() {
	}
}