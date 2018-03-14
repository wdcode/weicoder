package com.weicoder.nosql.redis.factory;

import java.util.Set;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.util.StringUtil;
import com.weicoder.nosql.params.RedisParams;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisCluster工厂
 * @author WD
 */
final class JedisClusterFactory extends FactoryKey<String, JedisCluster> {
	/** JedisCluster工厂 */
	final static JedisClusterFactory FACTORY = new JedisClusterFactory();

	@Override
	public JedisCluster newInstance(String name) {
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
		return new JedisCluster(nodes, 3000, 3000, 5, RedisParams.getPassword(name), config);
	}

	private JedisClusterFactory() {}
}
