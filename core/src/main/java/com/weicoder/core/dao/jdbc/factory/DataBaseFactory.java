package com.weicoder.core.dao.jdbc.factory;

import com.weicoder.core.dao.jdbc.DataBase;
import com.weicoder.core.dao.jdbc.impl.DataBaseImpl;
import com.weicoder.core.dao.datasource.DataSource;
import com.weicoder.core.dao.datasource.factory.DataSourceFactory;
import com.weicoder.core.factory.FactoryKey;

/**
 * 生成DataBase的工厂类
 * @see com.weicoder.common.dao.jdbc.DataBase
 * @author WD
 * @since JDK7
 * @version 1.0 2009-08-25
 */
public final class DataBaseFactory extends FactoryKey<DataSource, DataBase> {
	// DataBase工厂
	private final static DataBaseFactory	FACTORY	= new DataBaseFactory();

	/**
	 * 获得DataBase 
	 * @return DataBase
	 */
	public static DataBase getDataBase() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得DataBase
	 * @param name 数据源名称
	 * @return DataBase
	 */
	public static DataBase getDataBase(String name) {
		return FACTORY.getInstance(name);
	}

	/**
	 * 获得DataBase
	 * @param dataSource 数据源
	 * @return DataBase
	 */
	public static DataBase getDataBase(DataSource dataSource) {
		return FACTORY.getInstance(dataSource);
	}

	/**
	 * 实例化一个新的DataBase,根据配置文件生成DataBase.<br/>
	 * <h2>注: 这个方法将调用在配置文件内的配置生成对象,如果没配置或配置错误,<br/>
	 * 那将返回一个没有dataSource的DataBase ,如果要使用需要自行setDataSource<br/>
	 * 注: 这个方法每次都生成新的对象,并且只是当次运行的,<br/>
	 * 在方法调用后无法在得到这个DataBase的引用,包括调用getInstance(xxx)方法</h2>
	 * @return DataBase 数据库操作对象
	 */
	public DataBase newInstance() {
		return newInstance(DataSourceFactory.getDataSource());
	}

	/**
	 * 使用DataSource实例一个新的DataBase <br/>
	 * <h2>注: 这个方法每次都生成新的对象,并且只是当次运行的,<br/>
	 * 在方法调用后无法在得到这个DataBase的引用,包括调用getInstance(xxx)方法</h2>
	 * @param dataSource 数据源
	 * @return DataBase 数据库操作对象
	 */
	public DataBase newInstance(DataSource dataSource) {
		return new DataBaseImpl(dataSource);
	}

	/**
	 * 使用DataSource实例一个新的DataBase <br/>
	 * @param name 数据源名称
	 * @return DataBase 数据库操作对象
	 */
	public DataBase getInstance(String name) {
		return getInstance(DataSourceFactory.getDataSource(name));
	}

	/**
	 * 私有构造函数,禁止外部实例化
	 */
	private DataBaseFactory() {}
}
