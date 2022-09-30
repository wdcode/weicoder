package com.weicoder.datasource.hikaricp;

import javax.sql.DataSource;

import static com.weicoder.datasource.params.DataSourceParams.*;

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
		ds.setDriverClassName((getDriver(name)));
		ds.setJdbcUrl(getUrl(name));
		ds.setUsername(getUser(name));
		ds.setPassword(getPassword(name));
		ds.setMaximumPoolSize(getMaxPoolSize(name));
		ds.setMinimumIdle(getMinPoolSize(name));
		return ds;
	}
}
