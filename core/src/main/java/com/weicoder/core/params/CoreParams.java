package com.weicoder.core.params;

import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.params.Params;

/**
 * 读取配置
 * @author WD
 */
public final class CoreParams {
	/** 分页大小 */
	public final static int	PAGE_SIZE		= Params.getInt("page.size", 20);
	/** HTTP 超时 */
	public final static int	HTTP_TIMEOUT	= Params.getInt("http.timeout", 5000);
	/** HTTP 最大连接池 */
	public final static int	HTTP_MAX		= Params.getInt("http.max", SystemConstants.CPU_NUM * 10);

	private CoreParams() {}
}
