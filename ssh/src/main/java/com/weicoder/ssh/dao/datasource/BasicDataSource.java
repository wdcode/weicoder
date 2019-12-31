package com.weicoder.ssh.dao.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import com.weicoder.ssh.dao.datasource.impl.DBCP2;
import com.weicoder.ssh.dao.datasource.impl.Druid; 

/**
 * 封装 DataSource类 主要给Spring注入用
 * 
 * @author  WD
 * @version 1.0
 */
public final class BasicDataSource implements DataSource {
	// 数据源
	private DataSource ds;

	/**
	 * 设置数据源类型
	 * 
	 * @param parse 使用数据源类型
	 */
	public void setParse(String parse) {
		// 判断数据源
		switch (parse) { 
			case "dbcp2":
				ds = new DBCP2();
				break; 
			case "druid":
				ds = new Druid();
				break;
		}
	}

	/**
	 * 获得驱动类
	 */
	public String getDriver() {
		return ds.getDriver();
	}

	/**
	 * 获得多长时间检查一次空闲连接
	 */
	public long getIdleTimeout() {
		return ds.getIdleTimeout();
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
		return ds.getMaxSize();
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
		return ds.getTimeout();
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
		ds.setDriver(driver);
	}

	/**
	 * 设置多长时间检查一次空闲连接
	 */
	public void setIdleTimeout(long idleTimeout) {
		ds.setIdleTimeout(idleTimeout);
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
		ds.setMaxIdleTime(maxIdleTime);
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
		ds.setMaxSize(maxSize);
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
		ds.setTimeout(timeout);
	}

	/**
	 * 设置用户
	 */
	public void setUser(String user) {
		ds.setUser(user);
	}

	/**
	 * 获得密码
	 * 
	 * @return 密码
	 */
	public String getPassword() {
		return ds.getPassword();
	}

	/**
	 * 获得Url
	 * 
	 * @return Url
	 */
	public String getUrl() {
		return ds.getUrl();
	}

	/**
	 * 设置密码
	 * 
	 * @param password 密码
	 */
	public void setPassword(String password) {
		ds.setPassword(password);
	}

	/**
	 * 设置 Url
	 * 
	 * @param url Url
	 */
	public void setUrl(String url) {
		ds.setUrl(url);
	}

	/**
	 * 获得连接
	 * 
	 * @return 数据库连接
	 */
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	/**
	 * 获得连接
	 * 
	 * @param  username 用户名
	 * @param  password 密码
	 * @return          数据库连接
	 */
	public Connection getConnection(String username, String password) throws SQLException {
		return ds.getConnection(username, password);
	}

	/**
	 * 获得打印日志类
	 */
	public PrintWriter getLogWriter() throws SQLException {
		return ds.getLogWriter();
	}

	/**
	 * 获得登录超时时间
	 */
	public int getLoginTimeout() throws SQLException {
		return ds.getLoginTimeout();
	}

	/**
	 * 设置打印日志类
	 */
	public void setLogWriter(PrintWriter out) throws SQLException {
		ds.setLogWriter(out);
	}

	/**
	 * 设置登录超时时间
	 */
	public void setLoginTimeout(int seconds) throws SQLException {
		ds.setLoginTimeout(seconds);
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		try {
			ds.close();
		} catch (Exception e) {
		}
	}

	/**
	 * JDK1.6添加
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return ds.isWrapperFor(iface);
	}

	/**
	 * JDK1.6添加
	 */
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return ds.unwrap(iface);
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return ds.getParentLogger();
	}
}
