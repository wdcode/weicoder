package com.weicoder.core.nosql.redis.impl;

import org.redisson.Config;
import org.redisson.Redisson;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.core.nosql.base.BaseNoSQL;
import com.weicoder.core.nosql.redis.Redis;
import com.weicoder.core.params.RedisParams;

/**
 * Redis客户端Jedis实现
 * @author WD
 * @since JDK7
 * @version 1.0 2011-06-23
 */
public final class RedisRedisson extends BaseNoSQL implements Redis {
	// Jedis连接池
	private Redisson	client;

	public RedisRedisson(String name) {
		// 实例化Jedis配置
		Config config = new Config();
		// 设置属性
		config.setConnectionPoolSize(RedisParams.getMaxTotal(name));
		config.addAddress(RedisParams.getHost(name) + StringConstants.COLON + RedisParams.getPort(name));
		// 实例化连接池
		client = Redisson.create(config);
	}

	/**
	 * 设置键值 无论存储空间是否存在相同键 都保存 对象将变成字节数组村储存 需要对象能序列化或则能转变为json
	 * @param key 键
	 * @param value 值
	 */
	public boolean set(String key, Object value) {
		// 返回成功
		return true;
	}

	/**
	 * 根据键获得值 值都是字节数组
	 * @param key 键
	 * @return 值
	 */
	public byte[] get(String key) {
		// 返回值
		return null;
	}

	/**
	 * 删除键值
	 * @param key 键
	 */
	public void remove(String... key) {}

	/**
	 * 验证键是否存在
	 * @param key
	 * @return true 存在 false 不存在
	 */
	public boolean exists(String key) {
		// 返回值
		return true;
	}

	@Override
	public boolean append(String key, Object value) {
		// 返回成功
		return true;
	}

	@Override
	public void close() {
		client.shutdown();
	}

	@Override
	public void clear() {}
}