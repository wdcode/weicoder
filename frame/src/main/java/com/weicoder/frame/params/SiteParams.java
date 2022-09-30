package com.weicoder.frame.params;

import com.weicoder.common.constants.C;
import com.weicoder.common.params.P;

/**
 * WdLogs包参数读取类
 * @author WD
 * 
 * @version 1.0 2010-11-02
 */
public final class SiteParams {
	/** email验证是否开启 */
	public final static int		USER_STATE					= P.getInt("user.state", 1);
	/** email验证是否开启 */
	public final static boolean	USER_VERIFY_STATE			= P.getBoolean("user.verify.state", false);
	/** email验证是否开启 */
	public final static boolean	USER_VERIFY_EMAIL			= P.getBoolean("user.verify.email", false);
	/** email验证Action */
	public final static String	USER_VERIFY_EMAIL_ACTION	= P.getString("user.verify.email.action");
	/** email验证标题 */
	public final static String	USER_VERIFY_EMAIL_SUBJECT	= P.getString("user.verify.email.subject");
	/** email验证内容 */
	public final static String	USER_VERIFY_EMAIL_CONTENT	= P.getString("user.verify.email.content");
	/** email验证替换url */
	public final static String	USER_VERIFY_EMAIL_URL		= P.getString("user.verify.email.url");
	/** email验证替换用户名 */
	public final static String	USER_VERIFY_EMAIL_NAME		= P.getString("user.verify.email.name");
	/** 登录信息最大保存时间 */
	public final static int		LOGIN_MAX_AGE				= P.getInt("login.maxAge", C.D.WEEK);
	/** 登录信息最小保存时间 */
	public final static int		LOGIN_MIN_AGE				= P.getInt("login.minAge", C.D.HOUR * 2);
	/** 登录游客名称 */
	public final static String	LOGIN_GUEST_NAME			= P.getString("login.guest.name", "游客");
	/** 登录游客ID下标开始 */
	public final static int		LOGIN_GUEST_ID				= P.getInt("login.guest.id", -1);

	private SiteParams() {}
}