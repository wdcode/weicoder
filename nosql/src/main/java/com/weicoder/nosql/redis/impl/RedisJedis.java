package com.weicoder.nosql.redis.impl;

import java.util.List;
import java.util.Map;

import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.nosql.redis.base.BaseRedis;
import com.weicoder.nosql.params.RedisParams;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Protocol;

/**
 * Redis客户端Jedis实现
 * @author WD
 */
public final class RedisJedis extends BaseRedis {
	// Jedis连接池
	private JedisPool pool;

	public RedisJedis(String name) {
		// 实例化Jedis配置
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置属性
		config.setMaxTotal(RedisParams.getMaxTotal(name));
		config.setMaxIdle(RedisParams.getMaxIdle(name));
		config.setMaxWaitMillis(RedisParams.getMaxWait(name));
		// 实例化连接池
		pool = new JedisPool(config, RedisParams.getHost(name), RedisParams.getPort(name), Protocol.DEFAULT_TIMEOUT,
				EmptyUtil.isEmpty(RedisParams.getPassword(name)) ? null : RedisParams.getPassword(name), 0, null);
	}

	@Override
	public Jedis getResource() {
		return pool.getResource();
	}

	@Override
	public long rpush(String key, String... strings) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.rpush(key, strings);
		}
	}

	@Override
	public long llen(String key) {
		try (Jedis jedis = pool.getResource()) {
			return Conversion.toLong(jedis.llen(key));
		}
	}

	@Override
	public String lpop(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.lpop(key);
		}
	}

	@Override
	public String set(String key, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.set(key, value);
		}
	}

	@Override
	public long hset(String key, String field, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hset(key, field, value);
		}
	}

	@Override
	public String set(byte[] key, byte[] value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.set(key, value);
		}
	}

	@Override
	public String setex(String key, int seconds, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.setex(key, seconds, value);
		}
	}

	@Override
	public long hsetnx(String key, String field, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hsetnx(key, field, value);
		}
	}

	@Override
	public String get(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.get(key);
		}
	}

	@Override
	public byte[] get(byte[] key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.get(key);
		}
	}

	/**
	 * 删除键值
	 * @param key 键
	 */
	public long del(String... key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.del(key);
		}
	}

	/**
	 * 验证键是否存在
	 * @param key 键
	 * @return true 存在 false 不存在
	 */
	public boolean exists(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.exists(key);
		}
	}

	@Override
	public long append(String key, Object value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.append(Bytes.toBytes(key), Bytes.toBytes(value));
		}
	}

	@Override
	public long ttl(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.ttl(key);
		}
	}

	@Override
	public boolean hexists(String key, String field) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hexists(key, field);
		}
	}

	@Override
	public String hget(String key, String field) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hget(key, field);
		}
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hgetAll(key);
		}
	}

	@Override
	public long hdel(String key, String... field) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hdel(key, field);
		}
	}

	@Override
	public void subscribe(final JedisPubSub jedisPubSub, final String... channels) {
		try (Jedis jedis = pool.getResource()) {
			jedis.subscribe(jedisPubSub, channels);
		}
	}

	@Override
	public List<byte[]> mget(byte[][] key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.mget(key);
		}
	}
}