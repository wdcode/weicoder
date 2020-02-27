package com.weicer.rpc;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Map;

import com.weicer.rpc.annotation.Rpc;
import com.weicer.rpc.annotation.RpcBean;
import com.weicer.rpc.params.RpcParams;
import com.weicer.rpc.proxy.JDKInvocationHandler;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.socket.TcpClient;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.ProxyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * rpc客户端
 * 
 * @author wudi
 */
public final class RpcClients {
	// 保存rpc对应方法
	private final static Map<String, Method> METHODS = Maps.newMap();
	// 回调方法对应参数
	private final static Map<String, Class<?>> RESULTS = Maps.newMap();
	// rpc调用地址
	private final static Map<String, InetSocketAddress>   ADDRESS       = Maps.newMap(); 
	// 标识是否初始化
	private static boolean INIT = true;
	static {
		init();
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
		// 有sofa包返回 sofa client
		if (ClassUtil.forName("com.alipay.sofa.rpc.config.ConsumerConfig") != null)
			return sofa(rpc, host, port);
		// 使用jdk调用
		return ProxyUtil.newProxyInstance(rpc, new JDKInvocationHandler(new InetSocketAddress(host, port)));
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
		String name = rpc.getAnnotation(Rpc.class).value();
		// 返回代理对象
		return client(rpc, RpcParams.getHost(name), RpcParams.getPort(name));
	}

	/**
	 * rpc调用 实体类必须是rpcbean注解的 否则返回null
	 * 
	 * @param  rpc 调用rpc bean
	 * @return     返回相关对象
	 */
	public static Object rpc(Object obj) {
		// 获得对象的RPC bean
		RpcBean rpc = obj.getClass().getAnnotation(RpcBean.class);
		// 为空返回null 不为空调用rpc
		return rpc == null ? null : rpc(rpc.name(), rpc.method(), obj);
	}

	/**
	 * rpc调用
	 * 
	 * @param  name   调用rpc名称
	 * @param  method 调用方法
	 * @param  param  调用参数
	 * @return        返回相关对象
	 */
	public static Object rpc(String name, String method, Object param) {
		return rpc(ADDRESS.get(name), method, param);
	}

	/**
	 * rpc调用
	 * 
	 * @param  addr   调用rpc地址
	 * @param  method 调用方法
	 * @param  param  调用参数
	 * @return        返回相关对象
	 */
	public static Object rpc(InetSocketAddress addr, String method, Object param) {
		return Bytes.to(TcpClient.asyn(addr, Bytes.toBytes(true, method, param), true), RESULTS.get(method));
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

	/**
	 * 初始化
	 */
	public static void init() {
		// 是否需要初始化
		if (INIT) {
			// 循环处理rpc服务
			ClassUtil.getAnnotationClass(CommonParams.getPackages("rpc"), Rpc.class).forEach(r -> {
				// rpc服务地址
				String addr = r.getAnnotation(Rpc.class).value();
				if (EmptyUtil.isEmpty(addr))
					addr = StringUtil.convert(r.getSimpleName()); 
				ADDRESS.put(addr, new InetSocketAddress(RpcParams.getHost(addr), RpcParams.getPort(addr)));
				// 处理所有方法
				ClassUtil.getPublicMethod(r).forEach(m -> {
					String name = m.getName();
					METHODS.put(name, m);
					RESULTS.put(name, m.getReturnType());
				});
			});
			INIT = false;
		}
	}

	private RpcClients() {
	}
}