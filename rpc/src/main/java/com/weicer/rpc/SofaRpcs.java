package com.weicer.rpc;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.weicer.rpc.annotation.Rpc;
import com.weicer.rpc.params.RpcParams;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;

/**
 * 蚂蚁金服 sofa rpc实现
 * 
 * @author wudi
 */
public final class SofaRpcs {
	/**
	 * 启动rpc服务
	 */
	public static void start() {
		// 实例化一个参数
		ServerConfig config = new ServerConfig().setProtocol(RpcParams.PROTOCOL).setPort(RpcParams.PORT)
				.setDaemon(RpcParams.DAEMON);
		// 循环发布rpc服务
		ClassUtil.getAnnotationClass(CommonParams.getPackages("rpc"), Rpc.class).forEach(r -> {
			new ProviderConfig<Object>().setInterfaceId(r.getNestHost().getName()) // 指定接口
					.setRef(BeanUtil.newInstance(ClassUtil.getAssignedClass(r, 0))) // 指定实现
					.setServer(config)// 指定服务端
					.export(); // 发布服务
		});
	}

	/**
	 * 根据rpc接口返回客户端
	 * 
	 * @param  <E> client
	 * @param  cls rpc接口
	 * @return     client
	 */
	public static <E> E client(Class<E> cls) {
		// 获得接口的服务名称
		String name = cls.getAnnotation(Rpc.class).value();
		// 生成消费配置
		return new ConsumerConfig<E>().setInterfaceId(cls.getName()) // 指定接口
				.setProtocol(RpcParams.PROTOCOL) // 指定协议
				.setDirectUrl(RpcParams.PROTOCOL + "://" + RpcParams.getHost(name) + ":" + RpcParams.getPort(name))// 指定地址
				.refer();
	}

	private SofaRpcs() {
	}
}