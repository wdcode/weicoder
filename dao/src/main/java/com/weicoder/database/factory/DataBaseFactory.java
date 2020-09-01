package com.weicoder.database.factory;

import com.weicoder.database.DataBase;
import com.weicoder.database.impl.DataBaseImpl;
import com.weicoder.datasource.factory.DataSourceFactory;

import java.io.File; 
import java.util.Map; 

import javax.sql.DataSource;
 
import com.weicoder.common.U; 
import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.ResourceUtil;

/**
 * 生成DataBase的工厂类
 * 
 * @author WD
 */
public final class DataBaseFactory extends FactoryKey<DataSource, DataBase> {
	// DataBase工厂
	private final static DataBaseFactory FACTORY = new DataBaseFactory();

	/**
	 * 获得DataBase
	 * 
	 * @return DataBase
	 */
	public static Map<String, DataBase> getDataBase() {
		// 声明数据操作集合
		Map<String, DataBase> map = Maps.newMap();
		// 获得配置文件目录下所有配置
		File file = ResourceUtil.newFile("db/");
		// 读取配置
		for (String name : file.list()) {
			String n = U.S.split(name, "\\.")[0];
			map.put(n, FACTORY.getInstance(n));
		}
		// 返回对象
		return map;
	} 

	/**
	 * 获得DataBase
	 * 
	 * @param name 数据源名称
	 * @return DataBase
	 */
	public static DataBase getDataBase(String name) {
		return FACTORY.getInstance(name);
	}

	/**
	 * 获得DataBase
	 * 
	 * @param dataSource 数据源
	 * @return DataBase
	 */
	public static DataBase getDataBase(DataSource dataSource) {
		return FACTORY.getInstance(dataSource);
	}

	/**
	 * 实例化一个新的DataBase,根据配置文件生成DataBase.
	 * <h2>注: 这个方法将调用在配置文件内的配置生成对象,如果没配置或配置错误, 那将返回一个没有dataSource的DataBase ,如果要使用需要自行setDataSource 注:
	 * 这个方法每次都生成新的对象,并且只是当次运行的, 在方法调用后无法在得到这个DataBase的引用,包括调用getInstance(xxx)方法</h2>
	 * 
	 * @return DataBase 数据库操作对象
	 */
	public DataBase newInstance() {
		return newInstance(DataSourceFactory.getDataSource());
	}

	/**
	 * 使用DataSource实例一个新的DataBase
	 * <h2>注: 这个方法每次都生成新的对象,并且只是当次运行的, 在方法调用后无法在得到这个DataBase的引用,包括调用getInstance(xxx)方法</h2>
	 * 
	 * @param dataSource 数据源
	 * @return DataBase 数据库操作对象
	 */
	public DataBase newInstance(DataSource dataSource) {
		return new DataBaseImpl(dataSource);
	}

	/**
	 * 使用DataSource实例一个新的DataBase
	 * 
	 * @param name 数据源名称
	 * @return DataBase 数据库操作对象
	 */
	public DataBase getInstance(String name) {
		return getInstance(DataSourceFactory.getDataSource(name));
	}

	private DataBaseFactory() {
	}
}
