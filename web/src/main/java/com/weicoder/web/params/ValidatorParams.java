package com.weicoder.web.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.params.P;

/**
 * 验证参数
 * 
 * @author WD
 */
public final class ValidatorParams {
	private ValidatorParams() {
	}

	// 前缀
	private final static String	PREFIX		= "validator";
	// Properties配置
	private final static Config	CONFIG		= P.getConfig(PREFIX);
	/** 验证框架 Token 客户端Ip验证 多少段算通过 */
	public final static int		TOKEN_IP	= CONFIG.getInt("token.ip", P.getInt(PREFIX + ".token.ip", 3));
}
