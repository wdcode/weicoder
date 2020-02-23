package com.weicoder.test;
 
import com.weicer.rpc.SofaRpcs; 

public class SofaRpcClientTest {
	public static void main(String[] args) {
//		ConsumerConfig<Irpc> consumerConfig = new ConsumerConfig<Irpc>().setInterfaceId(Irpc.class.getName()) // 指定接口
//				.setProtocol("bolt") // 指定协议
//				.setDirectUrl("bolt://127.0.0.1:9696"); // 指定直连地址
//		Irpc rpc = consumerConfig.refer();
		
		Irpc rpc = SofaRpcs.client(Irpc.class);
		System.out.println(rpc.test(102));
		System.out.println(rpc.get(108));
		System.out.println(rpc.user(new RpcB(200L, "test")));
	}
}
