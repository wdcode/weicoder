package com.weicoder.web.params;

import com.weicoder.common.params.Params;

/**
 * 验证参数
 * @author WD
 */
public final class ValidatorParams {
	/** 验证框架 Token 客户端Ip验证 多少段算通过 */
	public final static int TOKEN_IP = Params.getInt("validator.token.ip", 3);

	private ValidatorParams() {}
}
