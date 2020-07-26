package com.weicoder.frame.params;

import com.weicoder.common.params.Params;

/**
 * quartz 任务参数
 * @author WD
 */
public final class QuartzParams {
	/** 是否启用spring job */
	public final static boolean SPRING = Params.getBoolean("quartz.spring", false);

	private QuartzParams() {}
}
