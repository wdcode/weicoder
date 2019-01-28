package com.weicoder.oauth.wechat;

/**
 * 微信
 * @author WD
 */
public class OAuthWeChatPub extends OAuthWeChatVip {
 
	@Override
	protected String appid() {
		return "wx45df843d3277b3dc";
	}

	@Override
	protected String redirect() {
		return "http://login.maobotv.com/cgi/login/callback?type=wechat_pub";
	}

	@Override
	protected String appsecret() {
		return "53cb6841f78721532565e212421c2b64";
	}
}
