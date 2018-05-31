package com.weicoder.core.params;

import com.weicoder.common.params.Params;

/**
 * 读取配置
 * @author WD
 */
public final class CoreParams {
	/** 分页大小 */
	public final static int		PAGE_SIZE	= Params.getInt("page.size", 20);
	
	private CoreParams() {}
}
