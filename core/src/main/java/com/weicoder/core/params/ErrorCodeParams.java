package com.weicoder.core.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.lang.Conversion;

/**
 * 错误码读取配置
 * @author WD
 */
public final class ErrorCodeParams {
	// errorcode 配置
	private final static Config CONFIG = ConfigFactory.getConfig("errorcode");

	/**
	 * 根据错误码获取错误信息
	 * @param errorcode 错误码
	 * @return 错误信息
	 */
	public static String getMessage(int errorcode) {
		return CONFIG.getString(Conversion.toString(errorcode));
	}

	private ErrorCodeParams() {}
}
