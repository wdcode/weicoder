package com.weicoder.hbase.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.hbase.HBase;
import com.weicoder.hbase.impl.HBaseImpl;
import com.weicoder.hbase.params.HBaseParams;

/**
 * HBase工厂
 * 
 * @author  WD
 * @version 1.0
 */
public final class HBaseFactory extends FactoryKey<String, HBase> {
	// 工厂
	private final static HBaseFactory FACTORY = new HBaseFactory();

	private HBaseFactory() {
	}

	/**
	 * 获得HBase
	 * 
	 * @return HBase
	 */
	public static HBase getHBase() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得HBase
	 * 
	 * @return HBase
	 */
	public static HBase getHBase(String key) {
		return FACTORY.getInstance(key);
	}

	/**
	 * 实例化一个新对象
	 */
	public HBase newInstance() {
		return newInstance(HBaseParams.HOST, HBaseParams.PORT);
	}

	/**
	 * 实例化一个新对象
	 * 
	 * @param  host 主机
	 * @param  port 端口
	 * @return      HBase
	 */
	public HBase newInstance(String host, int port) {
		return new HBaseImpl(host, port);
	}

	@Override
	public HBase newInstance(String key) {
		return null;
	}
}
