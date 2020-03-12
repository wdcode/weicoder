package com.weicer.rpc;

import java.lang.reflect.Method;
import java.util.Map;

import com.weicer.rpc.annotation.Rpc;
import com.weicer.rpc.params.RpcParams;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.socket.TcpServers;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;

/**
 * rpc服务端
 * 
 * @author wudi
 */
public final class RpcServers {
	// 保存rpc对象
	private final static Map<String, Object> PRCS = Maps.newMap();
	// 保存rpc对应方法
	private final static Map<String, Method> METHODS = Maps.newMap();
	// 回调方法对应参数
	private final static Map<String, Class<?>> PARAMES = Maps.newMap();

	/**
	 * 初始化
	 */
	public static void init() {
		// 获得所有rpc服务
		ClassUtil.getAnnotationClass(CommonParams.getPackages("rpc"), Rpc.class).forEach(r -> {
			// 获得rpc接口实现类
			Class<?> c = ClassUtil.getAssignedClass(r, 0);
			// 处理所有方法
			ClassUtil.getPublicMethod(c).forEach(m -> {
				String name = m.getName();
				PRCS.put(name, ClassUtil.newInstance(c));
				METHODS.put(name, m);
				PARAMES.put(name, m.getParameterCount() > 0 ? m.getParameters()[0].getType() : null);
			});
		});
		Logs.info("rpc jdk server init success");
	}

	/**
	 * 启动rpc服务
	 */
	public static void start() {
		// 初始化
		init();
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
//			// 获得参数
			Class<?> c = PARAMES.get(name);
			Object p = c == null ? null : Bytes.to(data, c);
			// 调用方法并返回对象
			Object o = BeanUtil.invoke(PRCS.get(name), method, p);
			Logs.info("rpc server invoke method={} parame={} result={}", name, p, o);
			return Bytes.toBytes(o);
		});
		Logs.info("rpc jdk server start success port={}", RpcParams.PORT);
		// 启动sofa rpc服务
		sofa();
	}

	/**
	 * 启动sofa rpc服务
	 */
	public static void sofa() {
		// sofa包存在
		if (ClassUtil.forName("com.alipay.sofa.rpc.config.ServerConfig") != null) {
			// 实例化一个参数
			com.alipay.sofa.rpc.config.ServerConfig config = new com.alipay.sofa.rpc.config.ServerConfig()
					.setProtocol(RpcParams.PROTOCOL).setPort(RpcParams.PORT + 1).setDaemon(RpcParams.DAEMON);
			// 循环发布rpc服务
			ClassUtil.getAnnotationClass(CommonParams.getPackages("rpc"), Rpc.class).forEach(r -> {
				new com.alipay.sofa.rpc.config.ProviderConfig<Object>().setInterfaceId(r.getNestHost().getName()) // 指定接口
						.setRef(ClassUtil.newInstance(ClassUtil.getAssignedClass(r, 0))) // 指定实现
						.setServer(config)// 指定服务端
						.export(); // 发布服务
			});
			Logs.info("rpc sofa server start success port={} protocol={}", RpcParams.PORT, RpcParams.PROTOCOL);
		}
	}

	private RpcServers() {
	}
}