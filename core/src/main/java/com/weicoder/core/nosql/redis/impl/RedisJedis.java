package com.weicoder.core.nosql.redis.impl;

import com.weicoder.common.lang.Bytes;
import com.weicoder.core.nosql.base.BaseNoSQL;
import com.weicoder.core.nosql.redis.Redis;
import com.weicoder.core.params.RedisParams;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis客户端Jedis实现
 * @author WD
 * @since JDK7
 * @version 1.0 2011-06-23
 */
public final class RedisJedis extends BaseNoSQL implements Redis {
	// Jedis连接池
	private JedisPool	pool;

	public RedisJedis(String name) {
		// 实例化Jedis配置
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置属性
		config.setMaxTotal(RedisParams.getMaxTotal(name));
		config.setMaxIdle(RedisParams.getMaxIdle(name));
		config.setMaxWaitMillis(RedisParams.getMaxWait(name));
		// 实例化连接池
		pool = new JedisPool(config, RedisParams.getHost(name), RedisParams.getPort(name));
	}

	/**
	 * 设置键值 无论存储空间是否存在相同键 都保存 对象将变成字节数组村储存 需要对象能序列化或则能转变为json
	 * @param key 键
	 * @param value 值
	 */
	public boolean set(String key, Object value) {
		// 获得Jedis对象
		Jedis jedis = pool.getResource();
		// 设置值
		jedis.set(Bytes.toBytes(key), Bytes.toBytes(value));
		// 回收Jedis
		pool.returnResource(jedis);
		// 返回成功
		return true;
	}

	/**
	 * 根据键获得值 值都是字节数组
	 * @param key 键
	 * @return 值
	 */
	public byte[] get(String key) {
		// 获得Jedis对象
		Jedis jedis = pool.getResource();
		// 设置值
		byte[] b = jedis.get(Bytes.toBytes(key));
		// 回收Jedis
		pool.returnResource(jedis);
		// 返回值
		return b;
	}

	/**
	 * 删除键值
	 * @param key 键
	 */
	public void remove(String... key) {
		// 获得Jedis对象
		Jedis jedis = pool.getResource();
		// 设置值
		jedis.del(key);
		// 回收Jedis
		pool.returnResource(jedis);
	}

	/**
	 * 验证键是否存在
	 * @param key
	 * @return true 存在 false 不存在
	 */
	public boolean exists(String key) {
		// 获得Jedis对象
		Jedis jedis = pool.getResource();
		// 是否存在
		boolean b = jedis.exists(Bytes.toBytes(key));
		// 回收Jedis
		pool.returnResource(jedis);
		// 返回值
		return b;
	}

	@Override
	public boolean append(String key, Object value) {
		// 获得Jedis对象
		Jedis jedis = pool.getResource();
		// 设置值
		jedis.append(Bytes.toBytes(key), Bytes.toBytes(value));
		// 回收Jedis
		pool.returnResource(jedis);
		// 返回成功
		return true;
	}

	@Override
	public void close() {
		pool.destroy();
	}

	@Override
	public void clear() {
		// 获得Jedis对象
		Jedis jedis = pool.getResource();
		// 清除
		jedis.flushAll();
		// 回收Jedis
		pool.returnResource(jedis);
	}
}