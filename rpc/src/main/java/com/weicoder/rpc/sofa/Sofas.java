package com.weicoder.rpc.sofa;

import java.net.InetSocketAddress;

import com.weicoder.rpc.params.RpcParams;
import com.weicoder.common.util.U;

/**
 * rpc客户端
 * 
 * @author wudi
 */
public final class Sofas {
	/**
	 * 根据rpc接口生成相关代理类 执行rpc调用操作
	 * 
	 * @param  <E>
	 * @param  rpc  rpc类
	 * @param  addr 远程地址
	 * @return      rpc客户端代理类
	 */
	public static <E> E client(Class<E> rpc, InetSocketAddress addr) {
		return sofa(rpc, addr);
	}

	/**
	 * 根据sofa rpc接口返回客户端
	 * 
	 * @param  cls  rpc接口
	 * @param  addr 远程地址
	 * @return      client
	 */
	public static <E> E sofa(Class<E> cls, InetSocketAddress addr) {
		return sofa(cls, addr.getAddress().getHostAddress(), addr.getPort());
	}

	/**
	 * 根据sofa rpc接口返回客户端
	 * 
	 * @param  <E> client
	 * @param  cls rpc接口
	 * @return     client
	 */
	public static <E> E sofa(Class<E> cls, String host, int port) {
		// 生成消费配置
		return new com.alipay.sofa.rpc.config.ConsumerConfig<E>().setInterfaceId(cls.getName()) // 指定接口
				.setProtocol(RpcParams.PROTOCOL) // 指定协议
				.setDirectUrl(U.S.add(RpcParams.PROTOCOL, "://", host, ":", port + 1))// 指定地址
				.refer();
	}

	private Sofas() {
	}
}