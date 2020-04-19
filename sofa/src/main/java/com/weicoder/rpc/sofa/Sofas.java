package com.weicoder.rpc.sofa;

import java.net.InetSocketAddress;
import java.util.Map;

import com.weicoder.rpc.annotation.Rpc;
import com.weicoder.rpc.params.RpcParams;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.weicoder.common.U;
import com.weicoder.common.U.S;
import com.weicoder.common.W.M;
import com.weicoder.common.interfaces.CallbackVoid;
import com.weicoder.common.util.StringUtil;
import com.weicoder.nacos.Nacos;

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
	 * @param  rpc rpc类
	 * @return     rpc客户端代理类
	 */
	public static <E> E one(Class<E> rpc) {
		// rpc服务地址
		Instance in = Nacos.one(getName(rpc));
		return client(rpc, in.getIp(), in.getPort());
	}

	/**
	 * 填充所有rpc接口进入map
	 * 
	 * @param  rpc rpc接口
	 * @return     K为ip:port,V为rpc client
	 */
	public static <E> Map<String, E> fill(Class<E> rpc) {
		// 声明个新的map
		Map<String, E> map = M.newMap();
		// 使用nacos处理
		nacos(getName(rpc), i -> map.put(i.toInetAddr(), client(rpc, i.getIp(), i.getPort())), i -> {
			// 服务器有效存活 检查是否已生成rpc
			String addr = i.toInetAddr();
			if (i.isEnabled() && i.isHealthy() && !map.containsKey(addr))
				// 添加到列表
				map.put(addr, client(rpc, i.getIp(), i.getPort()));
			else
				// 删除
				map.remove(addr);
		});
		// 返回map
		return map;
	}

	private static <E> void nacos(String name, CallbackVoid<Instance> select, CallbackVoid<Instance> subscribe) {
		// 启动获得所有存活rpc服务器并注册客户端
		Nacos.select(name).forEach(i -> select.callback(i));
		// 订阅rpc服务器变动 服务器变动检查状态并生成或则去处rpc服务
		Nacos.subscribe(name, list -> list.forEach(i -> subscribe.callback(i)));
	}

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
	 * 根据rpc接口生成相关代理类 执行rpc调用操作
	 * 
	 * @param  <E>
	 * @param  rpc  rpc类
	 * @param  host rpc服务器地址
	 * @param  port rpc服务器端口
	 * @return      rpc客户端代理类
	 */
	public static <E> E client(Class<E> rpc, String host, int port) {
		return client(rpc, new InetSocketAddress(host, port));
	}

	/**
	 * 根据rpc接口生成相关代理类 执行rpc调用操作
	 * 
	 * @param  <E>
	 * @param  rpc
	 * @return
	 */
	public static <E> E client(Class<E> rpc) {
		// 获得接口的服务名称
		String name = getName(rpc);
		// 返回代理对象
		return client(rpc, RpcParams.getHost(name), RpcParams.getPort(name));
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
				.setDirectUrl(StringUtil.add(RpcParams.PROTOCOL, "://", host, ":", port + 1))// 指定地址
				.refer();
	}

	private static String getName(Class<?> r) {
		// rpc服务地址
		String name = r.getAnnotation(Rpc.class).value();
		if (U.E.isEmpty(name))
			name = S.convert(r.getSimpleName(), "Rpc");
		return name;
	}

	private Sofas() {
	}
}