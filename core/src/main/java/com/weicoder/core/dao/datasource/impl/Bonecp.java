package com.weicoder.core.dao.datasource.impl;

import com.weicoder.core.dao.datasource.base.BaseDataSource;
import com.weicoder.core.params.DataSourceParams;

import javax.sql.DataSource;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * bonecp连接池实现
 * @author WD 
 *  
 */
public final class Bonecp extends BaseDataSource {
	// BoneCPDataSource数据源
	private BoneCPDataSource ds;

	public Bonecp(String name) {
		super(name);
		ds = new BoneCPDataSource();
		// 设置属性 
		ds.setDriverClass((DataSourceParams.getDriver(name)));
		ds.setJdbcUrl(DataSourceParams.getUrl(name));
		ds.setUser(DataSourceParams.getUser(name));
		ds.setPassword(DataSourceParams.getPassword(name));
		ds.setMaxConnectionsPerPartition(DataSourceParams.getMaxPoolSize(name));
		ds.setMinConnectionsPerPartition(DataSourceParams.getMinPoolSize(name));
		ds.setConnectionTimeoutInMs(DataSourceParams.getTimeout(name));
		ds.setIdleConnectionTestPeriodInSeconds(DataSourceParams.getIdleTimeout(name));
		ds.setPoolAvailabilityThreshold(DataSourceParams.getInitialPoolSize(name));
		ds.setIdleMaxAgeInSeconds(DataSourceParams.getMaxIdleTime(name));
	}

	@Override
	public DataSource getDataSource() {
		return ds;
	}
}
