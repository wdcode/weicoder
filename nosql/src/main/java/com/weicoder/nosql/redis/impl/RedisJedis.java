package com.weicoder.nosql.redis.impl;

import java.util.List;
import java.util.Map;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.nosql.redis.base.BaseRedis;
import com.weicoder.nosql.params.RedisParams;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Protocol;

/**
 * Redis客户端Jedis实现
 * @author WD
 */
public final class RedisJedis extends BaseRedis {
	// Jedis连接池
	private JedisPool pool;

	public RedisJedis(String name) {
		// 实例化Jedis配置
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置属性
		config.setMaxTotal(RedisParams.getMaxTotal(name));
		config.setMaxIdle(RedisParams.getMaxIdle(name));
		config.setMaxWaitMillis(RedisParams.getMaxWait(name));
		// 实例化连接池
		pool = new JedisPool(config, RedisParams.getHost(name), RedisParams.getPort(name), Protocol.DEFAULT_TIMEOUT,
				EmptyUtil.isEmpty(RedisParams.getPassword(name)) ? null : RedisParams.getPassword(name));
	}

	@Override
	public Jedis getResource() {
		return pool.getResource();
	}

	@Override
	public long rpush(String key, String... strings) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.rpush(key, strings);
		} catch (Exception e) {
			Logs.error(e);
			return 0L;
		}
	}

	@Override
	public long llen(String key) {
		try (Jedis jedis = pool.getResource()) {
			return Conversion.toLong(jedis.llen(key));
		} catch (Exception e) {
			Logs.error(e);
			return 0L;
		}
	}

	@Override
	public String lpop(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.lpop(key);
		} catch (Exception e) {
			Logs.error(e);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public String set(String key, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.set(key, value);
		} catch (Exception e) {
			Logs.error(e);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public long hset(String key, String field, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hset(key, field, value);
		} catch (Exception e) {
			Logs.error(e);
			return -1;
		}
	}

	@Override
	public String set(byte[] key, byte[] value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.set(key, value);
		} catch (Exception e) {
			Logs.error(e);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public String setex(String key, int seconds, String value) {
		try (Jedis jedis = pool.getResource()) {
			// 设置值
			return jedis.setex(key, seconds, value);
		} catch (Exception e) {
			Logs.error(e);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public long hsetnx(String key, String field, String value) {
		try (Jedis jedis = pool.getResource()) {
			// 设置值
			return jedis.hsetnx(key, field, value);
		} catch (Exception e) {
			Logs.error(e);
			return 0;
		}
	}

	@Override
	public String get(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.get(key);
		} catch (Exception e) {
			Logs.error(e);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public byte[] get(byte[] key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.get(key);
		} catch (Exception e) {
			Logs.error(e);
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	/**
	 * 删除键值
	 * @param key 键
	 */
	public long del(String... key) {
		try (Jedis jedis = pool.getResource()) {
			// 删除
			return jedis.del(key);
		} catch (Exception e) {
			Logs.error(e);
			return -1;
		}
	}

	/**
	 * 验证键是否存在
	 * @param key 键
	 * @return true 存在 false 不存在
	 */
	public boolean exists(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.exists(key);
		} catch (Exception e) {
			Logs.error(e);
			return false;
		}
	}

	@Override
	public boolean append(String key, Object value) {
		try (Jedis jedis = pool.getResource()) {
			// 设置值
			jedis.append(Bytes.toBytes(key), Bytes.toBytes(value));
			// 返回成功
			return true;
		} catch (Exception e) {
			Logs.error(e);
			return false;
		}
	}

	@Override
	public long ttl(String key) {
		try (Jedis jedis = pool.getResource()) {
			// 设置值
			return jedis.ttl(key);
		} catch (Exception e) {
			Logs.error(e);
			return 0;
		}
	}

	@Override
	public boolean hexists(String key, String field) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hexists(key, field);
		} catch (Exception e) {
			Logs.error(e);
			return false;
		}
	}

	@Override
	public String hget(String key, String field) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hget(key, field);
		} catch (Exception e) {
			Logs.error(e);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hgetAll(key);
		} catch (Exception e) {
			Logs.error(e);
			return Maps.emptyMap();
		}
	}

	@Override
	public long hdel(String key, String... field) {
		try (Jedis jedis = pool.getResource()) {
			// 删除
			return jedis.hdel(key, field);
		} catch (Exception e) {
			Logs.error(e);
			return -1;
		}
	}

	@Override
	public void subscribe(final JedisPubSub jedisPubSub, final String... channels) {
		try (Jedis jedis = pool.getResource()) {
			jedis.subscribe(jedisPubSub, channels);
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	@Override
	public List<byte[]> mget(byte[][] key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.mget(key);
		} catch (Exception e) {
			Logs.error(e);
			return Lists.emptyList();
		}
	}
}