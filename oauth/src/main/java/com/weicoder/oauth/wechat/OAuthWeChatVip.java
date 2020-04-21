package com.weicoder.oauth.wechat;

/**
 * 微信
 * @author WD
 */
public class OAuthWeChatVip extends OAuthWeChatWeb {

	@Override
	protected String url() {
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";
	}

	@Override
	protected String appid() {
		return "wxdc887ecf6bd7221e";
	}

	@Override
	protected String redirect() {
		return "http://login.maobotv.com/cgi/login/callback?type=wechat_vip";
	}

	@Override
	protected String appsecret() {
		return "1bff812969ee4fbbe4639df6ede5eeb2";
	} 
}
