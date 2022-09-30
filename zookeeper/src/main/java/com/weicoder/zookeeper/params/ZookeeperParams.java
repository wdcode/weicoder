package com.weicoder.zookeeper.params;

import com.weicoder.common.params.P;

/**
 * Zookeeper参数
 * 
 * @author WD
 */
public final class ZookeeperParams {
	/** Zookeeper 连接字符串 */
	public final static String CONNECT = P.getString("zk.connect");

	private ZookeeperParams() {
	}
}
