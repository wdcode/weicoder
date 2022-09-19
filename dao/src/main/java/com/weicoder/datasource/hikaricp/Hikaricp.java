package com.weicoder.datasource.hikaricp;

import javax.sql.DataSource;

import com.weicoder.common.params.DataSourceParams;
import com.weicoder.datasource.base.BaseDataSource;
import com.zaxxer.hikari.HikariDataSource;

/**
 * HikariCP 连接池
 * 
 * @author wudi
 */
public class Hikaricp extends BaseDataSource {

	public Hikaricp(String name) {
		super(name); 
	}

	@Override
	public DataSource init(String name) {
		HikariDataSource ds = new HikariDataSource();
		ds.setDriverClassName((DataSourceParams.getDriver(name)));
		ds.setJdbcUrl(DataSourceParams.getUrl(name));
		ds.setUsername(DataSourceParams.getUser(name));
		ds.setPassword(DataSourceParams.getPassword(name));
		ds.setMaximumPoolSize(DataSourceParams.getMaxPoolSize(name));
		ds.setMinimumIdle(DataSourceParams.getMinPoolSize(name));
		return ds;
	}
}
