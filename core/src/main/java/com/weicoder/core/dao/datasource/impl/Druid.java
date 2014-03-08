package com.weicoder.core.dao.datasource.impl;

import com.weicoder.common.util.CloseUtil;
import com.weicoder.core.dao.datasource.base.BaseDataSource;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 淘宝 druid连接池 实现
 * @author WD
 * @since JDK6
 * @version 1.0 2013-03-18
 */
public final class Druid extends BaseDataSource {
	// DruidDataSource数据源
	private DruidDataSource	ds	= new DruidDataSource();

	@Override
	public void close() {
		CloseUtil.close(ds);
	}

	@Override
	public String getDriver() {
		return ds.getDriverClassName();
	}

	@Override
	public void setDriver(String driver) {
		ds.setDriverClassName(driver);
	}

	@Override
	public String getUrl() {
		return ds.getUrl();
	}

	@Override
	public void setUrl(String url) {
		ds.setUrl(url);
	}

	@Override
	public String getUser() {
		return ds.getUsername();
	}

	@Override
	public void setUser(String user) {
		ds.setUsername(user);
	}

	@Override
	public String getPassword() {
		return ds.getPassword();
	}

	@Override
	public void setPassword(String password) {
		ds.setPassword(password);
	}

	@Override
	public int getInitialPoolSize() {
		return ds.getInitialSize();
	}

	@Override
	public void setInitialPoolSize(int initialPoolSize) {
		ds.setInitialSize(initialPoolSize);
	}

	@Override
	public int getMaxPoolSize() {
		return ds.getMaxActive();
	}

	@Override
	public void setMaxPoolSize(int maxPoolSize) {
		ds.setMaxActive(maxPoolSize);
	}

	@Override
	public int getMinPoolSize() {
		return 0;
	}

	@Override
	public void setMinPoolSize(int minPoolSize) {}

	@Override
	public int getMaxSize() {
		return 0;
	}

	@Override
	public void setMaxSize(int maxSize) {}

	@Override
	public long getTimeout() {
		return 0;
	}

	@Override
	public void setTimeout(long timeout) {}

	@Override
	public long getMaxIdleTime() {
		return 0;
	}

	@Override
	public void setMaxIdleTime(long maxIdleTime) {}

	@Override
	public long getIdleTimeout() {
		return 0;
	}

	@Override
	public void setIdleTimeout(long idleTimeout) {}

	@Override
	protected javax.sql.DataSource getDataSource() {
		return ds;
	}
}
