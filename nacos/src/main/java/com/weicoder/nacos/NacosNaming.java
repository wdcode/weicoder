package com.weicoder.nacos;

import java.util.List;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.nacos.params.NacosParams;

/**
 * alibaba nacos client
 * 
 * @author wudi
 */
public class NacosNaming {
	// 默认组
	private final static String GROUP = Constants.DEFAULT_GROUP;
	// 注册中心
	private NamingService naming;

	/**
	 * 根据名称构造
	 * 
	 * @param name 读取配置名称
	 */
	public NacosNaming(String name) {
		try {
			naming = NacosFactory.createNamingService(NacosParams.getAddr(name));
		} catch (NacosException e) {
			Logs.error(e);
		}
	}

	/**
	 * 注册服务
	 *
	 * @param serviceName name of service
	 * @param ip          instance ip
	 * @param port        instance port
	 */
	public void register(String serviceName, String ip, int port) {
		register(serviceName, GROUP, ip, port);
	}

	/**
	 * 注册服务
	 *
	 * @param serviceName name of service
	 * @param groupName   group of service
	 * @param ip          instance ip
	 * @param port        instance port
	 */
	public void register(String serviceName, String groupName, String ip, int port) {
		try {
			naming.registerInstance(serviceName, groupName, ip, port);
		} catch (NacosException e) {
			Logs.error(e);
		}
	}

	/**
	 * 获取所以注册服务 instances of a service
	 *
	 * @param  serviceName name of service
	 * @return             A list of instance
	 */
	public List<Instance> all(String serviceName) {
		return all(serviceName, GROUP);
	}

	/**
	 * 获取所以注册服务 instances of a service
	 *
	 * @param  serviceName name of service
	 * @param  groupName   group of service
	 * @return             A list of instance
	 */
	public List<Instance> all(String serviceName, String groupName) {
		try {
			return naming.getAllInstances(serviceName, groupName);
		} catch (NacosException e) {
			return Lists.emptyList();
		}
	}

	/**
	 * 获得所有存活服务 instances of service
	 *
	 * @param  serviceName name of service
	 * @return             A qualified list of instance
	 */
	public List<Instance> select(String serviceName) {
		return select(serviceName, true);
	}

	/**
	 * Get qualified instances of service
	 *
	 * @param  serviceName name of service
	 * @param  healthy     获取存活还是死亡服务
	 * @return             A qualified list of instance
	 */
	public List<Instance> select(String serviceName, boolean healthy) {
		return select(serviceName, GROUP, healthy);
	}

	/**
	 * Get qualified instances of service
	 *
	 * @param  serviceName name of service
	 * @param  groupName   group of service
	 * @param  healthy     a flag to indicate returning healthy or unhealthy instances
	 * @return             A qualified list of instance
	 */
	public List<Instance> select(String serviceName, String groupName, boolean healthy) {
		try {
			return naming.selectInstances(serviceName, groupName, healthy);
		} catch (NacosException e) {
			return Lists.emptyList();
		}
	}

	/**
	 * 使用负载策略或则一个存活的服务
	 *
	 * @param  serviceName name of service
	 * @return             qualified instance
	 */
	public Instance one(String serviceName) {
		return one(serviceName, GROUP);
	}

	/**
	 * 使用负载策略或则一个存活的服务
	 *
	 * @param  serviceName name of service
	 * @param  groupName   group of service
	 * @return             qualified instance
	 */
	public Instance one(String serviceName, String groupName) {
		try {
			return naming.selectOneHealthyInstance(serviceName, groupName);
		} catch (NacosException e) {
			return null;
		}
	}

	/**
	 * Subscribe service to receive events of instances alteration
	 *
	 * @param serviceName name of service
	 * @param call        回调
	 */
	public void subscribe(String serviceName, Callback<List<Instance>> call) {
		subscribe(serviceName, GROUP, call);
	}

	/**
	 * Subscribe service to receive events of instances alteration
	 *
	 * @param serviceName name of service
	 * @param groupName   group of service
	 * @param call        回调
	 */
	public void subscribe(String serviceName, String groupName, Callback<List<Instance>> call) {
		try {
			naming.subscribe(serviceName, groupName, e -> call.callback(((NamingEvent) e).getInstances()));
		} catch (NacosException e) {
			Logs.error(e);
		}
	}
}
