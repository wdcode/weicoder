package com.weicoder.nosql.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.log.Logs;
import com.weicoder.nosql.params.ZookeeperParams;

/**
 * Zookeeper 客户端
 * @author WD
 */
public final class ZookeeperClient {
	/** Zookeeper客户端 */
	private final static CuratorFramework curatorFramework;
	static {
		// 初始化CuratorFramework
		curatorFramework = CuratorFrameworkFactory.builder().connectString(ZookeeperParams.CONNECT)
				.connectionTimeoutMs(30000).sessionTimeoutMs(30000).canBeReadOnly(false)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).defaultData(null).build();
		// 启动
		curatorFramework.start();
	}

	/**
	 * 返回CuratorFramework
	 * @return CuratorFramework
	 */
	public static CuratorFramework getCuratorFramework() {
		return curatorFramework;
	}

	/**
	 * 设置ZK节点数据
	 * @param path 节点
	 * @param bytes 数据
	 */
	public static void set(String path, byte[] bytes) {
		try {
			if (curatorFramework.checkExists().forPath(path) == null) {
				create(path, bytes);
			} else {
				curatorFramework.setData().forPath(path, bytes);
			}
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 设置ZK永久节点数据
	 * @param path 节点
	 * @param bytes 数据
	 */
	public static void setWithPersistent(String path, byte[] bytes) {
		try {
			if (curatorFramework.checkExists().forPath(path) == null) {
				createWithPersistent(path, bytes);
			} else {
				curatorFramework.setData().forPath(path, bytes);
			}
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 删除ZK节点
	 * @param path 节点
	 */
	public static void delete(String path) {
		try {
			if (curatorFramework.checkExists().forPath(path) != null) {
				curatorFramework.delete().forPath(path);
			}
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 创建ZK持久节点
	 * @param path 节点
	 * @param bytes 数据
	 */
	public static void createWithPersistent(String path, byte[] bytes) {
		try {
			curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, bytes);
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 创建ZK临时节点
	 * @param path 节点
	 * @param bytes 数据
	 */
	public static void create(String path, byte[] bytes) {
		try {
			curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, bytes);
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 获得节点数据
	 * @param path 节点
	 * @return 字节数组
	 */
	public static byte[] get(String path) {
		try {
			return curatorFramework.getData().forPath(path);
		} catch (Exception e) {
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	/**
	 * 异步获取ZK节点数据，同时自动重新注册Watcher.
	 * @param path 路径
	 * @param callback 回调
	 */
	public static void getDataAsync(final String path, final Callback callback) {
		// 异步读取数据回调
		final BackgroundCallback background = (CuratorFramework client, CuratorEvent event) -> {
			try {
				callback.callBack(event.getData());
			} catch (Exception e) {
				Logs.error(e);
			}
		};
		// 每次接收ZK事件都要重新注册Watcher，然后才异步读数据
		final Watcher watcher = new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getType() == Event.EventType.NodeDataChanged) {
					try {
						curatorFramework.getData().usingWatcher(this).inBackground(background).forPath(path);
					} catch (Exception e) {
						Logs.error(e);
					}
				}
			}
		};

		// 这里首次注册Watcher并异步读数据
		try {
			curatorFramework.getData().usingWatcher(watcher).inBackground(background).forPath(path);
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 异步回调接口
	 * @author WD
	 */
	public interface Callback {
		void callBack(byte[] obj);
	}
}
