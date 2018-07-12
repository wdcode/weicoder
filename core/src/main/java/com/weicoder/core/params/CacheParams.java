package com.weicoder.core.params;

import com.weicoder.common.params.Params;

/**
 * 读取缓存参赛
 * @author WD
 */
public final class CacheParams {
	/** 最大容量 */
	public static final long	MAX		= Params.getLong("cache.max", 10000);
	/** 初始容量 */
	public static final int		INIT	= Params.getInt("cache.init", 1000);
	/** 并发级别 */
	public static final int		LEVEL	= Params.getInt("cache.level", 8);
	/** 刷新时间 默认1小时 */
	public static final long	REFRESH	= Params.getLong("cache.refresh", 3600);
	/** 过期时间 默认10分钟 */
	public static final long	EXPIRE	= Params.getLong("cache.expire", 600);

	private CacheParams() {}
}
