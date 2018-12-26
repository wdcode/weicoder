package com.weicoder.nosql.redis.builder;

import java.util.List;

import com.weicoder.common.lang.Lists;
import com.weicoder.nosql.params.RedisParams;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI; 
import io.lettuce.core.cluster.RedisClusterClient; 

/**
 * Lettuce 构建者
 * 
 * @author wudi
 *
 */
public final class LettuceBuilder {
	/**
	 * 构建Lettuce集群
	 * 
	 * @param name 名
	 * @return Lettuce集群
	 */
	public static RedisClusterClient buildCluster(String name) {
		// 设置集群
		List<RedisURI> list = Lists.newList();
		// 循环设置
		for (String uri : RedisParams.getUri(name))
			list.add(RedisURI.create(uri));
		// 返回集群
		return RedisClusterClient.create(list);
	}

	/**
	 * 构建Lettuce单机
	 * 
	 * @param name 名
	 * @return Lettuce集群
	 */
	public static RedisClient buildPool(String name) {
		return RedisClient.create(RedisParams.getUri(name)[0]);
	}

	private LettuceBuilder() {
	}
}
