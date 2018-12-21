package com.weicoder.nosql.redis.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import com.weicoder.nosql.redis.base.BaseRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * redis redisson 实现
 * 
 * @author wudi
 */
public final class RedisRedisson extends BaseRedis {
	// RedissonClient
	private RedissonClient redisson;

	public RedisRedisson(String name) {
		// 创建配置
		Config config = new Config();
		// 设置编码
		config.setCodec(new org.redisson.client.codec.StringCodec());

		// 设置单机模式
		config.useSingleServer().setAddress("redis://127.0.0.1:6379");
////config.setPassword("password")//设置密码
//		config.useSingleServer().setConnectionPoolSize(500);// 设置对于master节点的连接池中连接数最大为500
//		config.useSingleServer().setIdleConnectionTimeout(10000);// 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
//		config.useSingleServer().setConnectTimeout(30000);// 同任何节点建立连接时的等待超时。时间单位是毫秒。
//		config.useSingleServer().setTimeout(3000);// 等待节点回复命令的时间。该时间从命令发送成功时开始计时。
//		config.useSingleServer().setPingTimeout(30000);
////		config.useSingleServer().setReconnectionTimeout(3000);// 当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
//		
//		config.useClusterServers()
//		// 集群状态扫描间隔时间，单位是毫秒
//	    .setScanInterval(2000) 
//	    //cluster方式至少6个节点(3主3从，3主做sharding，3从用来保证主宕机后可以高可用)
//	    .addNodeAddress("redis://127.0.0.1:6379" )
//	    .addNodeAddress("redis://127.0.0.1:6380")
//	    .addNodeAddress("redis://127.0.0.1:6381")
//	    .addNodeAddress("redis://127.0.0.1:6382")
//	    .addNodeAddress("redis://127.0.0.1:6383")
//	    .addNodeAddress("redis://127.0.0.1:6384")
//
//	//config.setPassword("password")//设置密码
//	config.setMasterConnectionPoolSize(500)//设置对于master节点的连接池中连接数最大为500
//	config.setSlaveConnectionPoolSize(500)//设置对于slave节点的连接池中连接数最大为500
//	config.setIdleConnectionTimeout(10000)//如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
//	config.setConnectTimeout(30000)//同任何节点建立连接时的等待超时。时间单位是毫秒。
//	config.setTimeout(3000)//等待节点回复命令的时间。该时间从命令发送成功时开始计时。
//	config.setPingTimeout(30000)
//	config.setReconnectionTimeout(3000)//当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
 
 
		//声明RedissonClient
		redisson = Redisson.create(config);
	}

	@Override
	public Jedis getResource() {

		return null;
	}

	@Override
	public long append(String key, Object value) {

		return 0;
	}

	@Override
	public String set(String key, String value) {

		return null;
	}

	@Override
	public long hset(String key, String field, String value) {

		return 0;
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
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {

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

}
