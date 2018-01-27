package com.weicoder.nosql.params;

import com.weicoder.common.params.Params;

/**
 * Zookeeper参数
 * @author WD
 */
public final class ZookeeperParams {
	/** Zookeeper 连接字符串 */
	public final static String CONNECT = Params.getString("zk.connect");

	private ZookeeperParams() {}
}
