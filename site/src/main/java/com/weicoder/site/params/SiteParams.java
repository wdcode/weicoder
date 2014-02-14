package com.weicoder.site.params;

import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.params.Params;

/**
 * WdLogs包参数读取类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-11-02
 */
public final class SiteParams {
	/**
	 * email验证是否开启
	 */
	public final static boolean	USER_VERIFY_STATE			= Params.getBoolean("user.verify.state", false);
	/**
	 * email验证是否开启
	 */
	public final static boolean	USER_VERIFY_EMAIL			= Params.getBoolean("user.verify.email", false);
	/**
	 * email验证Action
	 */
	public final static String	USER_VERIFY_EMAIL_ACTION	= Params.getString("user.verify.email.action");
	/**
	 * email验证标题
	 */
	public final static String	USER_VERIFY_EMAIL_SUBJECT	= Params.getString("user.verify.email.subject");
	/**
	 * email验证内容
	 */
	public final static String	USER_VERIFY_EMAIL_CONTENT	= Params.getString("user.verify.email.content");
	/**
	 * email验证替换url
	 */
	public final static String	USER_VERIFY_EMAIL_URL		= Params.getString("user.verify.email.url");
	/**
	 * email验证替换用户名
	 */
	public final static String	USER_VERIFY_EMAIL_NAME		= Params.getString("user.verify.email.name");
	/**
	 * 登录信息最大保存时间
	 */
	public final static int		LOGIN_MAX_AGE				= Params.getInt("login.maxAge", DateConstants.WEEK);
	/**
	 * 登录信息最小保存时间
	 */
	public final static int		LOGIN_MIN_AGE				= Params.getInt("login.minAge", DateConstants.HOUR * 2);
	/**
	 * 登录游客名称
	 */
	public final static String	LOGIN_GUEST_NAME			= Params.getString("login.guest.name", "游客");
	/**
	 * 登录游客ID下标开始
	 */
	public final static int		LOGIN_GUEST_ID				= Params.getInt("login.guest.id", -1);
	/**
	 * 是否静态化
	 */
	public final static boolean	STAICS_POWER				= Params.getBoolean("staics.power", false);

	/**
	 * 私有构造
	 */
	private SiteParams() {}
}