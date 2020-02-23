package com.weicoder.test;

import com.weicer.rpc.annotation.Rpc;

@Rpc("rpct")
public interface Irpc {
	String test(int i);

	Users get(long i);
	
	Users user(RpcB b);
}
