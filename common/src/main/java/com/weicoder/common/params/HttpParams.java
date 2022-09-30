package com.weicoder.common.params;

import com.weicoder.common.constants.C;

/**
 * 读取配置
 * 
 * @author WD
 */
public sealed class HttpParams permits P.H {
	/** http连接超时时间 */
	public final static int		CONNECT_TIMEOUT	= P.getInt("http.connect.timeout", 3000);
	/** http读取超时时间 */
	public final static int		READ_TIMEOUT	= P.getInt("http.read.timeout", 10000);
	/** HTTP 超时 */
	public final static int		TIMEOUT			= P.getInt("http.timeout", 5000);
	/** HTTP 最大连接池 */
	public final static int		MAX				= P.getInt("http.max", C.O.CPU_NUM * 10);
	/** HTTP 基础URL */
	public final static String	BASE_URL		= P.getString("base.url", "http://m.weicoder.com/");
}
