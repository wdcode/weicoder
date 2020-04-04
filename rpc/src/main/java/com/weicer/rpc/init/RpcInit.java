package com.weicer.rpc.init;

import java.lang.reflect.Method;
import java.util.Map;

import com.weicer.rpc.annotation.Rpc;
import com.weicer.rpc.annotation.RpcServer;
import com.weicer.rpc.params.RpcParams;
import com.weicoder.common.C.O;
import com.weicoder.common.U.C;
import com.weicoder.common.init.Init;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.socket.TcpServers;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.IpUtil;

/**
 * rpc初始化
 * 
 * @author wudi
 */
public class RpcInit implements Init {
	// 保存rpc对应方法
	private final static Map<String, Method> METHODS = Maps.newMap();
	// 保存rpc对象
	private final static Map<String, Object> PRCS = Maps.newMap();
	// 回调方法对应参数
	private final static Map<String, Class<?>> PARAMES = Maps.newMap();
	// 记录初始化多少个rpc服务
	private static int num = 0;

	@Override
	public void init() {
		// 获得所有rpc服务
		C.from(RpcServer.class).forEach(r ->
		// 处理所有方法
		C.getPublicMethod(r).forEach(m -> {
			String name = m.getName();
			PRCS.put(name, ClassUtil.newInstance(r));
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
			String name = Bytes.toString(b, 0, true);
			// 获得方法
			Method method = METHODS.get(name);
			// 方法为空发返回空 不处理
			if (method == null) {
				Logs.info("rpc server not method={}", name);
				return null;
			}
			// 参数字节数组
			byte[] data = Bytes.copy(b, name.length() + 2, b.length);
			// 获得参数
			Class<?> c = PARAMES.get(name);
			Object p = c == null ? null : Bytes.to(data, c);
			// 调用方法并返回对象
			Object o = BeanUtil.invoke(PRCS.get(name), method, p);
			Logs.info("rpc server invoke method={} parame={} result={}", name, p, o);
			return Bytes.toBytes(o);
		});
		// 如果有nacos包 注册
		if (C.forName("com.weicoder.nacos.Nacos") != null)
			com.weicoder.nacos.Nacos.register(O.PROJECT_NAME, IpUtil.SERVER_IP, RpcParams.PORT);
		// 启动sofa rpc服务
		sofa();
	}

	/**
	 * 启动sofa rpc服务
	 */
	private static void sofa() {
		// sofa包存在
		if (C.forName("com.alipay.sofa.rpc.config.ServerConfig") != null) {
			// 实例化一个参数
			com.alipay.sofa.rpc.config.ServerConfig config = new com.alipay.sofa.rpc.config.ServerConfig()
					.setProtocol(RpcParams.PROTOCOL).setPort(RpcParams.PORT + 1).setDaemon(RpcParams.DAEMON);
			// 循环发布rpc服务
			C.from(Rpc.class).forEach(r -> {
				new com.alipay.sofa.rpc.config.ProviderConfig<Object>().setInterfaceId(r.getCanonicalName()) // 指定接口
						.setRef(ClassUtil.newInstance(C.from(r, 0))) // 指定实现
						.setServer(config)// 指定服务端
						.export(); // 发布服务
			});
			Logs.info("rpc sofa server start success port={} protocol={}", RpcParams.PORT, RpcParams.PROTOCOL);
		}
	}

}
