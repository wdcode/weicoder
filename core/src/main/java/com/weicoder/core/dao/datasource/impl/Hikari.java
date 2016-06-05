package com.weicoder.core.dao.datasource.impl;

import javax.sql.DataSource;

import com.weicoder.core.dao.datasource.base.BaseDataSource;
import com.weicoder.core.params.DataSourceParams;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Hikari 数据源
 * @author WD
 */
public class Hikari extends BaseDataSource {
	//数据源
	HikariDataSource ds;

	public Hikari(String name) {
		super(name);
		ds = new HikariDataSource();
		ds.setDriverClassName((DataSourceParams.getDriver(name)));
		ds.setJdbcUrl(DataSourceParams.getUrl(name));
		ds.setUsername(DataSourceParams.getUser(name));
		ds.setPassword(DataSourceParams.getPassword(name));
		ds.setMaximumPoolSize(DataSourceParams.getMaxPoolSize(name));
		ds.setMinimumIdle(DataSourceParams.getMinPoolSize(name));
		ds.setConnectionTimeout(DataSourceParams.getTimeout(name));
		ds.setIdleTimeout(DataSourceParams.getIdleTimeout(name));
		ds.setMaxLifetime(DataSourceParams.getMaxIdleTime(name));
	}

	@Override
	public DataSource getDataSource() {
		return ds;
	}
}
