package com.weicoder.nosql.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.log.Logs;

/**
 * Zookeeper 客户端
 * @author WD
 */
public final class ZookeeperClient {
	/** Zookeeper客户端 */
	private final static CuratorFramework curatorFramework;
	static {
		// 初始化CuratorFramework
		curatorFramework = CuratorFrameworkFactory.builder().connectString("").authorization("digest", "show:jufan`123~!@#".getBytes()).retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.namespace("show").build();
		// 启动
		curatorFramework.start();
	}

	/**
	 * 设置ZK节点数据
	 * @param path 节点
	 * @param bytes 数据
	 */
	public void set(String path, byte[] bytes) {
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
	 * 设置ZK节点数据
	 * @param path 节点
	 * @param bytes 数据
	 */
	public void setWithPersistent(String path, byte[] bytes) {
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
	public void delete(String path) {
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
	public void createWithPersistent(String path, byte[] bytes) {
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
	public void create(String path, byte[] bytes) {
		try {
			curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, bytes);
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 获得节点数据
	 * @param path 节点
	 * @return
	 */
	public byte[] get(String path) {
		try {
			return curatorFramework.getData().forPath(path);
		} catch (Exception e) {
			return ArrayConstants.BYTES_EMPTY;
		}
	}
}
