package com.weicoder.common.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.constants.C;
import com.weicoder.common.lang.W;

/**
 * 状态码读取配置
 * 
 * @author WD
 */
public sealed class StateParams permits P.S {
	// state 配置
	private final static Config	CONFIG		= P.getConfig("state");
	/** 状态码 0=成功 */
	public final static int		SUCCESS		= CONFIG.getInt("success", 0);
	/** 状态码 1=系统错误 */
	public final static int		ERROR		= CONFIG.getInt("error", 1);
	/** 状态码 100=空 */
	public final static int		NULL		= CONFIG.getInt("null", 2);
	/** 状态码成功信息 */
	public final static String	SUCCESS_MSG	= CONFIG.getString("success.message", C.S.SUCCESS);

	/**
	 * 根据状态码获取状态信息
	 * 
	 * @param code 状态码
	 * @return 状态信息
	 */
	public static String getMessage(int code) {
		return CONFIG.getString(W.C.toString(code));
	}
}
