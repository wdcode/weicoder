package com.weicoder.oauth.wechat;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.EmptyUtil; 
import com.weicoder.core.json.JsonEngine;
import com.weicoder.core.http.HttpClient;
import com.weicoder.oauth.OAuthInfo;
import com.weicoder.oauth.base.BaseOAuth;
import com.weicoder.oauth.params.OAuthParams; 

/**
 * 微信登录
 * @author WD
 */
public class OAuthWeChatWeb extends BaseOAuth {
	// 获得用户信息
	private final static String GET_USER_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

	@Override
	protected String redirect() {
		return OAuthParams.OAUTH_WECHAT_WEB_REDIRECT;
	}

	@Override
	protected String url() {
		return "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
	}

	@Override
	protected String appid() {
		return OAuthParams.OAUTH_WECHAT_WEB_APPID;
	}

	@Override
	protected String appsecret() {
		return OAuthParams.OAUTH_WECHAT_WEB_APPSECRET;
	}

	@Override
	protected String accessTokenUrl() {
		return "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	}

	@Override
	protected OAuthInfo getInfo(String res) {
		JSONObject json = JsonEngine.toJSONObject(res);
		return getInfoByToken(json.getString("access_token"), json.getString("openid"));
	}

	@Override
	public OAuthInfo getInfoByToken(String token, String openid) {
		// openid不为空 请求用户信息
		String res = HttpClient.get(String.format(GET_USER_URL, token, openid));
		// 返回信息
		Map<String, Object> map = JsonEngine.toMap(res);
		OAuthInfo info = new OAuthInfo();
		info.setOpenid(openid);
		info.setType("wechat");
		info.setData(res);
		info.setNickname(Conversion.toString(map.get("nickname")));
		info.setHead(Conversion.toString(map.get("headimgurl")));
		info.setSex("1".equals(Conversion.toString(map.get("sex"))) ? 1 : 0);
		if (EmptyUtil.isEmpty(info.getUnionid()))
			info.setUnionid(Conversion.toString(map.get("unionid")));
		return info;
	}
}
