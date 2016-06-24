package com.weicoder.dao.datasource.impl;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.weicoder.common.lang.Conversion;
import com.weicoder.dao.datasource.base.BaseDataSource;
import com.weicoder.dao.params.DataSourceParams;

/**
 * tomcat jdbc 连接池
 * @author WD
 */
public class Tomcat extends BaseDataSource {
	//数据源
	private DataSource ds;

	public Tomcat(String name) {
		super(name);
		ds = new DataSource();
		ds.setDriverClassName((DataSourceParams.getDriver(name)));
		ds.setUrl(DataSourceParams.getUrl(name));
		ds.setUsername(DataSourceParams.getUser(name));
		ds.setPassword(DataSourceParams.getPassword(name));
		ds.setMaxActive(DataSourceParams.getMaxPoolSize(name));
		ds.setMinIdle(DataSourceParams.getMinPoolSize(name));
		ds.setValidationQueryTimeout(Conversion.toInt(DataSourceParams.getTimeout(name)));
		ds.setTimeBetweenEvictionRunsMillis(Conversion.toInt(DataSourceParams.getIdleTimeout(name)));
		ds.setInitialSize(DataSourceParams.getInitialPoolSize(name));
		ds.setMaxIdle(Conversion.toInt(DataSourceParams.getMinPoolSize(name)));
	}

	@Override
	public javax.sql.DataSource getDataSource() {
		return ds;
	}
}
