package com.weicoder.datasource.impl;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.weicoder.datasource.base.BaseDataSource;
import com.weicoder.datasource.params.DataSourceParams;

/**
 * DBCP连接池实现
 * 
 * @author  WD
 * @version 1.0
 */
public final class DBCP2 extends BaseDataSource {
	// BasicDataSource数据源
	private BasicDataSource ds;

	public DBCP2(String name) {
		super(name);
		ds = new BasicDataSource();
		ds.setDriverClassName((DataSourceParams.getDriver(name)));
		ds.setUrl(DataSourceParams.getUrl(name));
		ds.setUsername(DataSourceParams.getUser(name));
		ds.setPassword(DataSourceParams.getPassword(name));
		ds.setMaxTotal(DataSourceParams.getMaxPoolSize(name));
		ds.setMinIdle(DataSourceParams.getMinPoolSize(name));
		ds.setValidationQueryTimeout(300000);
		ds.setTimeBetweenEvictionRunsMillis(60000);
		ds.setInitialSize(DataSourceParams.getInitialPoolSize(name));
		ds.setMaxWaitMillis(DataSourceParams.getMaxIdleTime(name));
		ds.setValidationQuery("SELECT 1");
	}

	@Override
	public DataSource getDataSource() {
		return ds;
	}
}
