package com.weicoder.nacos;

import java.util.List;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.weicoder.common.interfaces.CallbackVoid;
import com.weicoder.nacos.NacosConfig.Callback;
import com.weicoder.nacos.factory.NacosFactory;

/**
 * 默认空名称nacos的客户端方法 包含config与naming
 * 
 * @author wudi
 */
public final class Nacos {
	// 配置服务
	private final static NacosConfig CONFIG = NacosFactory.getConfig();
	// 注册中心
	private final static NacosNaming NAMING = NacosFactory.getNaming();

	/**
	 * 调用NacosConfig 获取配置
	 *
	 * @param  dataId dataId
	 * @return        config value
	 */
	public static String get(String dataId) {
		return CONFIG.get(dataId);
	}

	/**
	 * 调用NacosConfig 添加监听器 监听配置变化
	 *
	 * @param dataId   dataId
	 * @param listener listener
	 */
	public static void listener(String dataId, Callback call) {
		CONFIG.listener(dataId, call);
	}

	/**
	 * 调用NacosConfig 把配置推送到服务器
	 *
	 * @param  dataId  dataId
	 * @param  content content
	 * @return         Whether publish
	 */
	public static boolean publish(String dataId, String content) {
		return CONFIG.publish(dataId, content);
	}

	/**
	 * 调用NacosConfig 删除 config
	 *
	 * @param  dataId dataId
	 * @return        whether remove
	 */
	public static boolean remove(String dataId) {
		return CONFIG.remove(dataId);
	}

	/**
	 * 调用NacosNaming 注册服务
	 *
	 * @param serviceName name of service
	 * @param ip          instance ip
	 * @param port        instance port
	 */
	public static void register(String serviceName, String ip, int port) {
		NAMING.register(serviceName, ip, port);
	}

	/**
	 * 调用NacosNaming 获取所以注册服务 instances of a service
	 *
	 * @param  serviceName name of service
	 * @return             A list of instance
	 */
	public static List<Instance> all(String serviceName) {
		return NAMING.all(serviceName);
	}

	/**
	 * 调用NacosNaming 获得所有存活服务 instances of service
	 *
	 * @param  serviceName name of service
	 * @return             A qualified list of instance
	 */
	public static List<Instance> select(String serviceName) {
		return NAMING.select(serviceName);
	}

	/**
	 * 调用NacosNaming 使用负载策略或则一个存活的服务
	 *
	 * @param  serviceName name of service
	 * @return             qualified instance
	 */
	public static Instance one(String serviceName) {
		return NAMING.one(serviceName);
	}

	/**
	 * 调用NacosNaming Subscribe service to receive events of instances alteration
	 *
	 * @param serviceName name of service
	 * @param call        回调
	 */
	public static void subscribe(String serviceName, CallbackVoid<List<Instance>> call) {
		NAMING.subscribe(serviceName, call);
	}

	/**
	 * 获得NacosConfig
	 * 
	 * @return NacosConfig
	 */
	public static NacosConfig getConfig() {
		return CONFIG;
	}

	/**
	 * 获得NacosNaming
	 * 
	 * @return NacosNaming
	 */
	public static NacosNaming getNaming() {
		return NAMING;
	}

	private Nacos() {
	} 
}
