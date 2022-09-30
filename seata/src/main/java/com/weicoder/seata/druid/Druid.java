package com.weicoder.seata.druid;

import javax.sql.DataSource;

import static com.weicoder.datasource.params.DataSourceParams.*;

import com.alibaba.druid.pool.DruidDataSource; 
import com.weicoder.datasource.base.BaseDataSource;

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
		ds.setDriverClassName((getDriver(name)));
		ds.setUrl(getUrl(name));
		ds.setUsername(getUser(name));
		ds.setPassword(getPassword(name));
		ds.setMaxActive(getMaxPoolSize(name));
		ds.setMinIdle(getMinPoolSize(name));
		ds.setValidationQueryTimeout(300000);
		ds.setTimeBetweenEvictionRunsMillis(60000);
		ds.setInitialSize(getInitialPoolSize(name));
		ds.setMaxWait(getMaxIdleTime(name));
		ds.setValidationQuery("SELECT 1");
		return ds;
	}
}
