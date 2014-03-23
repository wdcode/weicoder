package com.weicoder.core.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.params.Params;

/**
 * nosql配置
 * @author WD
 * @since JDK7
 * @version 1.0 2014-1-15
 */
public final class NoSQLParams {
	/**
	 * 集群发送名称服务器
	 */
	public final static String[]	NAMES	= Params.getStringArray("nosql.names", ArrayConstants.STRING_EMPTY);

	/**
	 * 获得NoSQL使用的包<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: nosql.parse = ? <br/>
	 * XML: {@literal <nosql><parse>?</parse></nosql>}</h2>
	 * @return NoSQL使用的包
	 */
	public static String getParse(String name) {
		return Params.getString(Params.getKey("nosql", name, "parse"), "memcache");
	}

	private NoSQLParams() {}
}
