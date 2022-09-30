package com.weicoder.frame.params;

import com.weicoder.common.params.P;

/**
 * quartz 任务参数
 * @author WD
 */
public final class QuartzParams {
	/** 是否启用spring job */
	public final static boolean SPRING = P.getBoolean("quartz.spring", false);

	private QuartzParams() {}
}
