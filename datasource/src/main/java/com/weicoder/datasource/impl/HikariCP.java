package com.weicoder.datasource.impl;

import javax.sql.DataSource;

import com.weicoder.datasource.base.BaseDataSource;
import com.weicoder.datasource.params.DataSourceParams;
import com.zaxxer.hikari.HikariDataSource;

/**
 * HikariCP 连接池
 * 
 * @author wudi
 */
public class HikariCP extends BaseDataSource {
	//HikariCP 连接池
	private HikariDataSource ds;

	public HikariCP(String name) {
		super(name); 
		ds = new HikariDataSource();
		ds.setDriverClassName((DataSourceParams.getDriver(name)));
		ds.setJdbcUrl(DataSourceParams.getUrl(name));
		ds.setUsername(DataSourceParams.getUser(name));
		ds.setPassword(DataSourceParams.getPassword(name));
		ds.setMaximumPoolSize(DataSourceParams.getMaxPoolSize(name));
		ds.setMinimumIdle(DataSourceParams.getMinPoolSize(name));
//		ds.setMinIdle(DataSourceParams.getMinPoolSize(name));
//		ds.setValidationQueryTimeout(300000);
//		ds.setTimeBetweenEvictionRunsMillis(60000);
//		ds.setInitialSize(DataSourceParams.getInitialPoolSize(name));
//		ds.setMaxWait(DataSourceParams.getMaxIdleTime(name));
//		ds.setValidationQuery("SELECT 1");
	}

	@Override
	public DataSource getDataSource() { 
		return ds;
	}
}
