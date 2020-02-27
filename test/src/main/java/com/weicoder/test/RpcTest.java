package com.weicoder.test;
 
import com.weicer.rpc.RpcClients;

public class RpcTest {

	public static void main(String[] args) {
//		RpcClients.init(); 
		System.out.println(RpcClients.rpc("rpct", "test", 102));
		System.out.println(RpcClients.rpc("rpct", "get", 108L));
		System.out.println(RpcClients.rpc(new RpcB(188L,"test")));
		
		Irpc rpc = RpcClients.client(Irpc.class);
		System.out.println(rpc.test(202));
		System.out.println(rpc.get(208L));
		System.out.println(rpc.user(new RpcB(288L,"new")));
	}

}
