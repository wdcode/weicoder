package com.weicoder.nosql.redis.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.util.StringUtil;
import com.weicoder.nosql.params.RedisParams;
import com.weicoder.nosql.redis.base.BaseRedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

/**
 * Redis客户端Jedis实现
 * @author WD
 */
public final class RedisCluster extends BaseRedis {
	// Jedis集群
	private JedisCluster cluster;

	public RedisCluster(String name) {
		// 实例化Jedis配置
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置属性
		config.setMaxTotal(RedisParams.getMaxTotal(name));
		config.setMaxIdle(RedisParams.getMaxIdle(name));
		config.setMaxWaitMillis(RedisParams.getMaxWait(name));
		// 服务器节点
		Set<HostAndPort> nodes = Sets.newSet();
		for (String server : RedisParams.getCluster(name)) {
			String[] s = StringUtil.split(server, StringConstants.COLON);
			nodes.add(new HostAndPort(s[0], Conversion.toInt(s[1])));
		}
		// 生成JedisCluster
		cluster = new JedisCluster(nodes, config);
	}

	@Override
	public Jedis getResource() {
		return null;
	}

	@Override
	public long rpush(String key, String... strings) {
		return cluster.rpush(key, strings);
	}

	@Override
	public long llen(String key) {
		return Conversion.toLong(cluster.llen(key));
	}

	@Override
	public String lpop(String key) {
		return cluster.lpop(key);
	}

	@Override
	public String set(String key, String value) {
		return cluster.set(key, value);
	}

	@Override
	public long hset(String key, String field, String value) {
		return cluster.hset(key, field, value);
	}

	@Override
	public String set(byte[] key, byte[] value) {
		return cluster.set(key, value);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return cluster.setex(key, seconds, value);
	}

	@Override
	public long hsetnx(String key, String field, String value) {
		return cluster.hsetnx(key, field, value);
	}

	@Override
	public String get(String key) {
		return cluster.get(key);
	}

	@Override
	public byte[] get(byte[] key) {
		return cluster.get(key);
	}

	/**
	 * 删除键值
	 * @param key 键
	 */
	public long del(String... key) {
		return cluster.del(key);
	}

	/**
	 * 验证键是否存在
	 * @param key 键
	 * @return true 存在 false 不存在
	 */
	public boolean exists(String key) {
		return cluster.exists(key);
	}

	@Override
	public long append(String key, Object value) {
		return cluster.append(Bytes.toBytes(key), Bytes.toBytes(value));
	}

	@Override
	public long ttl(String key) {
		return cluster.ttl(key);
	}

	@Override
	public boolean hexists(String key, String field) {
		return cluster.hexists(key, field);
	}

	@Override
	public String hget(String key, String field) {
		return cluster.hget(key, field);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return cluster.hgetAll(key);
	}

	@Override
	public long hdel(String key, String... field) {
		return cluster.hdel(key, field);
	}

	@Override
	public void subscribe(final JedisPubSub jedisPubSub, final String... channels) {
		cluster.subscribe(jedisPubSub, channels);
	}

	@Override
	public List<byte[]> mget(byte[][] key) {
		return cluster.mget(key);
	}
}