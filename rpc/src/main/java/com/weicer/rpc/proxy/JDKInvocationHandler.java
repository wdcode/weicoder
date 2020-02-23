package com.weicer.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.weicer.rpc.RpcClients;

/**
 * 实现JDK代理方法
 * 
 * @author wudi
 */
public class JDKInvocationHandler implements InvocationHandler {
	private InetSocketAddress addr;

	public JDKInvocationHandler(InetSocketAddress addr) {
		this.addr = addr;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return RpcClients.rpc(addr, method.getName(), args[0]);
	}
}
