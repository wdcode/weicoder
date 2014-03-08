package com.weicoder.core.dao.datasource.impl;

import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.logicalcobwebs.proxool.ProxoolFacade;
import com.weicoder.common.lang.Conversion;
import com.weicoder.core.dao.datasource.base.BaseDataSource;

/**
 * Proxool连接池实现
 * @author WD
 * @since JDK7
 * @version 1.0 2010-1-23
 */
public final class Proxool extends BaseDataSource {
	// ProxoolDataSource数据源
	private ProxoolDataSource	ds	= new ProxoolDataSource();

	/**
	 * 获得多长时间检查一次空闲连接
	 */
	public long getIdleTimeout() {
		return ds.getHouseKeepingSleepTime();
	}

	/**
	 * 获得初始化连接数
	 */
	public int getInitialPoolSize() {
		return ds.getMinimumConnectionCount();
	}

	/**
	 * 获得测试空闲连接时间 超出时间回收
	 */
	public long getMaxIdleTime() {
		return ds.getMaximumActiveTime();
	}

	/**
	 * 获得连接池最大连接数
	 */
	public int getMaxPoolSize() {
		return ds.getMaximumConnectionCount();
	}

	/**
	 * 获得最大连接数
	 */
	public int getMaxSize() {
		return ds.getMaximumConnectionCount();
	}

	/**
	 * 获得连接池最小连接数
	 */
	public int getMinPoolSize() {
		return ds.getMinimumConnectionCount();
	}

	/**
	 * 获得超时等待时间
	 */
	public long getTimeout() {
		return ds.getOverloadWithoutRefusalLifetime();
	}

	/**
	 * 设置多长时间检查一次空闲连接
	 */
	public void setIdleTimeout(long idleTimeout) {
		ds.setHouseKeepingSleepTime(Conversion.toInt(idleTimeout));
	}

	/**
	 * 设置初始化连接数
	 */
	public void setInitialPoolSize(int initialPoolSize) {
		ds.setMinimumConnectionCount(initialPoolSize);
	}

	/**
	 * 设置 测试空闲连接时间 超出时间回收
	 */
	public void setMaxIdleTime(long maxIdleTime) {
		ds.setMaximumActiveTime(maxIdleTime);
	}

	/**
	 * 设置连接池最大连接数
	 */
	public void setMaxPoolSize(int maxPoolSize) {
		ds.setMaximumConnectionCount(maxPoolSize);
	}

	/**
	 * 设置最大连接数
	 */
	public void setMaxSize(int maxSize) {
		ds.setMaximumConnectionCount(maxSize);
	}

	/**
	 * 设置连接池最小连接数
	 */
	public void setMinPoolSize(int minPoolSize) {
		ds.setMinimumConnectionCount(minPoolSize);
	}

	/**
	 * 设置超时等待时间
	 */
	public void setTimeout(long timeout) {
		ds.setOverloadWithoutRefusalLifetime(Conversion.toInt(timeout));
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		ProxoolFacade.shutdown();
	}

	/**
	 * 获得url
	 */
	public String getUrl() {
		return ds.getDriverUrl();
	}

	/**
	 * 设置url
	 */
	public void setUrl(String url) {
		ds.setDriverUrl(url);
	}

	@Override
	public String getDriver() {
		return ds.getDriver();
	}

	@Override
	public void setDriver(String driver) {
		ds.setDriver(driver);
	}

	@Override
	public String getUser() {
		return ds.getUser();
	}

	@Override
	public void setUser(String user) {
		ds.setUser(user);
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
