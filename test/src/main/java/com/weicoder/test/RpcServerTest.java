package com.weicoder.test;
  
import com.weicer.rpc.RpcServers; 

public class RpcServerTest {

	public static void main(String[] args) {
		RpcServers.start();
		System.out.println("rpc server start...");
	}
}
