package com.weicoder.test;

import java.util.Properties;

import com.alibaba.nacos.api.NacosFactory; 
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance; 

public class NacosTest {

	public static void main(String[] args) throws Exception {
		String dataId = "";
		String group = "";
		String content = "";
		long curr = System.currentTimeMillis();
		Listener listener = null;
		// 方式一
		String addr = "127.0.0.1:8848";
		ConfigService config = NacosFactory.createConfigService(addr);
		config.addListener(dataId, group, listener);
		config.publishConfig(dataId, group, content);
		config.getConfig(dataId, group, curr);

		// 方式二
		Properties properties = new Properties();
		properties.put("serverAddr", addr);
		ConfigService configService = NacosFactory.createConfigService(properties);
		configService.addListener(dataId, group, listener);
		configService.publishConfig(dataId, group, content);
		configService.getConfig(dataId, group, curr);
		
		
		// 方式一 
		NamingService naming = NacosFactory.createNamingService(addr);

		// 方式二
		NamingService namingService = NacosFactory.createNamingService(properties);
		
		//方式一：
		String serverIp = "127.0.0.1";
		int serverPort = 8848;
//		String serverAddr = serverIp + ":" + serverPort;
		String serviceName = "nacos-sdk-java-discovery"; 
		naming.registerInstance(serviceName, serverIp, serverPort);
		Instance i = naming.getAllInstances(serviceName).get(0);
		System.err.println(i.getClusterName());
		
		//方式二：
		Instance instance = new Instance();
		instance.setIp(serverIp);//IP
		instance.setPort(serverPort);//端口
		instance.setServiceName(serviceName);//服务名
		instance.setEnabled(true);//true: 上线 false: 下线
		instance.setHealthy(true);//健康状态
		instance.setWeight(1.0);//权重
		instance.addMetadata("nacos-sdk-java-discovery", "true");//元数据 
		namingService.registerInstance(serviceName, instance);
		
		 
	}
}
