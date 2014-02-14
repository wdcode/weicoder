package com.weicoder.core.dao.datasource.factory;

import com.weicoder.core.dao.datasource.BasicDataSource;
import com.weicoder.core.dao.datasource.DataSource;
import com.weicoder.core.factory.FactoryKey;
import com.weicoder.core.params.DataSourceParams;

/**
 * 获得数据源 DataSource 的工厂类
 * @author WD
 * @since JDK7
 * @version 1.0 2011-05-26
 */
public final class DataSourceFactory extends FactoryKey<String, DataSource> {
	// 工厂
	private final static DataSourceFactory	FACTORY	= new DataSourceFactory();

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
		// 声明数据源
		BasicDataSource ds = new BasicDataSource();
		// 设置属性
		ds.setParse(DataSourceParams.getParse(key));
		ds.setDriver(DataSourceParams.getDriver(key));
		ds.setUrl(DataSourceParams.getUrl(key));
		ds.setUser(DataSourceParams.getUser(key));
		ds.setPassword(DataSourceParams.getPassword(key));
		ds.setMaxPoolSize(DataSourceParams.getMaxPoolSize(key));
		ds.setMinPoolSize(DataSourceParams.getMinPoolSize(key));
		ds.setMaxSize(DataSourceParams.getMaxSize(key));
		ds.setTimeout(DataSourceParams.getTimeout(key));
		ds.setIdleTimeout(DataSourceParams.getIdleTimeout(key));
		ds.setInitialPoolSize(DataSourceParams.getInitialPoolSize(key));
		ds.setMaxIdleTime(DataSourceParams.getMaxIdleTime(key));
		// 返回数据源
		return ds;
	}

	/**
	 * 私有构造
	 */
	private DataSourceFactory() {}
}
