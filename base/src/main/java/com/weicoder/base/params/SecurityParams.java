package com.weicoder.base.params;

import java.util.List;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.params.Params;

/**
 * 安全配置
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-25
 */
public final class SecurityParams {
	/**
	 * 安全方法过滤
	 */
	public final static String[]		SECURITY_METHODS		= Params.getStringArray("security.methods", new String[] { "add", "edit", "del", "dels", "trun" });
	/**
	 * 是否使用IP过滤
	 */
	public final static boolean			SECURITY_POWER_METHOD	= Params.getBoolean("security.power.method", false);
	/**
	 * 安全方法过滤
	 */
	public final static List<String>	SECURITY_IPS			= Params.getList("security.ips", null);
	/**
	 * 是否使用IP过滤
	 */
	public final static boolean			SECURITY_POWER_IP		= Params.getBoolean("security.power.ip", false);

	/**
	 * 获得方法下可执行的实体列表<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: security.xxx = ? <br/>
	 * XML: {@literal <security><xxx>?</xxx></security>}</h2>
	 * @return 是否方法下可执行的实体列表
	 */
	public static List<String> getModules(String name) {
		return Params.getList(Params.getKey("security", StringConstants.EMPTY, name), null);
	}

	/**
	 * 构造方法
	 */
	private SecurityParams() {}
}
