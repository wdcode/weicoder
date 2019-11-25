package com.weicoder.oauth.weibo;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.weicoder.common.codec.URLCode;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps; 
import com.weicoder.core.json.JsonEngine;
import com.weicoder.http.HttpClient;
import com.weicoder.oauth.OAuthInfo;
import com.weicoder.oauth.base.BaseOAuth;
import com.weicoder.oauth.params.OAuthParams; 

/**
 * 微博
 * @author WD 2013-12-17
 */
public class OAuthWeiboWeb extends BaseOAuth {
	@Override
	protected String redirect() {
		return OAuthParams.WEIBO_WEB_REDIRECT;
	}

	@Override
	public String getAuthorize(Map<String, String> params) {
		return String.format(url(), appid(), URLCode.encode(redirect()), URLCode.encode(params.get("toUrl")));
	}

	@Override
	protected String url() {
		return "https://api.weibo.com/oauth2/authorize?client_id=%s&response_type=code&redirect_uri=%s&state=%s";
	}

	@Override
	protected String appid() {
		return OAuthParams.WEIBO_WEB_APPID;
	}

	@Override
	protected String appsecret() {
		return OAuthParams.WEIBO_WEB_APPSECRET;
	}

	@Override
	protected String accessTokenUrl() {
		return "https://api.weibo.com/oauth2/access_token?client_id=%s&client_secret=%s&grant_type=authorization_code&code=%s&redirect_uri=%s";
	}

	@Override
	protected String http(String url) {
		return HttpClient.post(url, Maps.newMap());
	}

	@Override
	protected OAuthInfo getInfo(String res) {
		JSONObject json = JsonEngine.toJSONObject(res);
		return getInfoByToken(json.getString("access_token"), json.getString("uid"));
	}

	@Override
	public OAuthInfo getInfoByToken(String token, String openid) {
		String url = "https://api.weibo.com/2/users/show.json?access_token=" + token + "&uid=" + openid;
		String res = HttpClient.get(url);
		Map<String, Object> map = JsonEngine.toMap(res);
		// 返回信息
		OAuthInfo info = new OAuthInfo();
		info.setOpenid(Conversion.toString(map.get("id")));
		info.setType("weibo");
		info.setHead(Conversion.toString(map.get("profile_image_url")));
		info.setNickname(Conversion.toString(map.get("screen_name")));
		info.setSex("m".equals(Conversion.toString(map.get("gender"))) ? 1 : 0);
		info.setData(res);
		return info;
	}
}
