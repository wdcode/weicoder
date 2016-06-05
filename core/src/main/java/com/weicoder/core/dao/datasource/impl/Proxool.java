package com.weicoder.core.dao.datasource.impl;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolDataSource;
import com.weicoder.core.dao.datasource.base.BaseDataSource;
import com.weicoder.core.params.DataSourceParams;

/**
 * Proxool连接池实现
 * @author WD 
 *   
 */
public final class Proxool extends BaseDataSource {
	// ProxoolDataSource数据源
	private ProxoolDataSource ds;

	public Proxool(String name) {
		super(name);
		ds = new ProxoolDataSource();
		ds.setDriver((DataSourceParams.getDriver(name)));
		ds.setDriverUrl(DataSourceParams.getUrl(name));
		ds.setUser(DataSourceParams.getUser(name));
		ds.setPassword(DataSourceParams.getPassword(name));
		ds.setMaximumConnectionCount(DataSourceParams.getMaxPoolSize(name));
		ds.setMinimumConnectionCount(DataSourceParams.getMinPoolSize(name));
		ds.setMaximumActiveTime(DataSourceParams.getTimeout(name));
	}

	@Override
	public DataSource getDataSource() {
		return ds;
	}
}
