package com.weicer.rpc;

import java.lang.reflect.Method; 
import java.util.Map;

import com.weicer.rpc.annotation.RpcServer;
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
		ClassUtil.getAnnotationClass(CommonParams.getPackages("rpc"), RpcServer.class).forEach(r -> {
			// 处理所有方法
			ClassUtil.getPublicMethod(r).forEach(m -> { 
					String name = m.getName();
					PRCS.put(name, BeanUtil.newInstance(r));
					METHODS.put(name, m);
					PARAMES.put(name, m.getParameterCount() > 0 ? m.getParameters()[0].getType() : null); 
			});
		});
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
	}

	private RpcServers() {
	}
}
