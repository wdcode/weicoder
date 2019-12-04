package com.weicoder.oauth.qq;

import java.util.Map;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil; 
import com.weicoder.core.json.JsonEngine;
import com.weicoder.core.http.HttpClient;
import com.weicoder.oauth.OAuthInfo;
import com.weicoder.oauth.base.BaseOAuth;
import com.weicoder.oauth.params.OAuthParams; 

/**
 * QQ登录
 * @author WD 2013-12-17
 */
public final class OAuthQQWeb extends BaseOAuth {
	// 获得openid url地址
	private final static String	OPEN_ID_URL		= "https://graph.qq.com/oauth2.0/me?access_token=%s&unionid=1";
	// 获得用户信息
	private final static String	GET_USER_URL	= "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";

	@Override
	protected String redirect() {
		return OAuthParams.OAUTH_QQ_WEB_REDIRECT;
	}

	@Override
	protected String url() {
		return "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s";
	}

	@Override
	protected String appid() {
		return OAuthParams.OAUTH_QQ_WEB_APPID;
	}

	@Override
	protected String appsecret() {
		return OAuthParams.OAUTH_QQ_WEB_APPSECRET;
	}

	@Override
	protected String accessTokenUrl() {
		return "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";
	}

	@Override
	protected OAuthInfo getInfo(String res) {
		return getInfoByToken(StringUtil.subString(res, "access_token=", "&expires_in"), null);
	}

	@Override
	public OAuthInfo getInfoByToken(String token, String openid) {
		if (EmptyUtil.isEmpty(token) || token.startsWith("callback"))
			return null;
		// 获得openid url
		String url = String.format(OPEN_ID_URL, token);
		// 获得提交返回结果
		String res = StringUtil.subString(HttpClient.get(url), " ", " ");
		Map<String, Object> map = JsonEngine.toMap(res);
		if (EmptyUtil.isEmpty(map))
			return null;
		// 获得openid
		openid = Conversion.toString(map.get("openid"));
		// 返回信息 unioid
		OAuthInfo info = new OAuthInfo();
		info.setOpenid(openid);
		info.setUnionid(Conversion.toString(map.get("unionid")));
		info.setType("qq");
		info.setData(res);
		// openid不为空 请求用户信息
		if (!EmptyUtil.isEmpty(openid)) {
			res = HttpClient.get(String.format(GET_USER_URL, token, appid(), openid));
			Logs.debug("type={} openid={} user_info={}", info.getType(), openid, res);
			map = JsonEngine.toMap(res);
			info.setNickname(Conversion.toString(map.get("nickname")));
			info.setHead(Conversion.toString(map.get("figureurl_qq_1")));
			info.setSex("男".equals(Conversion.toString(map.get("gender"))) ? 1 : 0);
		}
		return info;
	}
}
