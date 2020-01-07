package com.weicoder.admin.params;

import com.weicoder.common.params.Params;

/**
 * 读取后台管理配置
 * 
 * @author  WD
 * @since   JDK7
 * @version 1.0 2009-08-04
 */
public final class AdminParams {
	/** 创建者ID */
	public final static int ADMIN = Params.getInt("admin", 1);

	private AdminParams() {
	}
}
