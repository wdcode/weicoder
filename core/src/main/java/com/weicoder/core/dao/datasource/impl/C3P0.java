package com.weicoder.core.dao.datasource.impl;

import com.weicoder.common.lang.Conversion;
import com.weicoder.core.log.Logs;
import com.weicoder.core.dao.datasource.base.BaseDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0连接池实现
 * @author WD
 * @since JDK7
 * @version 1.0 2010-01-23
 */
public final class C3P0 extends BaseDataSource {
	// C3P0数据源
	private ComboPooledDataSource	ds	= new ComboPooledDataSource();

	/**
	 * 获得驱动类
	 */
	public String getDriver() {
		return ds.getDriverClass();
	}

	/**
	 * 获得多长时间检查一次空闲连接
	 */
	public long getIdleTimeout() {
		return ds.getMaxIdleTime();
	}

	/**
	 * 获得初始化连接数
	 */
	public int getInitialPoolSize() {
		return ds.getInitialPoolSize();
	}

	/**
	 * 获得测试空闲连接时间 超出时间回收
	 */
	public long getMaxIdleTime() {
		return ds.getMaxIdleTime();
	}

	/**
	 * 获得连接池最大连接数
	 */
	public int getMaxPoolSize() {
		return ds.getMaxPoolSize();
	}

	/**
	 * 获得最大连接数
	 */
	public int getMaxSize() {
		return ds.getMaxConnectionAge();
	}

	/**
	 * 获得连接池最小连接数
	 */
	public int getMinPoolSize() {
		return ds.getMinPoolSize();
	}

	/**
	 * 获得超时等待时间
	 */
	public long getTimeout() {
		return ds.getCheckoutTimeout();
	}

	/**
	 * 获得用户名
	 */
	public String getUser() {
		return ds.getUser();
	}

	/**
	 * 设置驱动类
	 */
	public void setDriver(String driver) {
		try {
			ds.setDriverClass(driver);
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 设置多长时间检查一次空闲连接
	 */
	public void setIdleTimeout(long idleTimeout) {
		ds.setMaxIdleTime(Conversion.toInt(idleTimeout));
		ds.setIdleConnectionTestPeriod(Conversion.toInt(idleTimeout));
	}

	/**
	 * 设置初始化连接数
	 */
	public void setInitialPoolSize(int initialPoolSize) {
		ds.setInitialPoolSize(initialPoolSize);
	}

	/**
	 * 设置 测试空闲连接时间 超出时间回收
	 */
	public void setMaxIdleTime(long maxIdleTime) {
		ds.setMaxIdleTime(Conversion.toInt(maxIdleTime));
	}

	/**
	 * 设置连接池最大连接数
	 */
	public void setMaxPoolSize(int maxPoolSize) {
		ds.setMaxPoolSize(maxPoolSize);
	}

	/**
	 * 设置最大连接数
	 */
	public void setMaxSize(int maxSize) {
		ds.setMaxConnectionAge(maxSize);
	}

	/**
	 * 设置连接池最小连接数
	 */
	public void setMinPoolSize(int minPoolSize) {
		ds.setMinPoolSize(minPoolSize);
	}

	/**
	 * 设置超时等待时间
	 */
	public void setTimeout(long timeout) {
		ds.setCheckoutTimeout(Conversion.toInt(timeout));
	}

	/**
	 * 设置用户
	 */
	public void setUser(String user) {
		ds.setUser(user);
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		ds.close();
	}

	/**
	 * 获得密码
	 */
	public String getPassword() {
		return ds.getPassword();
	}

	/**
	 * 获得url
	 */
	public String getUrl() {
		return ds.getJdbcUrl();
	}

	/**
	 * 设置密码
	 */
	public void setPassword(String password) {
		ds.setPassword(password);
	}

	/**
	 * 设置url
	 */
	public void setUrl(String url) {
		ds.setJdbcUrl(url);
	}

	@Override
	protected javax.sql.DataSource getDataSource() {
		return ds;
	}
}
