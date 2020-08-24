package com.weicoder.seata.druid;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.weicoder.datasource.base.BaseDataSource;
import com.weicoder.datasource.params.DataSourceParams;

/**
 * alibaba Druid 连接池
 * 
 * @author wdcode
 *
 */
public class Druid extends BaseDataSource {

	public Druid(String name) {
		super(name); 
	}

	@Override
	public DataSource init(String name) {
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName((DataSourceParams.getDriver(name)));
		ds.setUrl(DataSourceParams.getUrl(name));
		ds.setUsername(DataSourceParams.getUser(name));
		ds.setPassword(DataSourceParams.getPassword(name));
		ds.setMaxActive(DataSourceParams.getMaxPoolSize(name));
		ds.setMinIdle(DataSourceParams.getMinPoolSize(name));
		ds.setValidationQueryTimeout(300000);
		ds.setTimeBetweenEvictionRunsMillis(60000);
		ds.setInitialSize(DataSourceParams.getInitialPoolSize(name));
		ds.setMaxWait(DataSourceParams.getMaxIdleTime(name));
		ds.setValidationQuery("SELECT 1");
		return ds;
	}
}
