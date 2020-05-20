package com.weicoder.redisson;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.redisson.api.RedissonClient;

import com.weicoder.common.interfaces.CallbackVoid;
import com.weicoder.redis.base.BaseRedis;
import com.weicoder.redisson.builder.RedissonBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

/**
 * redis redisson 实现
 * 
 * @author wudi
 */
public final class RedissonRedis extends BaseRedis {
	// RedissonClient
	private RedissonClient redisson;

	/**
	 * 构造方法
	 * 
	 * @param name
	 */
	public RedissonRedis(String name) {
		super(name);
		redisson = RedissonBuilder.newBuilder(name);
	}

	@Override
	public long append(String key, Object value) {
		return 0;
	}

	@Override
	public String set(String key, String value) {
		redisson.getBucket(key).set(value);
		return value;
	}

	@Override
	public long hset(String key, String field, String value) {
		redisson.getMap(key).put(field, value);
		return 1;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		return null;
	}

	@Override
	public String setex(String key, int seconds, String value) {

		return null;
	}

	@Override
	public String get(String key) {

		return null;
	}

	@Override
	public String hget(String key, String field) {

		return null;
	}

	@Override
	public long hlen(String key) {

		return 0;
	}

	@Override
	public Map<String, String> hgetAll(String key) {

		return null;
	}

	@Override
	public byte[] get(byte[] key) {

		return null;
	}

	@Override
	public List<byte[]> mget(byte[][] key) {

		return null;
	}

	@Override
	public long del(String... key) {

		return 0;
	}

	@Override
	public long hdel(String key, String... field) {

		return 0;
	}

	@Override
	public boolean exists(String key) {

		return false;
	}

	@Override
	public boolean sexists(String key, String value) {

		return false;
	}

	@Override
	public boolean hexists(String key, String field) {

		return false;
	}

	@Override
	public long hsetnx(String key, String field, String value) {

		return 0;
	}

	@Override
	public long ttl(String key) {

		return 0;
	}
 
	@Override
	public long publish(String channel, String message) {

		return 0;
	}

	@Override
	public long publish(byte[] channel, byte[] message) {

		return 0;
	}

	@Override
	public Long rpush(String key, String... strings) {

		return null;
	}

	@Override
	public String lpop(String key) {

		return null;
	}

	@Override
	public Long lpush(String key, String... strings) {

		return null;
	}

	@Override
	public long llen(String key) {

		return 0;
	}

	@Override
	public long zcard(String key) {

		return 0;
	}

	@Override
	public Double zscore(String key, String member) {

		return null;
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {

		return null;
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {

		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {

		return null;
	}

	@Override
	public Long zadd(String key, double score, String member) {

		return null;
	}

	@Override
	public Long sadd(String key, String... members) {

		return null;
	}

	@Override
	public Set<String> smembers(String key) {

		return null;
	}

	@Override
	public long scard(String key) {

		return 0;
	}

	@Override
	public Long zrem(String key, String... members) {

		return null;
	}

	@Override
	public Long srem(String key, String... members) {

		return null;
	}

	@Override
	public Jedis getResource(String key) {
		
		return null;
	}

	@Override
	public void exec(Callback callback) {
		

	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		
		return null;
	}

	@Override
	public String rpop(String key) {
		
		return null;
	}

	@Override
	public void subscribe(Subscribe sub, String... channels) {
		

	}

	@Override
	public List<String> lrange(String key, long start, long stop) {
		
		return null;
	}

	@Override
	public Double zincrby(String key, double increment, String member) {
		
		return null;
	}

	@Override
	public void multi(CallbackVoid<Transaction> callback) {
		// TODO Auto-generated method stub
		
	}
}
