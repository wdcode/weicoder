package com.weicoder.admin.params;

import java.util.List;

import com.weicoder.admin.constants.AdminConstants;
import com.weicoder.admin.po.Admin;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.params.Params;

/**
 * 读取后台管理配置
 * @author WD
 * @since JDK7
 * @version 1.0 2009-08-04
 */
public final class AdminParams {
	/**
	 * 创建者ID
	 */
	public final static int				ADMIN			= Params.getInt("admin", 1);
	/**
	 * 后台页面记录是否开启
	 */
	public final static boolean			BACK_URL		= Params.getBoolean("back.url", false);
	/**
	 * 后台主题
	 */
	public final static String			BACK_THEME		= Params.getString(AdminConstants.BACK_THEME_KEY, StringConstants.DEFAULT);
	/**
	 * 后台登录Key
	 */
	public final static String			BACK_LOGIN		= Params.getString("back.login", Admin.class.getSimpleName());
	/**
	 * 后台路径
	 */
	public final static String			BACK_PATH		= Params.getString("back.path", StringConstants.BACKSLASH);
	/**
	 * 判断是否开启权限认证
	 */
	public final static boolean			SECURITY_POWER	= Params.getBoolean("back.security.power", true);
	/**
	 * 判断是否开启IP认证
	 */
	public final static boolean			SECURITY_IP		= Params.getBoolean("back.security.ip", false);
	/**
	 * ip过滤列表
	 */
	public final static List<String>	SECURITY_IPS	= Params.getList("back.security.ips", Lists.getList(ArrayConstants.STRING_EMPTY));
	/**
	 * 判断是否开启权限角色认证
	 */
	public final static boolean			SECURITY_ROLE	= Params.getBoolean("back.security.role", true);
	/**
	 * 判断是否开启操作类型认证
	 */
	public final static int				SECURITY_TYPE	= Params.getInt("back.security.type", 0);
	/**
	 * 登录日志是否开启
	 */
	public final static boolean			LOGS_LOGIN		= Params.getBoolean("logs.login", false);
	/**
	 * 操作日志是否开启
	 */
	public final static boolean			LOGS			= Params.getBoolean("logs", false);

	/**
	 * 构造方法
	 */
	private AdminParams() {}
}
