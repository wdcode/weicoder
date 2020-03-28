package com.weicoder.test;
 
import com.weicer.rpc.Rpcs; 
import com.weicoder.common.W;

public class RpcTest {

	public static void main(String[] args) {
//		RpcClients.init(); 
		System.out.println(W.C.toString(Rpcs.rpc("rpct", "test", 102)).length());
		System.out.println(Rpcs.rpc("rpct", "get", 108L));
		System.out.println(Rpcs.rpc(new RpcB(188L,"test")));
		
		Irpc rpc = Rpcs.client(Irpc.class);
		System.out.println(rpc.test(202).length());
		System.out.println(rpc.get(208L));
		System.out.println(rpc.user(new RpcB(288L,"new")));
	}

}
