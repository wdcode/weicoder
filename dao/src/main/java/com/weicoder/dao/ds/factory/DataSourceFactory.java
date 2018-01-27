package com.weicoder.dao.ds.factory;

import javax.sql.DataSource;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.dao.ds.impl.Druid;
import com.weicoder.dao.ds.impl.Tomcat;
import com.weicoder.dao.params.DataSourceParams;

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
		// 判断数据源
		switch (DataSourceParams.getParse(key)) {
			case "tomcat":
				return new Tomcat(key).getDataSource();
			default:
				return new Druid(key).getDataSource();
		}
	}

	private DataSourceFactory() {}
}
