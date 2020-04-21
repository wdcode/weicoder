package com.weicoder.rpc.impl;

import java.net.InetSocketAddress;

import com.weicoder.common.U;
import com.weicoder.rpc.RpcClient;
import com.weicoder.rpc.Rpcs;

/**
 * JDK实现
 * 
 * @author wudi
 */
public class JdkClient implements RpcClient {

	@Override
	public <E> E client(Class<E> rpc, InetSocketAddress addr) {
		return U.C.newProxyInstance(rpc, (proxy, method, args) -> Rpcs.rpc(addr, method.getName(), args[0]));
	}
}
