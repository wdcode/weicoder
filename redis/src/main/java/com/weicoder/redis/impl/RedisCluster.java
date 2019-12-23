package com.weicoder.redis.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
 
import com.weicoder.common.lang.Bytes;
import com.weicoder.redis.Subscribe;
import com.weicoder.redis.base.BaseRedis;
import com.weicoder.redis.builder.JedisBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster; 
import redis.clients.jedis.JedisPubSub;

/**
 * redis 集群
 * 
 * @author wudi
 */
public final class RedisCluster extends BaseRedis implements Subscribe{
	// 声明JedisCluster
	private JedisCluster cluster;

	/**
	 * 构造
	 * 
	 * @param name 名称
	 */
	public RedisCluster(String name) { 
		cluster = JedisBuilder.buildCluster(name);
	}

	@Override
	public Jedis getResource() {
		return cluster.getConnectionFromSlot(1);
	}

	@Override
	public long append(String key, Object value) {
		return cluster.append(Bytes.toBytes(key), Bytes.toBytes(value));
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
	public String get(String key) { 
		return cluster.get(key);
	}

	@Override
	public String hget(String key, String field) { 
		return cluster.hget(key, field);
	}

	@Override
	public long hlen(String key) { 
		return cluster.hlen(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) { 
		return cluster.hgetAll(key);
	}

	@Override
	public byte[] get(byte[] key) { 
		return cluster.get(key);
	}

	@Override
	public List<byte[]> mget(byte[][] key) { 
		return cluster.mget(key);
	}

	@Override
	public long del(String... key) { 
		return cluster.del(key);
	}

	@Override
	public long hdel(String key, String... field) { 
		return cluster.hdel(key, field);
	}

	@Override
	public boolean exists(String key) { 
		return cluster.exists(key);
	}

	@Override
	public boolean sexists(String key, String value) { 
		return cluster.sismember(key, value);
	}

	@Override
	public boolean hexists(String key, String field) { 
		return cluster.hexists(key, field);
	}

	@Override
	public long hsetnx(String key, String field, String value) {  
		return cluster.hsetnx(key, field, value);
	}

	@Override
	public long ttl(String key) { 
		return cluster.ttl(key);
	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		cluster.subscribe(jedisPubSub, channels);
	}

	@Override
	public long publish(String channel, String message) { 
		return cluster.publish(channel, message);
	}

	@Override
	public long publish(byte[] channel, byte[] message) { 
		return cluster.publish(channel, message);
	}

	@Override
	public Long rpush(String key, String... strings) { 
		return cluster.rpush(key, strings);
	}

	@Override
	public String lpop(String key) { 
		return cluster.lpop(key);
	}

	@Override
	public Long lpush(String key, String... strings) { 
		return cluster.lpush(key, strings);
	}

	@Override
	public long llen(String key) { 
		return cluster.llen(key);
	}

	@Override
	public long zcard(String key) { 
		return cluster.zcard(key);
	}

	@Override
	public Double zscore(String key, String member) { 
		return cluster.zscore(key, member);
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) { 
		return cluster.zrevrange(key, start, end);
	}

	@Override
	public Set<String> zrange(String key, long start, long end) { 
		return cluster.zrange(key, start, end);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) { 
		return cluster.zrangeByScore(key, min, max);
	}

	@Override
	public Long zadd(String key, double score, String member) { 
		return cluster.zadd(key,score, member);
	}

	@Override
	public Long sadd(String key, String... members) { 
		return cluster.sadd(key, members);
	}

	@Override
	public Set<String> smembers(String key) {  
		return cluster.smembers(key);
	}

	@Override
	public long scard(String key) { 
		return cluster.scard(key);
	}

	@Override
	public Long zrem(String key, String... members) { 
		return cluster.zrem(key, members);
	}

	@Override
	public Long srem(String key, String... members) { 
		return cluster.srem(key, members);
	}

	@Override
	public Double zincrby(String key, double increment, String member) { 
		return cluster.zincrby(key, increment, member);
	}

	@Override
	public void exec(Callback callback) {
		callback.callback(getResource());
	}

	@Override
	public List<String> lrange(String key, long start, long stop) {
		return cluster.lrange(key, start,  stop);
	} 
}
