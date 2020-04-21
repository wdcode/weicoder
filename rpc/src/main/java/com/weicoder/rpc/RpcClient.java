package com.weicoder.rpc;

import java.net.InetSocketAddress;

/**
 * 生成Rpc客户端接口
 * 
 * @author wudi
 */
public interface RpcClient {
	/**
	 * rpc客户端生成服务方法
	 * 
	 * @param  <E>
	 * @param  rpc  rpc接口
	 * @param  addr rpc地址
	 * @return
	 */
	<E> E client(Class<E> rpc, InetSocketAddress addr);
}
