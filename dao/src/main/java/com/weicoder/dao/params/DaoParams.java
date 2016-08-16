package com.weicoder.dao.params;

import com.weicoder.common.params.Params;

/**
 * Dao参数获取
 * @author WD
 */
public final class DaoParams {
	/** Dao是否使用JDBC实现 默认false 使用hibernate true=JDBC实现 测试阶段 */
	public final static boolean JDBC = Params.getBoolean("dao.jdbc", false);

	private DaoParams() {}
}
