package com.weicoder.core.nosql.memcache.base;

import com.weicoder.core.nosql.base.BaseNoSQL;
import com.weicoder.core.nosql.memcache.Memcache;
import com.weicoder.core.params.MemcacheParams;

/**
 * MemCacheClient基础抽象
 * @author WD
 * @since JDK7
 * @version 1.0 2010-11-15
 */
public abstract class BaseMemcache extends BaseNoSQL implements Memcache {
	/**
	 * 构造方法
	 */
	protected BaseMemcache() {}

	/**
	 * 构造函数
	 * @param name 名称key
	 */
	public BaseMemcache(String name) {
		init(
			name, MemcacheParams.getServers(name), MemcacheParams.getWeights(name), MemcacheParams.getInitConn(name), MemcacheParams.getMinConn(name), MemcacheParams.getMaxConn(name), MemcacheParams.getMaxIdle(name), MemcacheParams.getSleep(name), MemcacheParams.getTO(name),
			MemcacheParams.getConnectTO(name), MemcacheParams.getBinary(name));

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
	protected abstract void init(String name, String[] servers, Integer[] weights, int initConn, int minConn, int maxConn, long maxIdle, long maintSleep, int socketTO, int socketConnectTO, boolean binary);
}
