package com.weicoder.redisson.builder;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;

import com.weicoder.common.util.EmptyUtil;
import com.weicoder.redis.params.RedisParams;

/**
 * Redisson 构建者
 * 
 * @author wudi
 */
public final class RedissonBuilder {
	/**
	 * 构建RedissonClient
	 * 
	 * @param  name 名
	 * @return      RedissonClient
	 */
	public static RedissonClient newBuilder(String name) {
		// 创建配置
		Config config = new Config();
		// 设置编码
		config.setCodec(new org.redisson.client.codec.StringCodec());

		// 判断使用哪个模式
		if (EmptyUtil.isEmpty(RedisParams.getCluster(name))) {
			// 使用单机
			config.useSingleServer().setAddress(RedisParams.getHost(name)).setPassword(RedisParams.getPassword(name))
					.setConnectionPoolSize(RedisParams.getMaxTotal(name));
		} else {
			// 使用集群
			ClusterServersConfig cs = config.useClusterServers();
			// 集群状态扫描间隔时间，单位是毫秒 设置密码
			cs.setScanInterval(2000).setPassword(RedisParams.getPassword(name));
			// 设置节点
			for (String node : RedisParams.getCluster(name)) {
				cs.addNodeAddress(node);
			}
		}
		// 声明RedissonClient
		return Redisson.create(config);
	}

	private RedissonBuilder() {
	}
}
