package com.weicoder.core.dao.datasource.impl;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import com.weicoder.core.log.Logs;
import com.weicoder.core.dao.datasource.base.BaseDataSource;

/**
 * DBCP连接池实现
 * @author WD
 * @since JDK7
 * @version 1.0 2010-01-23
 */
public final class DBCP extends BaseDataSource {
	// BasicDataSource数据源
	private BasicDataSource	ds = new BasicDataSource();

	/**
	 * 获得驱动类
	 */
	public String getDriver() {
		return ds.getDriverClassName();
	}

	/**
	 * 获得多长时间检查一次空闲连接
	 */
	public long getIdleTimeout() {
		return ds.getTimeBetweenEvictionRunsMillis();
	}

	/**
	 * 获得初始化连接数
	 */
	public int getInitialPoolSize() {
		return ds.getInitialSize();
	}

	/**
	 * 获得测试空闲连接时间 超出时间回收
	 */
	public long getMaxIdleTime() {
		return ds.getMinEvictableIdleTimeMillis();
	}

	/**
	 * 获得连接池最大连接数
	 */
	public int getMaxPoolSize() {
		return ds.getMaxIdle();
	}

	/**
	 * 获得最大连接数
	 */
	public int getMaxSize() {
		return ds.getMaxActive();
	}

	/**
	 * 获得连接池最小连接数
	 */
	public int getMinPoolSize() {
		return ds.getMinIdle();
	}

	/**
	 * 获得超时等待时间
	 */
	public long getTimeout() {
		return ds.getMaxWait();
	}

	/**
	 * 获得用户名
	 */
	public String getUser() {
		return ds.getUsername();
	}

	/**
	 * 设置驱动类
	 */
	public void setDriver(String driver) {
		ds.setDriverClassName(driver);
	}

	/**
	 * 设置多长时间检查一次空闲连接
	 */
	public void setIdleTimeout(long idleTimeout) {
		ds.setTimeBetweenEvictionRunsMillis(idleTimeout);
	}

	/**
	 * 设置初始化连接数
	 */
	public void setInitialPoolSize(int initialPoolSize) {
		ds.setInitialSize(initialPoolSize);
	}

	/**
	 * 设置 测试空闲连接时间 超出时间回收
	 */
	public void setMaxIdleTime(long maxIdleTime) {
		ds.setMinEvictableIdleTimeMillis(maxIdleTime);
	}

	/**
	 * 设置连接池最大连接数
	 */
	public void setMaxPoolSize(int maxPoolSize) {
		ds.setMaxIdle(maxPoolSize);
	}

	/**
	 * 设置最大连接数
	 */
	public void setMaxSize(int maxSize) {
		ds.setMaxActive(maxSize);
	}

	/**
	 * 设置连接池最小连接数
	 */
	public void setMinPoolSize(int minPoolSize) {
		ds.setMinIdle(minPoolSize);
	}

	/**
	 * 设置超时等待时间
	 */
	public void setTimeout(long timeout) {
		ds.setMaxWait(timeout);
	}

	/**
	 * 设置用户
	 */
	public void setUser(String user) {
		ds.setUsername(user);
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		try {
			ds.close();
		} catch (SQLException e) {
			Logs.warn(e);
		}
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
	public String getPassword() {
		return ds.getPassword();
	}

	@Override
	public void setPassword(String password) {
		ds.setPassword(password);
	}

	@Override
	protected javax.sql.DataSource getDataSource() {
		return ds;
	}
}
