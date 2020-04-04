package com.weicoder.redis.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.weicoder.common.lang.Bytes;
import com.weicoder.common.W;
import com.weicoder.common.log.Logs;
import com.weicoder.redis.base.BaseRedis;
import com.weicoder.redis.builder.JedisBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Tuple;

/**
 * Redis客户端Jedis实现
 * 
 * @author WD
 */
public final class RedisJedis extends BaseRedis {
	// Jedis连接池
	private JedisPool pool;

	public RedisJedis(String name) {
		super(name);
		pool = JedisBuilder.buildPool(name);
	}

	@Override
	public Long rpush(String key, String... strings) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.rpush(key, strings);
		}
	}

	@Override
	public long llen(String key) {
		try (Jedis jedis = pool.getResource()) {
			return W.C.toLong(jedis.llen(key));
		}
	}

	@Override
	public String lpop(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.lpop(key);
		}
	}

	@Override
	public Long lpush(String key, String... strings) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.lpush(key, strings);
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
	 * 
	 * @param key 键
	 */
	public long del(String... key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.del(key);
		}
	}

	/**
	 * 验证键是否存在
	 * 
	 * @param  key 键
	 * @return     true 存在 false 不存在
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
	public List<byte[]> mget(byte[][] key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.mget(key);
		}
	}

	@Override
	public long zcard(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zcard(key);
		}
	}

	@Override
	public Double zscore(String key, String member) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zscore(key, member);
		}
	}

	@Override
	public long hlen(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hlen(key);
		}
	}

	@Override
	public void subscribe(final Subscribe sub, final String... channels) {
		try (Jedis jedis = pool.getResource()) {
			jedis.subscribe(new JedisPubSub() {
				@Override
				public void onMessage(String channel, String message) {
					long time = System.currentTimeMillis();
					sub.onMessage(channel, message);
					Logs.debug("redis subscribe={}  channel={} message={} time={}  thread={}",
							sub.getClass().getSimpleName(), channel, message, System.currentTimeMillis() - time);
				}
			}, channels);
		}
	}

	@Override
	public long publish(String channel, String message) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.publish(channel, message);
		}
	}

	@Override
	public long publish(byte[] channel, byte[] message) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.publish(channel, message);
		}
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zrevrange(key, start, end);
		}
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zrange(key, start, end);
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zrangeByScore(key, min, max);
		}
	}

	@Override
	public Long zadd(String key, double score, String member) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zadd(key, score, member);
		}
	}

	@Override
	public Double zincrby(String key, double increment, String member) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zincrby(key, increment, member);
		}
	}

	@Override
	public Long zrem(String key, String... members) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zrem(key, members);
		}
	}

	@Override
	public Long srem(String key, String... members) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.srem(key, members);
		}
	}

	@Override
	public Long sadd(String key, String... members) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.sadd(key, members);
		}
	}

	@Override
	public Set<String> smembers(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.smembers(key);
		}
	}

	@Override
	public long scard(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.scard(key);
		}
	}

	@Override
	public boolean sexists(String key, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.sismember(key, value);
		}
	}

	@Override
	public List<String> lrange(String key, long start, long stop) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.lrange(key, start, stop);
		}
	}

	@Override
	public void exec(Callback callback) {
		try (Jedis jedis = pool.getResource()) {
			callback.callback(jedis);
		}
	}

	@Override
	public Jedis getResource(String key) {
		return pool.getResource();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		}
	}

	@Override
	public String rpop(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.rpop(key);
		}
	}
}