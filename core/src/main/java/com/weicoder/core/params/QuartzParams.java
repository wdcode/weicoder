package com.weicoder.core.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.params.Params;

/**
 * Quartz任务读取参数
 * @author WD
 */
public final class QuartzParams {
	// 前缀
	private final static String		PREFIX	= "quartz";
	/** 任务开关 */
	public final static boolean		POWER	= Params.getBoolean("quartz.power", false);
	/** 执行任务名称数组 */
	public final static String[]	NAMES	= Params.getStringArray("quartz.names", ArrayConstants.STRING_EMPTY);

	/**
	 * 获得Quartz执行任务类
	 * @param name 名称
	 * @return 任务类
	 */
	public static String getClass(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "class"));
	}

	/**
	 * 获得Quartz执行任务类执行时间
	 * @param name 名称
	 * @return 执行时间
	 */
	public static String[] getTrigger(String name) {
		return Params.getStringArray(Params.getKey(PREFIX, name, "trigger"), ArrayConstants.STRING_EMPTY);
	}

	private QuartzParams() {}
}
