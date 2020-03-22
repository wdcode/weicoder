package com.weicoder.test;

import com.weicoder.common.log.Logs;
import com.weicoder.zookeeper.ZookeeperClient;

public class ZookeeperTest {

	public static void main(String[] args) {
		Logs.debug("123");
		ZookeeperClient.create("1", new byte[0]);
	} 
}
