package com.weicoder.web.params;

import com.weicoder.common.params.Params;

/**
 * HTTP参数
 * @author WD
 * @since JDK6
 * @version 1.0 2012-12-21
 */
public final class HttpParams {
	/**
	 * HTTP 超时时间
	 */
	public final static int		TIMEOUT		= Params.getInt("http.timeout", 2000);
	/**
	 * HTTP 连接池
	 */
	public final static int		POOL		= Params.getInt("http.pool", 50);
	/**
	 * HTTP 缓存
	 */
	public final static int		BUFFER		= Params.getInt("http.buffer", 10024);
	/**
	 * http解析包
	 */
	public final static String	PARSE	= Params.getString("http.parse", "apache4");

	private HttpParams() {}
}
