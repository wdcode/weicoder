package com.weicoder.dao.params;

import com.weicoder.common.params.Params;

/**
 * frame包所用参数读取类
 * @author WD
 */
public final class FrameParams {
	/** 分页使用当前页的标识 */
	public final static String	PAGE_FLAG	= Params.getString("page.flag", "pager.currentPage");
	/** 分页大小 */
	public final static int		PAGE_SIZE	= Params.getInt("page.size", 20);
	/** 数据源配置 */
	public final static String	DB_CONFIG	= Params.getString("db.config", "db/");

	private FrameParams() {
	}
}
