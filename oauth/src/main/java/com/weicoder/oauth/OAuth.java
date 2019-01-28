package com.weicoder.oauth;

import java.util.Map;

/**
 * 验证接口
 * @author WD 2013-12-17
 */
public interface OAuth {
	/**
	 * 获得验证URL地址
	 * @param params 带到返回地址的参数
	 * @return 跳转地址
	 */
	String getAuthorize(Map<String, String> params);

	/**
	 * 根据code获得授权 一般web直接跳转使用
	 * @param code
	 * @return 授权
	 */
	OAuthInfo getInfoByCode(String code);

	/**
	 * 根据accessToken获得授权 一般手机和内部可使用
	 * @param token accessToken
	 * @param openid 第三方id
	 * @return 授权
	 */
	OAuthInfo getInfoByToken(String token, String openid);

}
