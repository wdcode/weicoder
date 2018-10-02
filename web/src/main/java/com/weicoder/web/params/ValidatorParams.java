package com.weicoder.web.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.params.Params;

/**
 * 验证参数
 * @author WD
 */
public final class ValidatorParams {
	// 前缀
	private final static String	PREFIX		= "validator";
	// Properties配置
	private final static Config	CONFIG		= ConfigFactory.getConfig(PREFIX);
	/** 验证框架 Token 客户端Ip验证 多少段算通过 */
	public final static int		TOKEN_IP	= CONFIG.getInt("token.ip", Params.getInt(PREFIX + ".token.ip", 3));

	private ValidatorParams() {}
}
