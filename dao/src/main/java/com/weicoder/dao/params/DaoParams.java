package com.weicoder.dao.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.params.P;

/**
 * Dao参数获取
 * 
 * @author WD
 */
public final class DaoParams {
	private DaoParams() {
	}

	// 前缀
	private final static String	PREFIX		= "dao";
	// Properties配置
	private final static Config	CONFIG		= P.getConfig(PREFIX);
	/** Dao是否使用JDBC实现 默认false 使用hibernate true=JDBC实现 测试阶段 */
	public final static boolean	JDBC		= CONFIG.getBoolean("jdbc", P.getBoolean(PREFIX + ".jdbc", false));
//	/** 分页使用当前页的标识 */
//	public final static String	PAGE_FLAG	= CONFIG.getString("page.flag", P.getString(PREFIX + ".page.flag", "pager.currentPage"));
//	/** 数据源配置 */
//	public final static String	DB_CONFIG	= CONFIG.getString("db.config", P.getString(PREFIX + ".db.config", "db"));
	/** PO 扫描包名 */
	public final static String	PACKAGES	= CONFIG.getString("packages", P.getString(PREFIX + ".packages"));
	/** 队列更新时间 默认2秒 */
	public final static int		QUEUE_TIME	= CONFIG.getInt("queue.time", P.getInt(PREFIX + ".queue.time", 2));
	/** 队列更新步长 默认200 */
	public final static int		QUEUE_SETP	= CONFIG.getInt("queue.setp", P.getInt(PREFIX + ".queue.setp", 200));
	/** dao分步更新间隔时间 默认100 */
	public final static long	SETP_SLEEP	= CONFIG.getLong("setp.sleep", P.getInt(PREFIX + ".setp.sleep", 100));
}
