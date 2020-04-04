package com.weicoder.redis.base;

import java.util.List;

import com.weicoder.common.W.C;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.ThreadUtil;
import com.weicoder.common.zip.ZipEngine;
import com.weicoder.json.JsonEngine;
import com.weicoder.redis.RedisPool;

/**
 * Redis基类
 * 
 * @author WD
 */
public abstract class BaseRedis implements RedisPool {
	// redismingc
	private String name;

	public BaseRedis(String name) {
		super();
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}

	/**
	 * 压缩值 当值能压缩时才压缩
	 * 
	 * @param key   键
	 * @param value 值
	 */
	public final String compress(String key, Object value) {
		return set(Bytes.toBytes(key), ZipEngine.compress(value));
	}

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * 
	 * @param  key 键
	 * @return     值
	 */
	public final byte[] extract(String key) {
		return ZipEngine.extract(get(key));
	}

	/**
	 * 获得多个键的数组
	 * 
	 * @param  keys 键
	 * @return      值
	 */
	public Object[] get(String... keys) {
		// 声明列表
		Object[] objs = new Object[keys.length];
		// 循环解压数据
		for (int i = 0; i < keys.length; i++) {
			objs[i] = get(keys[i]);
		}
		// 返回列表
		return objs;
	}

	/**
	 * 获得多个键的数组
	 * 
	 * @param  keys 键
	 * @return      值
	 */
	public List<byte[]> extract(String... keys) {
		// 声明列表
		List<byte[]> list = Lists.newList(keys.length);
		// 循环解压数据
		for (Object o : get(keys)) {
			list.add(ZipEngine.extract(o));
		}
		// 返回列表
		return list;
	}

	@Override
	public String set(String key, Object value) {
		return set(key, toString(value));
	}

	@Override
	public long hset(String key, Object field, Object value) {
		return hset(key, toString(key), toString(value));
	}

	@Override
	public void lock(String key) {
		lock(key, -1L);
	}

	@Override
	public boolean lock(String key, int s) {
		return lock(key, s, -1L);
	}

	@Override
	public void unlock(String key) {
		exec(r -> r.del(key));
	}

	@Override
	public boolean lock(String key, long ms) {
		return lock(key, 1, ms);
	}

	@Override
	public boolean lock(String key, int seconds, long timeout) {
		try {
			exec(r -> {
				// 当前时间
				long curr = System.currentTimeMillis();
				// 检查分布式锁是否存在 如果存在循环等待
				while (r.exists(key)) {
					// 等待5毫秒
					ThreadUtil.sleep(5L);
					// 检查是否超时
					if (timeout > 0 && System.currentTimeMillis() - curr > timeout)
						throw new RuntimeException("timeout ..");
				}
				// 加锁
//				if (r.exists(key))
//					throw new RuntimeException("other server is lock");
				if (seconds == -1)
					r.set(key, key);
				else
					r.setex(key, seconds, key);
			});
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据传入类型转换成String
	 * 
	 * @param  o
	 * @return
	 */
	protected String toString(Object o) {
		return o instanceof Number || o instanceof String ? C.toString(o) : JsonEngine.toJson(o);
	}
}
