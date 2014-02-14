package com.weicoder.core.nosql.memcache.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.DefaultConnectionFactory;
import net.spy.memcached.MemcachedClient;

import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.log.Logs;
import com.weicoder.core.nosql.memcache.base.BaseMemcache;

/**
 * spymemcached api
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-27
 */
public final class MemcacheSpy extends BaseMemcache {
	// spymemcached client
	private MemcachedClient	client;

	/**
	 * 构造方法
	 * @param name
	 */
	public MemcacheSpy(String name) {
		super(name);
	}

	@Override
	public boolean append(String key, Object value) {
		try {
			return client.append(key, value).get();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Map<String, Object> getMap(String... keys) {
		return client.getBulk(keys);
	}

	@Override
	public Object[] get(String... keys) {
		return getMap(keys).values().toArray();
	}

	@Override
	public boolean set(String key, Object value) {
		try {
			return client.set(key, getExt(), value).get();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Object get(String key) {
		return client.get(key);
	}

	@Override
	public void remove(String... key) {
		for (String k : key) {
			client.delete(k);
		}
	}

	@Override
	public boolean exists(String key) {
		return !EmptyUtil.isEmpty(get(key));
	}

	@Override
	public void clear() {
		try {
			client.flush();
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	@Override
	public void close() {
		client.shutdown();
	}

	@Override
	protected void init(String name, String[] servers, Integer[] weights, int initConn, int minConn, int maxConn, long maxIdle, long maintSleep, int socketTO, int socketConnectTO, boolean binary) {
		try {
			client = new MemcachedClient(binary ? new BinaryConnectionFactory() : new DefaultConnectionFactory(), getAddress(servers));
		} catch (IOException e) {
			Logs.warn(e);
		}
	}

	/**
	 * 根据服务器获得地址
	 * @param servers
	 * @return
	 */
	private List<InetSocketAddress> getAddress(String[] servers) {
		// 声明地址列表
		List<InetSocketAddress> address = Lists.getList(servers.length);
		// 循环服务器生成地址
		for (String server : servers) {
			String[] host = server.split(StringConstants.COLON);
			address.add(new InetSocketAddress(host[0], Conversion.toInt(host[1])));
		}
		// 返回地址
		return address;
	}

	/**
	 * 获得缓存保存时间
	 * @return 缓存保存时间
	 */
	private int getExt() {
		return DateConstants.DAY;
	}
}
