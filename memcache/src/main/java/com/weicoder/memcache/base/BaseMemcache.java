package com.weicoder.memcache.base;

import java.util.List;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.statics.Zips;
import com.weicoder.memcache.params.MemcacheParams;
import com.weicoder.memcache.Memcache;

/**
 * MemCacheClient基础抽象
 * @author WD
 */
public abstract class BaseMemcache implements Memcache {
	/**
	 * 构造方法
	 */
	protected BaseMemcache() {}

	/**
	 * 压缩值 当值能压缩时才压缩
	 * @param key 键
	 * @param value 值
	 */
	public final boolean compress(String key, Object value) {
		return set(key, Zips.compress(value));
	}

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * @param key 键
	 * @return 值
	 */
	public final byte[] extract(String key) {
		return Zips.extract(get(key));
	}

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
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
	 * @param keys 键
	 * @return 值
	 */
	public List<byte[]> extract(String... keys) {
		// 声明列表
		List<byte[]> list = Lists.newList(keys.length);
		// 循环解压数据
		for (Object o : get(keys)) {
			list.add(Zips.extract(o));
		}
		// 返回列表
		return list;
	}

	/**
	 * 构造函数
	 * @param name 名称key
	 */
	public BaseMemcache(String name) {
		init(name, MemcacheParams.getServers(name), MemcacheParams.getWeights(name), MemcacheParams.getInitConn(name), MemcacheParams.getMinConn(name), MemcacheParams.getMaxConn(name),
				MemcacheParams.getMaxIdle(name), MemcacheParams.getSleep(name), MemcacheParams.getTO(name), MemcacheParams.getConnectTO(name), MemcacheParams.getBinary(name));

	}

	/**
	 * 初始化方法
	 * @param name 名称
	 * @param servers 服务器地址
	 * @param weights 权重列表
	 * @param initConn 初始化连接
	 * @param minConn 最小连接
	 * @param maxConn 最大连接
	 * @param maxIdle 空闲时间
	 * @param maintSleep 睡眠时间
	 * @param socketTO 超时读取
	 * @param socketConnectTO 连接超时
	 * @param binary 是否使用binary(二进制协议)
	 */
	protected abstract void init(String name, String[] servers, Integer[] weights, int initConn, int minConn, int maxConn, long maxIdle, long maintSleep, int socketTO, int socketConnectTO,
			boolean binary);
}
