package com.weicoder.test;

import com.weicoder.rpc.init.RpcInit; 

public class RpcServerTest {

	public static void main(String[] args) {
		new RpcInit().init();
 		System.out.println("rpc server start...");
	}
}
