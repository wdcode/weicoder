package com.weicoder.web.params;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.params.Params;
import com.weicoder.web.util.IpUtil;

/**
 * jetty参数
 * @author WD 
 * @version 1.0 
 */
public final class JettyParams {
	/** 主机 */
	public final static String	HOST	= Params.getString("jetty.host", IpUtil.LOCAL_IP);
	/** 端口 */
	public final static int		PORT	= Params.getInt("jetty.port", 8080);
	/** webapp路径 */
	public final static String	WEBAPP	= Params.getString("jetty.webapp", "webapp");
	/** 工程路径 */
	public final static String	CONTEXT	= Params.getString("jetty.context", StringConstants.BACKSLASH);

	private JettyParams() {}
}
