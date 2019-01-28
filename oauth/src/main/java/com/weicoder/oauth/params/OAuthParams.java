package com.weicoder.oauth.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.constants.StringConstants; 

/**
 * 用户配置
 * @author WD
 */
public final class OAuthParams {
	/** */
	private final static Config	OAUTH						= ConfigFactory.getConfig("oauth");
	/** 保存oathu state */
	public final static String	OAUTH_STATE					= OAUTH.getString("oauth.state", "maozhua");
	/** 微信 APPID */
	public final static String	OAUTH_WECHAT_WEB_APPID		= OAUTH.getString("wechat.web.appid", StringConstants.EMPTY);
	/** 微信 APPSECRET */
	public final static String	OAUTH_WECHAT_WEB_APPSECRET	= OAUTH.getString("wechat.web.secret", StringConstants.EMPTY);
	/** 微信 REDIRECT_URI 回调地址 */
	public final static String	OAUTH_WECHAT_WEB_REDIRECT	= OAUTH.getString("wechat.web.redirect", StringConstants.EMPTY);
	/** QQ APPID */
	public final static String	OAUTH_QQ_WEB_APPID			= OAUTH.getString("qq.web.appid");
	/** QQ APPSECRET */
	public final static String	OAUTH_QQ_WEB_APPSECRET		= OAUTH.getString("qq.web.secret");
	/** QQ REDIRECT_URI 回调地址 */
	public final static String	OAUTH_QQ_WEB_REDIRECT		= OAUTH.getString("qq.web.redirect", StringConstants.EMPTY);
	/** QQ APPID */
	public final static String	OAUTH_QQ_NEW_APPID			= OAUTH.getString("qq.new.appid");
	/** QQ APPSECRET */
	public final static String	OAUTH_QQ_NEW_APPSECRET		= OAUTH.getString("qq.new.secret");
	/** QQ REDIRECT_URI 回调地址 */
	public final static String	OAUTH_QQ_NEW_REDIRECT		= OAUTH.getString("qq.new.redirect", StringConstants.EMPTY);
	/** 微博 APPID */
	public final static String	OAUTH_WEIBO_WEB_APPID		= OAUTH.getString("weibo.web.appid", StringConstants.EMPTY);
	/** 微博 APPSECRET */
	public final static String	OAUTH_WEIBO_WEB_APPSECRET	= OAUTH.getString("weibo.web.secret", StringConstants.EMPTY);
	/** 微博 REDIRECT_URI 回调地址 */
	public final static String	OAUTH_WEIBO_WEB_REDIRECT	= OAUTH.getString("weibo.web.redirect", StringConstants.EMPTY);
	/** 360 APPID */
	public final static String	OAUTH_360_WEB_APPID			= OAUTH.getString("360.web.appid");
	/** 360 APPKEY */
	public final static String	OAUTH_360_WEB_APPKEY		= OAUTH.getString("360.web.appkey");
	/** 360 APPSECRET */
	public final static String	OAUTH_360_WEB_APPSECRET		= OAUTH.getString("360.web.secret");
	/** 360 REDIRECT_URI 回调地址 */
	public final static String	OAUTH_360_WEB_REDIRECT		= OAUTH.getString("360.web.redirect", StringConstants.EMPTY); 

	private OAuthParams() {}
}
