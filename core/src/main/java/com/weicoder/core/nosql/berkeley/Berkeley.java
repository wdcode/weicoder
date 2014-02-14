package com.weicoder.core.nosql.berkeley;

import java.util.List;

import com.weicoder.core.nosql.NoSQL;

/**
 * BerkeleyDB Dao接口
 * @author WD
 * @since JDK7
 * @version 1.0 2010-11-21
 */
public interface Berkeley extends NoSQL {

	/**
	 * 查询所有数据
	 * @return 数据列表
	 */
	List<Object> query();
}
