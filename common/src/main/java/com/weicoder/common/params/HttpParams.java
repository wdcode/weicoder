package com.weicoder.common.params;

import com.weicoder.common.constants.C;

/**
 * 读取配置
 * 
 * @author WD
 */
public sealed class HttpParams permits P.H {
	/** HTTP 超时 */
	public final static int		HTTP_TIMEOUT	= Params.getInt("http.timeout", 5000);
	/** HTTP 最大连接池 */
	public final static int		HTTP_MAX		= Params.getInt("http.max", C.O.CPU_NUM * 10);
	/** HTTP 基础URL */
	public final static String	BASE_URL		= Params.getString("base.url", "http://m.weicoder.com/");
}
