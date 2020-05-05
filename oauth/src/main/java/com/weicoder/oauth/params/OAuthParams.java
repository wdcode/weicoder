package com.weicoder.oauth.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory; 

/**
 * 用户配置
 * 
 * @author WD
 */
public final class OAuthParams {
	/** 配置文件 */
	private final static Config OAUTH                = ConfigFactory.getConfig("oauth");
	/** 保存oathu state */
	public final static String  STATE                = OAUTH.getString("oauth.state", "weicoder");
	/** 微信 APPID */
	public final static String  WECHAT_WEB_APPID     = OAUTH.getString("wechat.web.appid");
	/** 微信 APPSECRET */
	public final static String  WECHAT_WEB_APPSECRET = OAUTH.getString("wechat.web.secret");
	/** 微信 REDIRECT_URI 回调地址 */
	public final static String  WECHAT_WEB_REDIRECT  = OAUTH.getString("wechat.web.redirect");
	/** QQ APPID */
	public final static String  QQ_WEB_APPID         = OAUTH.getString("qq.web.appid");
	/** QQ APPSECRET */
	public final static String  QQ_WEB_APPSECRET     = OAUTH.getString("qq.web.secret");
	/** QQ REDIRECT_URI 回调地址 */
	public final static String  QQ_WEB_REDIRECT      = OAUTH.getString("qq.web.redirect");
	/** QQ APPID */
	public final static String  QQ_NEW_APPID         = OAUTH.getString("qq.new.appid");
	/** QQ APPSECRET */
	public final static String  QQ_NEW_APPSECRET     = OAUTH.getString("qq.new.secret");
	/** QQ REDIRECT_URI 回调地址 */
	public final static String  QQ_NEW_REDIRECT      = OAUTH.getString("qq.new.redirect");
	/** 微博 APPID */
	public final static String  WEIBO_WEB_APPID      = OAUTH.getString("weibo.web.appid");
	/** 微博 APPSECRET */
	public final static String  WEIBO_WEB_APPSECRET  = OAUTH.getString("weibo.web.secret");
	/** 微博 REDIRECT_URI 回调地址 */
	public final static String  WEIBO_WEB_REDIRECT   = OAUTH.getString("weibo.web.redirect");
	/** 360 APPID */
	public final static String  _360_WEB_APPID       = OAUTH.getString("360.web.appid");
	/** 360 APPKEY */
	public final static String  _360_WEB_APPKEY      = OAUTH.getString("360.web.appkey");
	/** 360 APPSECRET */
	public final static String  _360_WEB_APPSECRET   = OAUTH.getString("360.web.secret");
	/** 360 REDIRECT_URI 回调地址 */
	public final static String  _360_WEB_REDIRECT    = OAUTH.getString("360.web.redirect");

	private OAuthParams() {
	}
}
