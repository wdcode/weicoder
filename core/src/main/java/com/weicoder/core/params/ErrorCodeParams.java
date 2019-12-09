package com.weicoder.core.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.params.Params;

/**
 * 错误码读取配置
 * 
 * @author WD
 */
public final class ErrorCodeParams {
	// errorcode 配置
	private final static Config CONFIG      = ConfigFactory.getConfig("errorcode");
	/** 状态码 0=成功 */
	public final static int     SUCCESS     = Params.getInt("success", 0);
	/** 错误码 1=系统错误 */
	public final static int     ERROR       = CONFIG.getInt("error", 1);
	/** 状态码 100=空 */
	public final static int     NULL        = Params.getInt("null", 100);
	/** 状态码成功信息 */
	public final static String  SUCCESS_MSG = Params.getString("success.msg", "success");

	/**
	 * 根据错误码获取错误信息
	 * 
	 * @param errorcode 错误码
	 * @return 错误信息
	 */
	public static String getMessage(int errorcode) {
		return CONFIG.getString(Conversion.toString(errorcode));
	}

	private ErrorCodeParams() {
	}
}
