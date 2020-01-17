package com.weicoder.test;
 
import java.util.concurrent.Executor;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.naming.NamingService; 
import com.weicoder.common.concurrent.ExecutorUtil;
import com.weicoder.common.constants.DateConstants;

public class NacosTest {

	public static void main(String[] args) throws Exception {
		String ip = "172.18.77.135";
		int port = 8848;
		String addr = ip + ":" + port;
		String dataId = "cf.test";
		String group = "user";
		String content = "test123321";
		long curr = DateConstants.TIME_MINUTE;
		Listener listener = new Listener() {
			@Override
			public void receiveConfigInfo(String configInfo) {
				System.out.println(configInfo);
			}

			@Override
			public Executor getExecutor() {
				return ExecutorUtil.pool();
			}
		};
		// 配置
		ConfigService config = NacosFactory.createConfigService(addr);
		config.addListener(dataId, group, listener);
		config.publishConfig(dataId, group, content);
		System.out.println(config.getConfig(dataId, group, curr));

		// 注册
		NamingService naming = NacosFactory.createNamingService(addr);

		// 方式一： 
		naming.registerInstance(group, ip, port);
		naming.getAllInstances(group).forEach(i->System.out.println(i)); 
	}
}
