package com.weicoder.oauth.impl;

import java.util.Map;

import com.weicoder.common.http.HttpEngine;
import com.weicoder.common.lang.C; 
import com.weicoder.json.JsonEngine; 
import com.weicoder.oauth.OAuthInfo;
import com.weicoder.oauth.base.BaseOAuth;
import com.weicoder.oauth.params.OAuthParams; 

/**
 * 360登录
 * @author WD
 */
public final class OAuth360Web extends BaseOAuth {
	// 获得openid url地址
	private final static String OPEN_ID_URL = "https://openapi.360.cn/user/me.json?access_token=%s";
	// 类型

	@Override
	protected String redirect() {
		return OAuthParams._360_WEB_REDIRECT;
	}

	@Override
	protected String url() {
		return "https://openapi.360.cn/oauth2/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s";
	}

	@Override
	protected String appid() {
		return OAuthParams._360_WEB_APPKEY;
	}

	@Override
	protected String appsecret() {
		return OAuthParams._360_WEB_APPSECRET;
	}

	@Override
	protected String accessTokenUrl() {
		return "https://openapi.360.cn/oauth2/access_token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";
	}

	@Override
	protected OAuthInfo getInfo(String res) {
		// 截取出token
		return getInfoByToken(JsonEngine.toJSONObject(res).getString("access_token"), null);
	}

	@Override
	public OAuthInfo getInfoByToken(String token, String openid) {
		// 获得openid url
		String url = String.format(OPEN_ID_URL, token);
		// 获得提交返回结果
		String res = HttpEngine.get(url);
		Map<String, Object> map = JsonEngine.toMap(res);
		// 返回信息
		OAuthInfo info = new OAuthInfo();
		info.setOpenid(C.toString(map.get("id")));
		info.setNickname(C.toString(map.get("name")));
		info.setHead(C.toString(map.get("avatar")));
		info.setSex(C.toInt(map.get("sex")));
		info.setType("360");
		info.setData(res);
		return info;
	}
}
