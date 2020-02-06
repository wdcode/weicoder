package com.weicoder.test;
  
import com.weicoder.common.constants.DateConstants;
import com.weicoder.nacos.NacosConfig;
import com.weicoder.nacos.NacosNaming;
import com.weicoder.nacos.factory.NacosFactory;

public class NacosTest {

	public static void main(String[] args) throws Exception {
		String ip = "172.18.77.135";
		int port = 8848; 
		String dataId = "cf.test";
		String group = "user";
		String content = "test123321";
		long curr = DateConstants.TIME_MINUTE;
		// 配置
		NacosConfig config = NacosFactory.getConfig("");
		config.listener(dataId, group, s -> System.out.println(s));
		config.publish(dataId, group, content);
		System.out.println(config.get(dataId, group, curr));

		// 注册
		NacosNaming naming = NacosFactory.getNaming("");

		// 方式一：
		naming.register(group, ip, port);
		naming.all(group).forEach(i -> System.out.println(i));
	}
}
