package com.weicoder.test;

import com.weicoder.rpc.annotation.Rpc;

@Rpc("rpct")
public interface Irpc {
	String test(int i);

	Users get(long i);
	
	Users user(RpcB b);
}
