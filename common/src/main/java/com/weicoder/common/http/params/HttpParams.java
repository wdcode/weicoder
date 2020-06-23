package com.weicoder.common.http.params;

import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.params.Params;

/**
 * 读取配置
 * 
 * @author WD
 */
public final class HttpParams {
	/** HTTP 超时 */
	public final static int HTTP_TIMEOUT = Params.getInt("http.timeout", 5000);
	/** HTTP 最大连接池 */
	public final static int HTTP_MAX = Params.getInt("http.max", SystemConstants.CPU_NUM * 10);
	/** HTTP 基础URL */
	public final static String BASE_URL = Params.getString("base.url", "http://m.weicoder.com/");

	private HttpParams() {
	}
}
