package com.weicoder.tomcat.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory; 
import com.weicoder.common.params.P;

/**
 * tomcat参数
 * 
 * @author wudi
 */
public final class TomcatParams {
	/** tomcat前缀 */
	private final static String PREFIX = "tomcat";
	// Properties配置
	private final static Config CONFIG   = ConfigFactory.getConfig(PREFIX);
	/** tomcat 端口 */
	public final static int     PORT     = CONFIG.getInt("port", P.getInt(PREFIX + ".port", 8080));
	/** tomcat 端口 */
	public final static String  PATH     = CONFIG.getString("path",
			P.getString(PREFIX + ".path", "/"));
	/** tomcat protocol 协议 */
	public final static String  PROTOCOL = CONFIG.getString("protocol",
			P.getString(PREFIX + ".protocol", "HTTP/1.1"));

	private TomcatParams() {
	}
}
