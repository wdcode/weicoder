package com.weicoder.core.dao.datasource.impl;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.log.Logs;
import com.weicoder.core.dao.datasource.base.BaseDataSource;
import com.weicoder.core.params.DataSourceParams;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0连接池实现
 * @author WD 
 *   
 */
public final class C3P0 extends BaseDataSource {
	// C3P0数据源
	private ComboPooledDataSource ds;

	public C3P0(String name) {
		super(name);
		ds = new ComboPooledDataSource();
		// 设置属性 
		try {
			ds.setDriverClass((DataSourceParams.getDriver(name)));
			ds.setJdbcUrl(DataSourceParams.getUrl(name));
			ds.setUser(DataSourceParams.getUser(name));
			ds.setPassword(DataSourceParams.getPassword(name));
			ds.setMaxPoolSize(DataSourceParams.getMaxPoolSize(name));
			ds.setMinPoolSize(DataSourceParams.getMinPoolSize(name));
			ds.setLoginTimeout(Conversion.toInt(DataSourceParams.getTimeout(name)));
			ds.setIdleConnectionTestPeriod(Conversion.toInt(DataSourceParams.getIdleTimeout(name)));
			ds.setInitialPoolSize(DataSourceParams.getInitialPoolSize(name));
			ds.setMaxIdleTime(Conversion.toInt(DataSourceParams.getMaxIdleTime(name)));
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	@Override
	public DataSource getDataSource() {
		return ds;
	}
}
