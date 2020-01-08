package com.weicoder.admin.params;

import com.weicoder.common.params.Params;

/**
 * 读取后台管理配置
 * 
 * @author WD
 */
public final class AdminParams {
	/** 创建者ID */
	public final static String ADMIN = Params.getString("admin", "admin");

	private AdminParams() {
	}
}
