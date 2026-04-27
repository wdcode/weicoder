package com.weicoder.redis.builder;

import java.time.Duration;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.weicoder.common.constants.C;
import com.weicoder.common.lang.W;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.U;
import com.weicoder.redis.params.RedisParams;

import redis.clients.jedis.Connection;
import redis.clients.jedis.ConnectionPoolConfig;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.RedisClient;
import redis.clients.jedis.RedisClusterClient;

/**
 * jedis 构建者
 * 
 * @author wudi
 */
public final class RedisBuilder {
	/**
	 * 构建Jedis集群
	 * 
	 * @param name 名称
	 * @return Jedis集群
	 */
	public static RedisClusterClient cluster(String name) {
		// 连接配置
		RedisClusterClient.Builder builder = RedisClusterClient.builder();
		// 实例化Jedis配置
		GenericObjectPoolConfig<Connection> config = new GenericObjectPoolConfig<Connection>();
		// 设置属性
		config.setMaxTotal(RedisParams.getMaxTotal(name));
		config.setMaxIdle(RedisParams.getMaxIdle(name));
		config.setMaxWait(Duration.ofMillis(RedisParams.getMaxWait(name)));
		builder.poolConfig(config);
		builder.clientConfig(config(name));
		// 服务器节点
		Set<HostAndPort> nodes = W.S.set();
		for (String server : RedisParams.getCluster(name)) {
			String[] s = U.S.split(server, C.S.COLON);
			nodes.add(new HostAndPort(s[0], W.C.toInt(s[1])));
		}
		builder.nodes(nodes);
		// 生成JedisCluster
		Logs.info("redis init cluster nodes={}", nodes);
		return builder.build();
	}

	/**
	 * 构建RedisClient对象池
	 * 
	 * @param name 名称
	 * @return RedisClient
	 */
	public static RedisClient pool(String name) {
		// 连接配置
		RedisClient.Builder builder = RedisClient.builder();
		// 连接池配置
		ConnectionPoolConfig config = new ConnectionPoolConfig();
		// 设置属性
		config.setMaxTotal(RedisParams.getMaxTotal(name));
		config.setMaxIdle(RedisParams.getMaxIdle(name));
		config.setMaxWait(Duration.ofMillis(RedisParams.getMaxWait(name)));
		builder.poolConfig(config);
		builder.clientConfig(config(name));
		// 主机端口
		builder.hostAndPort(new HostAndPort(RedisParams.getHost(name), RedisParams.getPort(name)));
		// 实例化连接池
		Logs.info("redis init pool config={}", config);
		return builder.build();
	}

	private static JedisClientConfig config(String name) {
		return DefaultJedisClientConfig.builder().password(RedisParams.getPassword(name))
				.database(RedisParams.getDatabase(name)).build();
	}

	private RedisBuilder() {
	}
}
