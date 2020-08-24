package com.weicoder.seata.proxy;

import javax.sql.DataSource;

import com.weicoder.datasource.base.BaseDataSource;
import com.weicoder.seata.druid.Druid;

import io.seata.rm.datasource.DataSourceProxy;

/**
 * Seata分布式连接数代理
 * 
 * @author wdcode
 *
 */
public class SeataProxy extends BaseDataSource {

	public SeataProxy(String name) {
		super(name);
	}

	@Override
	public DataSource init(String name) {
		return new DataSourceProxy(new Druid(name).getDataSource());
	}
}
