package com.weicoder.redis.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.weicoder.common.lang.W;
import com.weicoder.common.interfaces.Calls;
import com.weicoder.common.log.Logs;
import com.weicoder.redis.base.BaseRedis;
import com.weicoder.redis.builder.RedisBuilder;

import redis.clients.jedis.AbstractTransaction; 
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.RedisClient; 
import redis.clients.jedis.params.SetParams;
import redis.clients.jedis.params.ZRangeParams;
import redis.clients.jedis.resps.Tuple;

/**
 * Redis客户端Jedis实现
 * 
 * @author WD
 */
public final class RedisJedis extends BaseRedis {
	// RedisClient连接池 
	private RedisClient client;

	public RedisJedis(String name) {
		super(name);
//		pool = JedisBuilder.buildPool(name);
		client = RedisBuilder.pool(name);
	}

	@Override
	public Long rpush(String key, String... strings) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.rpush(key, strings);
//		}
		return client.rpush(key, strings);
	}

	@Override
	public long llen(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return W.C.toLong(jedis.llen(key));
//		}
		return W.C.toLong(client.llen(key));
	}

	@Override
	public String lpop(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.lpop(key);
//		}
		return client.lpop(key);
	}

	@Override
	public Long lpush(String key, String... strings) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.lpush(key, strings);
//		}
		return client.lpush(key, strings);
	}

	@Override
	public String set(String key, String value) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.set(key, value);
//		}
		return client.set(key, value);
	}

	@Override
	public long hset(String key, String field, String value) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.hset(key, field, value);
//		}
		return client.hset(key, field, value);
	}

	@Override
	public String set(byte[] key, byte[] value) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.set(key, value);
//		}
		return client.set(key, value);
	}

	@Override
	public String setex(String key, long seconds, String value) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.setex(key, seconds, value);
//		}
		return client.set(key, value, SetParams.setParams().ex(seconds));
	}

	@Override
	public long hsetnx(String key, String field, String value) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.hsetnx(key, field, value);
//		}
		return client.hsetnx(key, field, value);
	}

	@Override
	public String get(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.get(key);
//		}
		return client.get(key);
	}

	@Override
	public byte[] get(byte[] key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.get(key);
//		}
		return client.get(key);
	}

	/**
	 * 删除键值
	 * 
	 * @param key 键
	 */
	public long del(String... key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.del(key);
//		}
		return client.del(key);
	}

	/**
	 * 验证键是否存在
	 * 
	 * @param key 键
	 * @return true 存在 false 不存在
	 */
	public boolean exists(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.exists(key);
//		}
		return client.exists(key);
	}

	@Override
	public long append(String key, Object value) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.append(W.B.toBytes(key), W.B.toBytes(value));
//		}
		return client.append(W.B.toBytes(key), W.B.toBytes(value));
	}

	@Override
	public long ttl(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.ttl(key);
//		}
		return client.ttl(key);
	}

	@Override
	public boolean hexists(String key, String field) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.hexists(key, field);
//		}
		return client.hexists(key, field);
	}

	@Override
	public String hget(String key, String field) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.hget(key, field);
//		}
		return client.hget(key, field);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.hgetAll(key);
//		}
		return client.hgetAll(key);
	}

	@Override
	public long hdel(String key, String... field) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.hdel(key, field);
//		}
		return client.hdel(key, field);
	}

	@Override
	public List<byte[]> mget(byte[][] key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.mget(key);
//		}
		return client.mget(key);
	}

	@Override
	public long zcard(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.zcard(key);
//		}
		return client.zcard(key);
	}

	@Override
	public Double zscore(String key, String member) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.zscore(key, member);
//		}
		return client.zscore(key, member);
	}

	@Override
	public long hlen(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.hlen(key);
//		}
		return client.hlen(key);
	}

	@Override
	public void subscribe(final Subscribe sub, final String... channels) {
//		try (Jedis jedis = pool.getResource()) {
//			jedis.subscribe(new JedisPubSub() {
//				@Override
//				public void onMessage(String channel, String message) {
//					long time = System.currentTimeMillis();
//					sub.onMessage(channel, message);
//					Logs.debug("redis subscribe={}  channel={} message={} time={}  thread={}",
//							sub.getClass().getSimpleName(), channel, message, System.currentTimeMillis() - time);
//				}
//			}, channels);
//		}
		client.subscribe(new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) {
				long time = System.currentTimeMillis();
				sub.onMessage(channel, message);
				Logs.debug("redis subscribe={}  channel={} message={} time={}  thread={}",
						sub.getClass().getSimpleName(), channel, message, System.currentTimeMillis() - time);
			}
		}, channels);
	}

	@Override
	public long publish(String channel, String message) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.publish(channel, message);
//		}
		return client.publish(channel, message);
	}

	@Override
	public long publish(byte[] channel, byte[] message) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.publish(channel, message);
//		}
		return client.publish(channel, message);
	}

	@Override
	public List<String> zrevrange(String key, long start, long end) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.zrevrange(key, start, end);
//		}
		return client.zrange(key, ZRangeParams.zrangeParams(start, end).rev());
	}

	@Override
	public List<String> zrange(String key, long start, long end) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.zrange(key, start, end);
//		}
		return client.zrange(key, start, end);
	}

	@Override
	public List<String> zrangeByScore(String key, double min, double max) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.zrangeByScore(key, min, max);
//		}
		return client.zrange(key, ZRangeParams.zrangeByScoreParams(min, max));
	}

	@Override
	public Long zadd(String key, double score, String member) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.zadd(key, score, member);
//		}
		return client.zadd(key, score, member);
	}

	@Override
	public Double zincrby(String key, double increment, String member) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.zincrby(key, increment, member);
//		}
		return client.zincrby(key, increment, member);
	}

	@Override
	public Long zrem(String key, String... members) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.zrem(key, members);
//		}
		return client.zrem(key, members);
	}

	@Override
	public Long srem(String key, String... members) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.srem(key, members);
//		}
		return client.srem(key, members);
	}

	@Override
	public Long sadd(String key, String... members) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.sadd(key, members);
//		}
		return client.sadd(key, members);
	}

	@Override
	public Set<String> smembers(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.smembers(key);
//		}
		return client.smembers(key);
	}

	@Override
	public long scard(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.scard(key);
//		}
		return client.scard(key);
	}

	@Override
	public boolean sexists(String key, String value) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.sismember(key, value);
//		}
		return client.sismember(key, value);
	}

	@Override
	public List<String> lrange(String key, long start, long stop) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.lrange(key, start, stop);
//		}
		return client.lrange(key, start, stop);
	}

	@Override
	public void exec(Calls.EoV<RedisClient> callback) {
//		try (Jedis jedis = pool.getResource()) {
//			callback.call(jedis);
//		}
		callback.call(client);
	}

	@Override
	public RedisClient getResource(String key) {
//		return pool.getResource();
		return null;
	}

	@Override
	public List<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
//		} 
		return client.zrangeWithScores(key, ZRangeParams.zrangeByScoreParams(min, max).limit(offset, count).rev());
	}

	@Override
	public String rpop(String key) {
//		try (Jedis jedis = pool.getResource()) {
//			return jedis.rpop(key);
//		}
		return client.rpop(key);
	}

	@Override
	public void multi(Calls.EoV<AbstractTransaction> callback) {
//		try (Jedis jedis = pool.getResource()) {
//			Transaction t = jedis.multi();
//			try {
//				callback.call(t);
//				t.exec();
//			} catch (Exception e) {
//				t.discard();
//				Logs.error(e);
//			} finally {
//				t.close();
//			}
//		}
		AbstractTransaction t = client.multi();
		try {
			callback.call(t);
			t.exec();
		} catch (Exception e) {
			t.discard();
			Logs.error(e);
		} finally {
			t.close();
		}
	}
}