package com.weicoder.datasource.base;

import javax.sql.DataSource;

/**
 * 基础数据源实现
 * 
 * @author WD
 */
public abstract class BaseDataSource {
	protected String		name;
	protected DataSource	ds;
 
	/**
	 * 构造函数
	 * 
	 * @param name 数据源名
	 */
	public BaseDataSource(String name) {
		this.name = name;
		this.ds = init(name);
	}

	/**
	 * 获得数据源
	 * 
	 * @return 数据源
	 */
	public DataSource getDataSource() {
		return ds;
	}

	/**
	 * 初始化DataSource
	 * 
	 * @param name
	 * @return
	 */
	public abstract DataSource init(String name);
}
