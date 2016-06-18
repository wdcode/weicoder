package com.weicoder.web.params;

import java.util.Set;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.params.Params;

/**
 * Servlet读取配置
 * @author WD
 */
public final class ServletParams {
	/** Servlet服务器开关 */
	public final static boolean		POWER	= Params.getBoolean("servlet.power", false);
	/** 是否支持get */
	public final static boolean		GET		= Params.getBoolean("servlet.get", true);
	/** IP鉴权 允许的IP访问 */
	public final static Set<String>	IPS		= Sets.getSet(Params.getStringArray("servlet.ips", ArrayConstants.STRING_EMPTY));

	private ServletParams() {
	}
}
