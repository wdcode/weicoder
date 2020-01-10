package com.weicoder.redis.base;

import java.util.List;

import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.ThreadUtil;
import com.weicoder.common.zip.ZipEngine;
import com.weicoder.redis.RedisPool;

/**
 * Redis基类
 * 
 * @author WD
 */
public abstract class BaseRedis implements RedisPool {
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
	public void lock(String key) {
		lock(key, -1L);
	}

	@Override
	public boolean lock(String key, int s) {
		return lock(key, 1000L);
	}

	@Override
	public void unlock(String key) {
		exec(r -> r.del(key));
	}

	@Override
	public boolean lock(String key, long ms) {
		try {
			exec(r -> {
				// 当前时间
				long curr = System.currentTimeMillis();
				// 检查分布式锁是否存在 如果存在循环等待
				while (r.exists(key)) {
					// 等待5毫秒
					ThreadUtil.sleep(2L);
					// 检查是否超时
					if (ms > 0 && System.currentTimeMillis() - curr > ms)
						throw new RuntimeException("timeout ..");
				}
				// 加锁
				r.setex(key, 1, key);
			});
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
