package com.weicoder.rpc.init;

import java.lang.reflect.Method;
import java.util.Map;

//import com.weicoder.rpc.annotation.Rpc;
import com.weicoder.rpc.annotation.RpcServer;
import com.weicoder.rpc.params.RpcParams;
import com.weicoder.common.constants.C.O;
import com.weicoder.common.init.Init;
import com.weicoder.common.lang.W; 
import com.weicoder.common.log.Logs;
import com.weicoder.common.socket.TcpServers;
import com.weicoder.common.util.U; 
import com.weicoder.common.util.U.C;

/**
 * rpc初始化
 * 
 * @author wudi
 */
public class RpcInit implements Init {
	// 保存rpc对应方法
	private final static Map<String, Method> METHODS = W.M.map();
	// 保存rpc对象
	private final static Map<String, Object> PRCS = W.M.map();
	// 回调方法对应参数
	private final static Map<String, Class<?>> PARAMES = W.M.map();
	// 记录初始化多少个rpc服务
	private static int num = 0;

	@Override
	public void init() {
		// 获得所有rpc服务
		C.list(RpcServer.class).forEach(r ->
		// 处理所有方法
		C.getPublicMethod(r).forEach(m -> {
			String name = m.getName();
			PRCS.put(name, U.C.newInstance(r));
			METHODS.put(name, m);
			PARAMES.put(name, m.getParameterCount() > 0 ? m.getParameters()[0].getType() : null);
			num++;
		}));
		// 有rpc服务 启动监听
		if (num > 0)
			start();
	}

	/**
	 * 启动rpc服务
	 */
	private void start() {
		// 启动tcp server
		TcpServers.aio(RpcParams.PORT, b -> {
			// 获得rpc方法名
			String name = W.B.toString(b, 0, true);
			// 获得方法
			Method method = METHODS.get(name);
			// 方法为空发返回空 不处理
			if (method == null) {
				Logs.info("rpc server not method={}", name);
				return null;
			}
			// 参数字节数组
			byte[] data = W.B.copy(b, name.length() + 2, b.length);
			// 获得参数
			Class<?> c = PARAMES.get(name);
			Object p = c == null ? null : W.B.to(data, c);
			// 调用方法并返回对象
			Object o = U.B.invoke(PRCS.get(name), method, p);
			Logs.info("rpc server invoke method={} parame={} result={}", name, p, o);
			return W.B.toBytes(o);
		});
		// 如果有nacos包 注册
		if (C.forName("com.weicoder.nacos.Nacos") != null)
			com.weicoder.nacos.Nacos.register(O.PROJECT_NAME, U.IP.SERVER_IP, RpcParams.PORT);
		// 启动sofa rpc服务
//		sofa();
	}

//	/**
//	 * 启动sofa rpc服务
//	 */
//	private static void sofa() {
//		// sofa包存在
//		if (C.forName("com.alipay.sofa.rpc.config.ServerConfig") != null) {
//			// 实例化一个参数
//			com.alipay.sofa.rpc.config.ServerConfig config = new com.alipay.sofa.rpc.config.ServerConfig()
//					.setProtocol(RpcParams.PROTOCOL).setPort(RpcParams.PORT + 1).setDaemon(RpcParams.DAEMON);
//			// 循环发布rpc服务
//			C.list(Rpc.class).forEach(r -> {
//				new com.alipay.sofa.rpc.config.ProviderConfig<Object>().setInterfaceId(r.getNestHost().getName()) // 指定接口
//						.setRef(U.C.newInstance(C.from(r, 0))) // 指定实现
//						.setServer(config)// 指定服务端
//						.export(); // 发布服务
//			});
//			Logs.info("rpc sofa server start success port={} protocol={}", RpcParams.PORT, RpcParams.PROTOCOL);
//		}
//	}
}
