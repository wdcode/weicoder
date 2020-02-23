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
	private final static Map<Class<?>, InetSocketAddress> ADDRESS_CLASS = Maps.newMap();

	/**
	 * 根据rpc接口生成相关代理类 执行rpc调用操作
	 * 
	 * @param  <E>
	 * @param  rpc
	 * @return
	 */
	public static <E> E newRpc(Class<E> rpc) {
		return ProxyUtil.newProxyInstance(rpc, new JDKInvocationHandler(ADDRESS_CLASS.get(rpc)));
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
		// 为空返回null
		if (rpc == null)
			return null;
		// 调用rpc
		return rpc(rpc.name(), rpc.method(), obj);
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
	 * 初始化
	 */
	public static void init() {
		// 循环处理rpc服务
		ClassUtil.getAnnotationClass(CommonParams.getPackages("rpc"), Rpc.class).forEach(r -> {
			// rpc服务地址
			String addr = r.getAnnotation(Rpc.class).value();
			if (EmptyUtil.isEmpty(addr))
				addr = StringUtil.convert(r.getSimpleName());
			InetSocketAddress isa = new InetSocketAddress(RpcParams.getHost(addr), RpcParams.getPort(addr));
			ADDRESS.put(addr, isa);
			ADDRESS_CLASS.put(r, isa);
			// 处理所有方法
			ClassUtil.getPublicMethod(r).forEach(m -> {
				String name = m.getName();
				METHODS.put(name, m);
				RESULTS.put(name, m.getReturnType());
			});
		});
	}

	private RpcClients() {
	}
}