package com.weicoder.dao.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;

/**
 * Dao参数获取
 * @author WD
 */
public final class DaoParams {
	// Properties配置
	private final static Config	CONFIG		= ConfigFactory.getConfig("dao");
	/** Dao是否使用JDBC实现 默认false 使用hibernate true=JDBC实现 测试阶段 */
	public final static boolean	JDBC		= CONFIG.getBoolean("jdbc", false);
	/** 分页使用当前页的标识 */
	public final static String	PAGE_FLAG	= CONFIG.getString("page.flag", "pager.currentPage");
	/** 分页大小 */
	public final static int		PAGE_SIZE	= CONFIG.getInt("page.size", 20);
	/** 数据源配置 */
	public final static String	DB_CONFIG	= CONFIG.getString("db.config", "db");
	/** PO 扫描包名 */
	public final static String	PACKAGES	= CONFIG.getString("packages");

	private DaoParams() {}
}
