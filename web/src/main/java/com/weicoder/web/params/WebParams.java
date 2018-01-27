package com.weicoder.web.params;

import java.util.Set;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.params.Params;

/**
 * web包参数读取类
 * @author WD
 */
public final class WebParams {
	/** 是否支持get */
	public final static boolean		GET				= Params.getBoolean("servlet.get", true);
	/** IP鉴权 允许的IP访问 */
	public final static Set<String>	IPS				= Sets.newSet(Params.getStringArray("servlet.ips", ArrayConstants.STRING_EMPTY));
	/** 验证码出现的字符集 */
	public final static char[]		VERIFY_CODE		= Params.getString("verify.code", "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	/** 验证码长度 */
	public final static int			VERIFY_LENGTH	= Params.getInt("verify.length", 4);
	/** 域名 */
	public final static String		DOMAIN			= Params.getString("domain");

	private WebParams() {}
}