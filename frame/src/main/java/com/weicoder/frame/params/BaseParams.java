package com.weicoder.frame.params;

import com.weicoder.common.params.Params;

/**
 * WdBase包所用参数读取类
 * @author WD 
 * @version 1.0 
 */
public final class BaseParams {
	/** 分页使用当前页的标识 */
	public final static String	PAGE_FLAG			= Params.getString("page.flag", "pager.currentPage");
	/** 分页大小 */
	public final static int		PAGE_SIZE			= Params.getInt("page.size", 20);
	/** 缓存是否有效 */
	public final static boolean	CACHE_VALID_POWER	= Params.getBoolean("cache.valid.power", true);
	/** 缓存类型 */
	public final static String	CACHE_TYPE			= Params.getString("cache.type", "map");

	/**
	 * 获得是否使用缓存<br/>
	 * 需在配置文件中配置,如果不配置或配置不对将优先使用CACHE_VALID<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: cache.valid.xxx = ? <br/>
	 * XML: {@literal <cache><valid><xxx>?</xxx></valid></cache>}</h2>
	 * @return 是否使用缓存
	 */
	public static boolean getCache(String name) {
		return Params.getBoolean(Params.getKey("cache", "valid", name), CACHE_VALID_POWER);
	}

	private BaseParams() {}
}
