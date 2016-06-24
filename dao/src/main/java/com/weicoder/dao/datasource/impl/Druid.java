package com.weicoder.dao.datasource.impl;

import com.weicoder.common.lang.Conversion;
import com.weicoder.dao.datasource.base.BaseDataSource;
import com.weicoder.dao.params.DataSourceParams;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 淘宝 druid连接池 实现
 * @author WD  
 */
public final class Druid extends BaseDataSource {
	// DruidDataSource数据源
	private DruidDataSource ds;

	public Druid(String name) {
		super(name);
		System.setProperty("druid.logType", "slf4j");
		ds = new DruidDataSource();
		ds.setDriverClassName((DataSourceParams.getDriver(name)));
		ds.setUrl(DataSourceParams.getUrl(name));
		ds.setUsername(DataSourceParams.getUser(name));
		ds.setPassword(DataSourceParams.getPassword(name));
		ds.setMaxActive(DataSourceParams.getMaxPoolSize(name));
		ds.setMinIdle(DataSourceParams.getMinPoolSize(name));
		ds.setValidationQueryTimeout(Conversion.toInt(DataSourceParams.getTimeout(name)));
		ds.setTimeBetweenEvictionRunsMillis(DataSourceParams.getIdleTimeout(name));
		ds.setInitialSize(DataSourceParams.getInitialPoolSize(name));
		ds.setMaxWait(DataSourceParams.getMaxIdleTime(name));
	}

	@Override
	public DataSource getDataSource() {
		return ds;
	}
}
