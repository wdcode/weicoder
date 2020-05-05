package com.weicoder.admin.state;

import com.weicoder.common.bean.StateCode;

/**
 * 错误码
 * 
 * @author WD
 */
public final class ErrorCode {
	/** 10000=管理员不存在 */
	public final static StateCode ADMIN_NOT   = StateCode.build(10000);
	/** 10001=管理员状态不对 */
	public final static StateCode ADMIN_STATE = StateCode.build(10001);
	/** 10002=管理员密码不对 */
	public final static StateCode ADMIN_PWD   = StateCode.build(10002);
	/** 10003=管理员信息不全 */
	public final static StateCode ADMIN_NULL  = StateCode.build(10003);
	/** 10004=管理员名长度不能超过8位 */
	public final static StateCode ADMIN_LEN   = StateCode.build(10004);

	private ErrorCode() {
	}
}
