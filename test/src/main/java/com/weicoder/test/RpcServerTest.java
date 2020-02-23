package com.weicoder.test;
  
import com.weicer.rpc.RpcServers; 

public class RpcServerTest {

	public static void main(String[] args) {
		RpcServers.start();
//		ClassUtil.getAnnotationClass(CommonParams.getPackages("rpc"), RpcServer.class).forEach(r -> {// 处理所有方法
//			for (Method m : r.getDeclaredMethods()) {
//				String name = m.getName();
//				System.out.println(name);
//				System.out.println(BeanUtil.newInstance(r));
//				System.out.println(m);
//				System.out.println(m.getParameterCount() > 0 ? m.getParameters()[0].getType() : null);
//			}
//		});
	}
}
