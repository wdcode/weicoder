package com.weicoder.oauth;

import com.weicoder.oauth.impl.OAuth360Web;
import com.weicoder.oauth.qq.OAuthQQNew;
import com.weicoder.oauth.qq.OAuthQQWeb;
import com.weicoder.oauth.wechat.OAuthWeChatPub;
import com.weicoder.oauth.wechat.OAuthWeChatVip;
import com.weicoder.oauth.wechat.OAuthWeChatWeb;
import com.weicoder.oauth.weibo.OAuthWeiboWeb;

/**
 * 第三方验证构造器
 * @author WD 2013-12-17
 */
public final class OAuthBuilder {
	/**
	 * 构建一个第三方登录器
	 * @param type
	 * @return
	 */
	public static OAuth build(String type) {
		switch (type) {
			case "qq":
				// QQ
				return new OAuthQQWeb();
			case "weibo":
				// weibo
				return new OAuthWeiboWeb();
			case "wechat":
				// wechat
				return new OAuthWeChatWeb();
			case "weixin":
				// weixin
				return new OAuthWeChatWeb();
			case "360":
				// 360
				return new OAuth360Web();
			case "qqn":
				// QQ
				return new OAuthQQNew();
			case "wechat_vip":
				// QQ
				return new OAuthWeChatVip();
			case "wechat_pub":
				// QQ
				return new OAuthWeChatPub();
			default:
				return null;
		}
	}
}
