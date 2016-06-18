package com.weicoder.web.params;

import com.weicoder.common.params.Params;

/**
 * web包参数读取类
 * @author WD
 */
public final class WebParams {
	/** 验证码出现的字符集 */
	public final static String	VERIFY_CODE			= Params.getString("verify.code", "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	/** 验证码出现的字符集 */
	public final static char[]	VERIFY_CODE_CHARS	= VERIFY_CODE.toCharArray();
	/** 验证码长度 */
	public final static int		VERIFY_LENGTH		= Params.getInt("verify.length", 4);
	/** 保存到session中的key */
	public final static String	VERIFY_KEY			= Params.getString("verify.key", "verifyCode");
	/** 验证码字体 */
	public final static String	VERIFY_FONT			= Params.getString("verify.font", "Times New Roman");
	/** 域名 */
	public final static String	DOMAIN				= Params.getString("domain");

	private WebParams() {
	}
}