package com.weicoder.core.nosql.hbase.factory;

import com.weicoder.core.factory.FactoryKey;
import com.weicoder.core.nosql.hbase.HBase;
import com.weicoder.core.params.CoreParams;

/**
 * HBase工厂
 * @author WD
 * @since JDK7
 * @version 1.0 2010-12-12
 */
public final class HBaseFactory extends FactoryKey<String, HBase> {
	// 工厂
	private final static HBaseFactory	FACTORY;

	/**
	 * 静态初始化
	 */
	static {
		FACTORY = new HBaseFactory();
	}

	/**
	 * 私有构造
	 */
	private HBaseFactory() {}

	/**
	 * 获得HBase
	 * @return HBase
	 */
	public static HBase getHBase() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得HBase
	 * @return HBase
	 */
	public static HBase getHBase(String key) {
		return FACTORY.getInstance(key);
	}

	/**
	 * 实例化一个新对象
	 */
	public HBase newInstance() {
		return newInstance(CoreParams.NOSQL_HBASE_HOST, CoreParams.NOSQL_HBASE_PORT);
	}

	/**
	 * 实例化一个新对象
	 * @param host 主机
	 * @param port 端口
	 * @return HBase
	 */
	public HBase newInstance(String host, int port) {
		return new HBaseImpl(host, port);
	}

	@Override
	public HBase newInstance(String key) {
		return null;
	}
}
