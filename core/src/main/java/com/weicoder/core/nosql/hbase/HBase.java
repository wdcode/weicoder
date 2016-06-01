package com.weicoder.core.nosql.hbase;

import com.weicoder.core.nosql.NoSQL;

/**
 * HBase接口
 * @author WD 
 *  
 */
public interface HBase extends NoSQL {
	/**
	 * 获得Dao
	 * @param tableName 表名
	 * @return HBaseDao
	 */
	HBaseDao getDao(String tableName);

	/**
	 * 创建表
	 * @param tableName 表名
	 * @return HBaseDao
	 */
	HBaseDao createTable(String tableName, String... cfs);

	/**
	 * 删除表
	 * @param tableName 表名
	 */
	void deleteTable(String tableName);
}
