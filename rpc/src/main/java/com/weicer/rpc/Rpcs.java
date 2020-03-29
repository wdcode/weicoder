package com.weicer.rpc;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import com.weicer.rpc.annotation.Rpc;
import com.weicer.rpc.annotation.RpcBean;
import com.weicer.rpc.params.RpcParams;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.socket.TcpClient;
import com.weicoder.common.U;
import com.weicoder.common.U.B;
import com.weicoder.common.U.C;
import com.weicoder.common.U.S;
import com.weicoder.common.W.L;
import com.weicoder.common.W.M;
import com.weicoder.common.interfaces.CallbackVoid;
import com.weicoder.common.util.StringUtil;

/**
 * rpc客户端
 * 
 * @author wudi
 */
public final class Rpcs {
	// 保存rpc对应方法
	private final static Map<String, Method> METHODS = Maps.newMap();
	// 回调方法对应参数
	private final static Map<String, Class<?>> RESULTS = Maps.newMap();
	// rpc调用地址
	private final static Map<String, InetSocketAddress> ADDRESS = Maps.newMap();
	// 保存nacos rpc address
	private final static Map<String, List<InetSocketAddress>> RPCS = Maps.newMap();

	static {
		// 循环处理rpc服务
		C.from(Rpc.class).forEach(r -> {
			// rpc服务地址
			String name = getName(r);
			ADDRESS.put(name, new InetSocketAddress(RpcParams.getHost(name), RpcParams.getPort(name)));
			// 如果有nacos
			List<InetSocketAddress> ls = M.getList(RPCS, name);
			// 使用nacos处理
			nacos(name, i -> ls.add(new InetSocketAddress(i.getIp(), i.getPort())), i -> {
				// 服务器有效存活 检查是否已生成rpc
				InetSocketAddress in = new InetSocketAddress(i.getIp(), i.getPort());
				if (i.isEnabled() && i.isHealthy() && ls.contains(in))
					// 添加到列表
					ls.add(in);
				else
					// 删除
					ls.remove(in);
			});
			// 处理所有方法
			C.getPublicMethod(r).forEach(m -> {
				String mn = m.getName();
				METHODS.put(mn, m);
				RESULTS.put(mn, m.getReturnType());
			});
		});
	}

	/**
	 * 根据rpc接口生成相关代理类 执行rpc调用操作
	 * 
	 * @param  <E>
	 * @param  rpc rpc类
	 * @return     rpc客户端代理类
	 */
	public static <E> E one(Class<E> rpc) {
		// rpc服务地址
		String name = getName(rpc);
		if (C.forName("com.weicoder.nacos.Nacos") != null) {
			com.alibaba.nacos.api.naming.pojo.Instance in = com.weicoder.nacos.Nacos.one(name);
			return client(rpc, in.getIp(), in.getPort());
		} else {
			InetSocketAddress in = RPCS.get(name).get(U.M.nextInt(RPCS.size()));
			return client(rpc, in.getAddress().getHostAddress(), in.getPort());
		}
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

	private static <E> void nacos(String name, CallbackVoid<com.alibaba.nacos.api.naming.pojo.Instance> select,
			CallbackVoid<com.alibaba.nacos.api.naming.pojo.Instance> subscribe) {
		// 判断是否用Nacos
		if (C.forName("com.weicoder.nacos.Nacos") != null) {
			// 启动获得所有存活rpc服务器并注册客户端
			com.weicoder.nacos.Nacos.select(name).forEach(i -> select.callback(i));
			// 订阅rpc服务器变动 服务器变动检查状态并生成或则去处rpc服务
			com.weicoder.nacos.Nacos.subscribe(name, list -> list.forEach(i -> subscribe.callback(i)));
		}
	}

	/**
	 * 根据rpc接口生成相关代理类 执行rpc调用操作
	 * 
	 * @param  <E>
	 * @param  rpc rpc类
	 * @return     rpc客户端代理类
	 */
	public static <E> E all(Class<E> rpc) {
		// 声明出所有rpc
		List<E> rs = L.newList();
		RPCS.get(getName(rpc)).forEach(i -> rs.add(client(rpc, i)));
		// 使用jdk代理调用所有rpc
		return U.C.newProxyInstance(rpc, (proxy, method, args) -> {
			List<Object> res = L.newList();
			rs.forEach(r -> res.add(B.invoke(r, method, args[0])));
			return res;
		});
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
		// 有sofa包返回 sofa client
		if (C.forName("com.alipay.sofa.rpc.config.ConsumerConfig") != null)
			return sofa(rpc, addr);
		// 使用jdk调用
		return U.C.newProxyInstance(rpc, (proxy, method, args) -> rpc(addr, method.getName(), args[0]));
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

	private Rpcs() {
	}
}