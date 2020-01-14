package com.weicoder.redis.builder;

import java.util.Set;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.C;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.StringUtil;
import com.weicoder.redis.params.RedisParams;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * jedis 构建者
 * 
 * @author wudi
 */
public final class JedisBuilder {

	/**
	 * 构建Jedis集群
	 * 
	 * @param name 名称
	 * @return Jedis集群
	 */
	public static JedisCluster buildCluster(String name) {
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
			nodes.add(new HostAndPort(s[0], C.toInt(s[1])));
		}
		// 生成JedisCluster
		Logs.info("redis init cluster nodes={}", nodes);
		return new JedisCluster(nodes, 3000, 3000, 5, RedisParams.getPassword(name), config);
	}

	/**
	 * 构建Jedis对象池
	 * @param name 名称
	 * @return Jedis对象池
	 */
	public static JedisPool buildPool(String name) {
		// 实例化Jedis配置
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置属性
		config.setMaxTotal(RedisParams.getMaxTotal(name));
		config.setMaxIdle(RedisParams.getMaxIdle(name));
		config.setMaxWaitMillis(RedisParams.getMaxWait(name));
		// 实例化连接池
		Logs.info("redis init pool config={}", config);
		return new JedisPool(config, RedisParams.getHost(name), RedisParams.getPort(name), Protocol.DEFAULT_TIMEOUT,
				RedisParams.getPassword(name), RedisParams.getDatabase(name), null);
	}

	private JedisBuilder() {
	}
}
