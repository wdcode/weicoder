package com.weicoder.test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.weicoder.redis.RedisPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class EmptyRedis implements RedisPool {

	@Override
	public Jedis getResource(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exec(Callback callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lock(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean lock(String key, long ms) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lock(String key, int s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lock(String key, int seconds, long timeout) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public String compress(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] extract(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> extract(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long append(String key, Object value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setex(String key, int seconds, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long hlen(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] get(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> mget(byte[][] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] get(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long del(String... key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long hdel(String key, String... field) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exists(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sexists(String key, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hexists(String key, String field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long hsetnx(String key, String field, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long ttl(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void subscribe(Subscribe sub, String... channels) {
		// TODO Auto-generated method stub

	}

	@Override
	public long publish(String channel, String message) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long publish(byte[] channel, byte[] message) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long rpush(String key, String... strings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpush(String key, String... strings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> lrange(String key, long start, long stop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long llen(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long zcard(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Double zscore(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String key, double score, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zincrby(String key, double increment, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long sadd(String key, String... members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> smembers(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long scard(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long zrem(String key, String... members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long srem(String key, String... members) {
		// TODO Auto-generated method stub
		return null;
	}

}
