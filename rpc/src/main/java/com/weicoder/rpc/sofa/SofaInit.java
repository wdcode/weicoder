package com.weicoder.rpc.sofa;
 
import com.weicoder.rpc.annotation.RpcServer;
import com.weicoder.rpc.params.RpcParams;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.weicoder.common.U.C;
import com.weicoder.common.init.Init;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.ClassUtil;

/**
 * rpc初始化
 * 
 * @author wudi
 */
public class SofaInit implements Init {
	@Override
	public void init() {
		// 实例化一个参数
		ServerConfig config = new ServerConfig().setProtocol(RpcParams.PROTOCOL).setPort(RpcParams.PORT + 1)
				.setDaemon(RpcParams.DAEMON);
		// 循环发布rpc服务
		C.list(RpcServer.class).forEach(r -> new ProviderConfig<Object>().setInterfaceId(r.getName()) // 指定接口
				.setRef(ClassUtil.newInstance(C.from(r, 0))) // 指定实现
				.setServer(config)// 指定服务端
				.export() // 发布服务
		);
		Logs.info("rpc sofa server start success port={} protocol={}", RpcParams.PORT, RpcParams.PROTOCOL);
	}
}
