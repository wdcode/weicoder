package com.weicoder.test;
 
import com.weicer.rpc.RpcClients;
import com.weicoder.extend.C;

public class RpcTest {

	public static void main(String[] args) {
//		RpcClients.init(); 
		System.out.println(C.toString(RpcClients.rpc("rpct", "test", 102)).length());
		System.out.println(RpcClients.rpc("rpct", "get", 108L));
		System.out.println(RpcClients.rpc(new RpcB(188L,"test")));
		
		Irpc rpc = RpcClients.client(Irpc.class);
		System.out.println(rpc.test(202).length());
		System.out.println(rpc.get(208L));
		System.out.println(rpc.user(new RpcB(288L,"new")));
	}

}
