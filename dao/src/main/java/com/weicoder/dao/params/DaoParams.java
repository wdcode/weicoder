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
	/** 数据源配置 */
	public final static String	DB_CONFIG	= CONFIG.getString("db.config", "db");
	/** PO 扫描包名 */
	public final static String	PACKAGES	= CONFIG.getString("packages");
	/** 队列更新时间 默认60秒 */
	public final static int		QUEUE_TIME	= CONFIG.getInt("queue.time", 2);
	/** 队列更新步长 默认500 */
	public final static int		QUEUE_SETP	= CONFIG.getInt("queue.setp", 200);
	/** dao分步更新间隔时间 */
	public final static long	SETP_SLEEP	= CONFIG.getLong("setp.sleep", 100);

	private DaoParams() {}
}
