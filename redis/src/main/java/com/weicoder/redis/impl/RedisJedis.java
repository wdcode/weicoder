package com.weicoder.redis.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.log.Logs;
import com.weicoder.redis.Subscribe;
import com.weicoder.redis.base.BaseRedis;
import com.weicoder.redis.builder.JedisBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * Redis客户端Jedis实现
 * 
 * @author WD
 */
public final class RedisJedis extends BaseRedis implements Subscribe {
	// Jedis连接池
	private JedisPool pool;
	// 默认异常返回long
	private long error = -1L;

	public RedisJedis(String name) {
		pool = JedisBuilder.buildPool(name);
	}

	@Override
	public Jedis getResource() {
		return pool.getResource();
	}

	@Override
	public Long rpush(String key, String... strings) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.rpush(key, strings);
		} catch (Exception e) {
			Logs.error(e, "redis pool rpush key={} strings={}", key, strings);
			return error;
		}
	}

	@Override
	public long llen(String key) {
		try (Jedis jedis = pool.getResource()) {
			return Conversion.toLong(jedis.llen(key));
		} catch (Exception e) {
			Logs.error(e, "redis pool llen key={}", key);
			return error;
		}
	}

	@Override
	public String lpop(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.lpop(key);
		} catch (Exception e) {
			Logs.error(e, "redis pool lpop key={}", key);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public Long lpush(String key, String... strings) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.lpush(key, strings);
		} catch (Exception e) {
			Logs.error(e, "redis pool lpush key={} strings={}", key, strings);
			return error;
		}
	}

	@Override
	public String set(String key, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.set(key, value);
		} catch (Exception e) {
			Logs.error(e, "redis pool set key={} value={}", key, value);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public long hset(String key, String field, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hset(key, field, value);
		} catch (Exception e) {
			Logs.error(e, "redis pool hset key={} field={} value={}", key, field, value);
			return error;
		}
	}

	@Override
	public String set(byte[] key, byte[] value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.set(key, value);
		} catch (Exception e) {
			Logs.error(e, "redis pool set key={} strings={}", key, value);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public String setex(String key, int seconds, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.setex(key, seconds, value);
		} catch (Exception e) {
			Logs.error(e, "redis pool setex key={} seconds={} value", key, seconds, value);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public long hsetnx(String key, String field, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hsetnx(key, field, value);
		} catch (Exception e) {
			Logs.error(e, "redis pool hsetnx key={} field={} value={}", key, field, value);
			return error;
		}
	}

	@Override
	public String get(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.get(key);
		} catch (Exception e) {
			Logs.error(e, "redis pool get key={}", key);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public byte[] get(byte[] key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.get(key);
		} catch (Exception e) {
			Logs.error(e, "redis pool get key={}", key);
			return ArrayConstants.BYTES_EMPTY;
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
		} catch (Exception e) {
			Logs.error(e, "redis pool del key={}", Arrays.toString(key));
			return error;
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
		} catch (Exception e) {
			Logs.error(e, "redis pool exists key={}", key);
			return false;
		}
	}

	@Override
	public long append(String key, Object value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.append(Bytes.toBytes(key), Bytes.toBytes(value));
		} catch (Exception e) {
			Logs.error(e, "redis pool append key={} value={}", key, value);
			return error;
		}
	}

	@Override
	public long ttl(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.ttl(key);
		} catch (Exception e) {
			Logs.error(e, "redis pool ttl key={}", key);
			return error;
		}
	}

	@Override
	public boolean hexists(String key, String field) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hexists(key, field);
		} catch (Exception e) {
			Logs.error(e, "redis pool hexists key={} field={}", key, field);
			return false;
		}
	}

	@Override
	public String hget(String key, String field) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hget(key, field);
		} catch (Exception e) {
			Logs.error(e, "redis pool hget key={} field={}", key, field);
			return StringConstants.EMPTY;
		}
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hgetAll(key);
		} catch (Exception e) {
			Logs.error(e, "redis pool hgetAll key={}", key);
			return Maps.emptyMap();
		}
	}

	@Override
	public long hdel(String key, String... field) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hdel(key, field);
		} catch (Exception e) {
			Logs.error(e, "redis pool hdel key={} field={}", key, field);
			return error;
		}
	}

	@Override
	public List<byte[]> mget(byte[][] key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.mget(key);
		} catch (Exception e) {
			Logs.error(e, "redis pool mget key={}", Arrays.toString(key));
			return Lists.emptyList();
		}
	}

	@Override
	public long zcard(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zcard(key);
		} catch (Exception e) {
			Logs.error(e, "redis pool zcard key={}", key);
			return error;
		}
	}

	@Override
	public Double zscore(String key, String member) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zscore(key, member);
		} catch (Exception e) {
			Logs.error(e, "redis pool zscore key={} member={}", key, member);
			return 0D;
		}
	}

	@Override
	public long hlen(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hlen(key);
		} catch (Exception e) {
			Logs.error(e, "redis pool hlen key={}", key);
			return error;
		}
	}

	@Override
	public void subscribe(final JedisPubSub jedisPubSub, final String... channels) {
		try (Jedis jedis = pool.getResource()) {
			jedis.subscribe(jedisPubSub, channels);
		} catch (Exception e) {
			Logs.error(e, "redis pool subscribe sub={} channels={}", jedisPubSub, channels);
		}
	}

	@Override
	public long publish(String channel, String message) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.publish(channel, message);
		} catch (Exception e) {
			Logs.error(e, "redis pool publish channel={} message={}", channel, message);
			return error;
		}
	}

	@Override
	public long publish(byte[] channel, byte[] message) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.publish(channel, message);
		} catch (Exception e) {
			Logs.error(e, "redis pool publish channel={} message={}", channel, message);
			return error;
		}
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zrevrange(key, start, end);
		} catch (Exception e) {
			Logs.error(e, "redis pool zrevrange key={} start={} end={}", key, start, end);
			return Sets.emptySet();
		}
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zrange(key, start, end);
		} catch (Exception e) {
			Logs.error(e, "redis pool zrange key={} start={} end={}", key, start, end);
			return Sets.emptySet();
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			Logs.error(e, "redis pool zrangeByScore key={} min={} max={}", key, min, max);
			return Sets.emptySet();
		}
	}

	@Override
	public Long zadd(String key, double score, String member) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zadd(key, score, member);
		} catch (Exception e) {
			Logs.error(e, "redis pool zadd key={} score={} member={}", key, score, member);
			return error;
		}
	}

	@Override
	public Double zincrby(String key, double increment, String member) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zincrby(key, increment, member);
		} catch (Exception e) {
			Logs.error(e, "redis pool zadd key={} score={} member={}", key, increment, member);
			return -1D;
		}
	}

	@Override
	public Long zrem(String key, String... members) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.zrem(key, members);
		} catch (Exception e) {
			Logs.error(e, "redis pool zrem key={} members={}", key, members);
			return error;
		}
	}

	@Override
	public Long srem(String key, String... members) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.srem(key, members);
		} catch (Exception e) {
			Logs.error(e, "redis pool srem key={} members={}", key, members);
			return error;
		}
	}

	@Override
	public Long sadd(String key, String... members) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.sadd(key, members);
		} catch (Exception e) {
			Logs.error(e, "redis pool sadd key={} members={}", key, members);
			return error;
		}
	}

	@Override
	public Set<String> smembers(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.smembers(key);
		} catch (Exception e) {
			Logs.error(e, "redis pool smembers key={}", key);
			return Sets.emptySet();
		}
	}

	@Override
	public long scard(String key) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.scard(key);
		} catch (Exception e) {
			Logs.error(e, "redis pool scard key={}", key);
			return error;
		}
	}

	@Override
	public boolean sexists(String key, String value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.sismember(key, value);
		} catch (Exception e) {
			Logs.error(e, "redis pool sexists key={}", key);
			return false;
		}
	}
}