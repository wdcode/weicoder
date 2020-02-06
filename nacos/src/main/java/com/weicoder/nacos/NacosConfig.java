package com.weicoder.nacos;

import java.util.concurrent.Executor;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.weicoder.common.concurrent.ExecutorUtil;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.log.Logs;
import com.weicoder.nacos.params.NacosParams;

/**
 * alibaba nacos client
 * 
 * @author wudi
 */
public class NacosConfig {
	// 默认组
	private final static String GROUP = Constants.DEFAULT_GROUP;
	// 配置中心
	private ConfigService config;

	/**
	 * 根据名称构造
	 * 
	 * @param name 读取配置名称
	 */
	public NacosConfig(String name) {
		try {
			config = NacosFactory.createConfigService(NacosParams.getAddr(name));
		} catch (NacosException e) {
			Logs.error(e);
		}
	}

	/**
	 * 获取配置
	 *
	 * @param  dataId dataId
	 * @return        config value
	 */
	public String get(String dataId) {
		return get(dataId, GROUP, DateConstants.TIME_SECOND);
	}

	/**
	 * 获取配置
	 *
	 * @param  dataId dataId
	 * @param  group  group
	 * @return        config value
	 */
	public String get(String dataId, String group) {
		return get(dataId, group, DateConstants.TIME_SECOND);
	}

	/**
	 * 获取配置
	 *
	 * @param  dataId    dataId
	 * @param  group     group
	 * @param  timeoutMs read timeout
	 * @return           config value
	 */
	public String get(String dataId, String group, long timeoutMs) {
		try {
			return config.getConfig(dataId, group, timeoutMs);
		} catch (NacosException e) {
			return StringConstants.EMPTY;
		}
	}

	/**
	 * 添加监听器 监听配置变化
	 *
	 * @param dataId   dataId
	 * @param listener listener
	 */
	public void listener(String dataId, Callback call) {
		listener(dataId, GROUP, call);
	}

	/**
	 * 添加监听器 监听配置变化
	 *
	 * @param dataId   dataId
	 * @param group    group
	 * @param listener listener
	 */
	public void listener(String dataId, String group, Callback call) {
		try {
			config.addListener(dataId, group, new Listener() {
				@Override
				public void receiveConfigInfo(String configInfo) {
					call.callback(configInfo);
				}

				@Override
				public Executor getExecutor() {
					return ExecutorUtil.pool();
				}
			});
		} catch (NacosException e) {
			Logs.error(e);
		}
	}

	/**
	 * 把配置推送到服务器
	 *
	 * @param  dataId  dataId
	 * @param  content content
	 * @return         Whether publish
	 */
	public boolean publish(String dataId, String content) {
		return publish(dataId, GROUP, content);
	}

	/**
	 * 把配置推送到服务器
	 *
	 * @param  dataId  dataId
	 * @param  group   group
	 * @param  content content
	 * @return         Whether publish
	 */
	public boolean publish(String dataId, String group, String content) {
		try {
			return config.publishConfig(dataId, group, content);
		} catch (NacosException e) {
			return false;
		}
	}

	/**
	 * 删除 config
	 *
	 * @param  dataId dataId
	 * @return        whether remove
	 */
	public boolean remove(String dataId) {
		return remove(dataId, GROUP);
	}

	/**
	 * 删除 config
	 *
	 * @param  dataId dataId
	 * @param  group  group
	 * @return        whether remove
	 */
	public boolean remove(String dataId, String group) {
		try {
			return config.removeConfig(dataId, group);
		} catch (NacosException e) {
			return false;
		}
	}

	/**
	 * 监听回调
	 * 
	 * @author wudi
	 */
	public interface Callback {
		/**
		 * 回调方法
		 * 
		 * @param result 结果
		 */
		void callback(String result);
	}
}
