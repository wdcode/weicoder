package com.weicoder.core.dao.datasource;

import com.weicoder.common.interfaces.Close;

/**
 * 自定义数据源接口
 * @author WD
 * @since JDK7
 * @version 1.0 2010-01-22
 */
public interface DataSource extends javax.sql.DataSource, Close {
	/**
	 * 获得 驱动类
	 * @return 驱动类
	 */
	String getDriver();

	/**
	 * 设置驱动类
	 * @param driver 驱动类
	 */
	void setDriver(String driver);

	/**
	 * 获得Url
	 * @return Url
	 */
	String getUrl();

	/**
	 * 设置 Url
	 * @param url Url
	 */
	void setUrl(String url);

	/**
	 * 获得用户名
	 * @return 用户名
	 */
	String getUser();

	/**
	 * 设置用户名
	 * @param user 用户名
	 */
	void setUser(String user);

	/**
	 * 获得密码
	 * @return 密码
	 */
	String getPassword();

	/**
	 * 设置密码
	 * @param password 密码
	 */
	void setPassword(String password);

	/**
	 * 获得初始化连接数
	 * @return 初始化连接数
	 */
	int getInitialPoolSize();

	/**
	 * 设置初始化连接数
	 * @param initialPoolSize 初始化连接数
	 */
	void setInitialPoolSize(int initialPoolSize);

	/**
	 * 获得连接池最大连接数
	 * @return 连接池最大连接数
	 */
	int getMaxPoolSize();

	/**
	 * 设置连接池最大连接数
	 * @param maxPoolSize 连接池最大连接数
	 */
	void setMaxPoolSize(int maxPoolSize);

	/**
	 * 连接池最小连接数
	 * @return 连接池最小连接数
	 */
	int getMinPoolSize();

	/**
	 * 设置连接池最小连接数
	 * @param minPoolSize 连接池最小连接数
	 */
	void setMinPoolSize(int minPoolSize);

	/**
	 * 获得最大连接数
	 * @return 最大连接数
	 */
	int getMaxSize();

	/**
	 * 设置最大连接数
	 * @param maxSize 最大连接数
	 */
	void setMaxSize(int maxSize);

	/**
	 * 获得超时等待时间
	 * @return 超时等待时间
	 */
	long getTimeout();

	/**
	 * 设置超时等待时间
	 * @param timeout 超时等待时间
	 */
	void setTimeout(long timeout);

	/**
	 * 获得测试空闲连接时间 超出时间回收
	 * @return 测试空闲连接时间 超出时间回收
	 */
	long getMaxIdleTime();

	/**
	 * 设置测试空闲连接时间 超出时间回收
	 * @param maxIdleTime 测试空闲连接时间 超出时间回收
	 */
	void setMaxIdleTime(long maxIdleTime);

	/**
	 * 获得多长时间检查一次空闲连接
	 * @return 多长时间检查一次空闲连接
	 */
	long getIdleTimeout();

	/**
	 * 设置多长时间检查一次空闲连接
	 * @param idleTimeout 多长时间检查一次空闲连接
	 */
	void setIdleTimeout(long idleTimeout);
}
