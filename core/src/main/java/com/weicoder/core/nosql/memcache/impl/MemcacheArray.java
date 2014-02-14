package com.weicoder.core.nosql.memcache.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.ArrayUtil;
import com.weicoder.common.util.ClearUtil;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.core.nosql.memcache.Memcache;
import com.weicoder.core.nosql.memcache.base.BaseMemcache;
import com.weicoder.core.nosql.memcache.factory.MemcacheFactory;

/**
 * 集群客户端
 * @author WD
 * @since JDK7
 * @version 1.0 2011-12-20
 */
public final class MemcacheArray extends BaseMemcache {
	// 集群
	private Memcache[]			clients;
	// 声明线程池
	private ExecutorService		service;
	// 声明集群使用列表
	private List<ClientEntity>	ces;

	/**
	 * 构造方法
	 * @param names 集群名称数组
	 */
	public MemcacheArray(String[] names) {
		// 获得集群数量
		int num = names.length;
		// 初始化数组
		clients = ArrayUtil.getArray(Memcache.class, num);
		// 初始化集合
		for (int i = 0; i < num; i++) {
			clients[i] = MemcacheFactory.getMemcache(names[i]);
		}
		// 初始化线程池
		service = Executors.newFixedThreadPool(num);
		// 实例化集群使用列表
		ces = Lists.getList(num);
		// 初始化实例化集群使用列表
		for (int i = 0; i < num; i++) {
			ces.add(new ClientEntity(i));
		}
	}

	/**
	 * 根据键获得值
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key) {
		return getMemCacheClient().get(key);
	}

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	public Object[] get(String... keys) {
		return getMemCacheClient().get(keys);
	}

	/**
	 * 获得多个键的Map
	 * @param keys 键
	 * @return 值
	 */
	public Map<String, Object> getMap(String... keys) {
		return getMemCacheClient().getMap(keys);
	}

	/**
	 * 删除键值
	 * @param key 键
	 */
	public void remove(String... key) {
		// 循环删除
		for (int i = 0; i < clients.length; i++) {
			clients[i].remove(key);
		}
	}

	/**
	 * 设置键值 无论存储空间是否存在相同键，都保存
	 * @param key 键
	 * @param value 值
	 */
	public boolean set(final String key, final Object value) {
		// 循环执行
		for (int i = 0; i < clients.length; i++) {
			// 声明下标
			final int n = i;
			// 线程执行
			service.execute(new Runnable() {
				public void run() {
					clients[n].set(key, value);
				}
			});
		}
		// 返回成功
		return true;
	}

	/**
	 * 追加键值
	 * @param key 键
	 * @param value 值
	 */
	public boolean append(final String key, final Object value) {
		// 循环执行
		for (int i = 0; i < clients.length; i++) {
			// 声明下标
			final int n = i;
			// 线程执行
			service.execute(new Runnable() {
				public void run() {
					clients[n].append(key, value);
				}
			});
		}
		// 返回成功
		return true;
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		CloseUtil.close(clients);
	}

	/**
	 * 判断键是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return clients[0].exists(key);
	}

	/**
	 * 清除数据
	 */
	public void clear() {
		ClearUtil.clear(clients);
	}

	/**
	 * 获得MemCacheClient客户端
	 * @return MemCacheClient客户端
	 */
	protected Memcache getMemCacheClient() {
		// 排序列表
		Lists.sort(ces);
		// 获得列表实体
		ClientEntity ce = ces.get(0);
		// 设置使用次数
		ce.setNum(ce.getNum() + 1);
		// 返回客户端
		return clients[ce.getFlag()];
	}

	/**
	 * 初始化
	 */
	protected void init(String name, String[] servers, Integer[] weights, int initConn, int minConn, int maxConn, long maxIdle, long maintSleep, int socketTO, int socketConnectTO, boolean binary) {}

	/**
	 * 客户端实体保存使用次数
	 * @author WD
	 * @since JDK7
	 * @version 1.0 2011-12-20
	 */
	class ClientEntity implements Comparable<ClientEntity> {
		// 下标
		private int	flag;
		// 次数
		private int	num;

		/**
		 * 构造方法
		 * @param flag 下标
		 */
		public ClientEntity(int flag) {
			this.flag = flag;
		}

		/**
		 * 获得下标
		 * @return 下标
		 */
		public int getFlag() {
			return flag;
		}

		/**
		 * 设置下标
		 * @param flag 下标
		 */
		public void setFlag(int flag) {
			this.flag = flag;
		}

		/**
		 * 获得次数
		 * @return 次数
		 */
		public int getNum() {
			return num;
		}

		/**
		 * 设置次数
		 * @param num 次数
		 */
		public void setNum(int num) {
			this.num = num;
		}

		/**
		 * 对比大小
		 */
		public int compareTo(ClientEntity o) {
			return num > o.getNum() ? 1 : -1;
		}
	}
}
