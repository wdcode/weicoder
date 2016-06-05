package com.weicoder.core.dao.datasource.impl;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.weicoder.common.lang.Conversion;
import com.weicoder.core.dao.datasource.base.BaseDataSource;
import com.weicoder.core.params.DataSourceParams;

/**
 * DBCP连接池实现
 * @author WD 
 *   
 */
public final class DBCP extends BaseDataSource {
	// BasicDataSource数据源
	private BasicDataSource ds;

	public DBCP(String name) {
		super(name);
		ds = new BasicDataSource();
		ds.setDriverClassName((DataSourceParams.getDriver(name)));
		ds.setUrl(DataSourceParams.getUrl(name));
		ds.setUsername(DataSourceParams.getUser(name));
		ds.setPassword(DataSourceParams.getPassword(name));
		ds.setMaxActive(DataSourceParams.getMaxPoolSize(name));
		ds.setMinIdle(DataSourceParams.getMinPoolSize(name));
		ds.setValidationQueryTimeout(Conversion.toInt(DataSourceParams.getTimeout(name)));
		ds.setTimeBetweenEvictionRunsMillis(DataSourceParams.getIdleTimeout(name));
		ds.setInitialSize(DataSourceParams.getInitialPoolSize(name));
		ds.setMaxIdle(Conversion.toInt(DataSourceParams.getMaxIdleTime(name)));
	}

	@Override
	public DataSource getDataSource() {
		return ds;
	}
}
