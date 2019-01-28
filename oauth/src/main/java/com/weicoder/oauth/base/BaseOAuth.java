package com.weicoder.oauth.base;

import java.util.Map;

import com.weicoder.common.codec.URLCode;
import com.weicoder.common.util.StringUtil;
import com.weicoder.http.HttpClient;
import com.weicoder.web.util.HttpUtil;

import com.weicoder.oauth.OAuth;
import com.weicoder.oauth.OAuthInfo;
import com.weicoder.oauth.params.OAuthParams; 

/**
 * 基础OAuth实现
 * @author WD
 */
public abstract class BaseOAuth implements OAuth {
	@Override
	public String getAuthorize(Map<String, String> params) {
		return String.format(url(), appid(),
				URLCode.encode(StringUtil.add(redirect(), "&", HttpUtil.toParameters(params))), OAuthParams.OAUTH_STATE);
	}

	@Override
	public OAuthInfo getInfoByCode(String code) {
		// 获得ACCESS_TOKEN url
		String url = String.format(accessTokenUrl(), appid(), appsecret(), code, redirect());
		// 获得提交返回结果
		String res = http(url);
		// 返回OAuthInfo
		return getInfo(res);
	}

	/**
	 * http提交请求token 一般为get 微博需要post 实现里自己改
	 * @param url
	 * @return
	 */
	protected String http(String url) {
		return HttpClient.get(url);
	}

	/**
	 * 回调地址
	 * @return 回调地址url
	 */
	protected abstract String redirect();

	/**
	 * 跳转url
	 * @return 跳转url
	 */
	protected abstract String url();

	/**
	 * 获得appid
	 * @return appid
	 */
	protected abstract String appid();

	/**
	 * 获得appsecret
	 * @return appsecret
	 */
	protected abstract String appsecret();

	/**
	 * 获得获取accessToken的url
	 * @return url
	 */
	protected abstract String accessTokenUrl();

	/**
	 * 获得第三方登录信息
	 * @param res access token URL获得的结果
	 * @return OAuthInfo
	 */
	protected abstract OAuthInfo getInfo(String res);
}
