package com.weicoder.datasource.factory;

import javax.sql.DataSource;

import com.weicoder.common.factory.FactoryKey; 
import com.weicoder.datasource.impl.HikariCP;

/**
 * 获得数据源 DataSource 的工厂类
 * @author WD
 */
public final class DataSourceFactory extends FactoryKey<String, DataSource> {
	// 工厂
	private final static DataSourceFactory FACTORY = new DataSourceFactory();

	/**
	 * 返回DataSource
	 * @return DataSource
	 */
	public static DataSource getDataSource() {
		return FACTORY.getInstance();
	}

	/**
	 * 返回DataSource
	 * @param key 配置键
	 * @return DataSource
	 */
	public static DataSource getDataSource(String key) {
		return FACTORY.getInstance(key);
	}

	/**
	 * 实例化一个新对象
	 */
	public DataSource newInstance(String key) {
//		return new Druid(key).getDataSource();
		return new HikariCP(key).getDataSource();
	}

	private DataSourceFactory() {}
}
