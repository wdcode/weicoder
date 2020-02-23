package com.weicoder.test;
 
import com.weicer.rpc.SofaRpcs;

public class SofaRpcServerTest {

	public static void main(String[] args) {
		SofaRpcs.start();
		System.out.println("sofa rpc start");
//		 ServerConfig serverConfig = new ServerConfig()
//	                .setProtocol("bolt") // 设置一个协议，默认bolt
//	                .setPort(9696); // 非守护线程
//		 System.out.println("Daemon="+serverConfig.isDaemon());
//
//	        ProviderConfig<Irpc> providerConfig = new ProviderConfig<Irpc>()
//	                .setInterfaceId(Irpc.class.getName()) // 指定接口
//	                .setRef(new RpcS()) // 指定实现
//	                .setServer(serverConfig); // 指定服务端
//
//	        providerConfig.export(); // 发布服务
	} 
}
