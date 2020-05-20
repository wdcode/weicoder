package com.weicoder.rpc.sofa;

import java.net.InetSocketAddress;

import com.weicoder.common.util.StringUtil;
import com.weicoder.rpc.RpcClient;
import com.weicoder.rpc.params.RpcParams;

/**
 * sofa rpc客户端实现
 * 
 * @author wudi
 */
public class SofaClient implements RpcClient {

	@Override
	public <E> E client(Class<E> rpc, InetSocketAddress addr) {
		// 生成消费配置
		return new com.alipay.sofa.rpc.config.ConsumerConfig<E>().setInterfaceId(rpc.getName()) // 指定接口
				.setProtocol(RpcParams.PROTOCOL) // 指定协议
				.setDirectUrl(StringUtil.add(RpcParams.PROTOCOL, "://", addr.getAddress().getHostAddress(), ":",
						addr.getPort() + 1))// 指定地址
				.refer();
	}
}
