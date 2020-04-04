package com.weicoder.datasource.base;

import javax.sql.DataSource;

/**
 * 基础数据源实现
 * @author WD  
 */
public abstract class BaseDataSource {

	/**
	 * 构造函数
	 * @param name 数据源名
	 */
	public BaseDataSource(String name) {}

	/**
	 * 获得数据源
	 * @return 数据源
	 */
	public abstract DataSource getDataSource();
}
