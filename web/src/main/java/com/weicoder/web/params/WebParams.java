package com.weicoder.web.params;

import com.weicoder.common.params.Params;

/**
 * web包参数读取类
 * @author WD
 */
public final class WebParams {
	/** 是否支持get */
	public final static boolean	GET					= Params.getBoolean("servlet.get", true);
	/** 验证码出现的字符集 */
	public final static char[]	VERIFY_CODE			= Params.getString("verify.code", "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	/** 验证码长度 */
	public final static int		VERIFY_LENGTH		= Params.getInt("verify.length", 4);
	/** 域名 */
	public final static String	DOMAIN				= Params.getString("domain");
	/** 状态码 空状态 */
	public final static int		STATE_ERROR_NULL	= Params.getInt("state.error.null", 100);
	/** 状态码 成功 */
	public final static int		STATE_SUCCESS		= Params.getInt("state.success", 0);
	/** 状态码成功信息 */
	public final static String	STATE_SUCCESS_MSG	= Params.getString("state.success.msg", "success");

	private WebParams() {}
}