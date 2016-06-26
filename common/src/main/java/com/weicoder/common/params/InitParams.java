package com.weicoder.common.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.params.Params;

/**
 * 初始化参数
 * @author WD
 */
public final class InitParams {
	/** 执行任务名称数组 */
	public final static String[] CLASSES = Params.getStringArray("init.class", ArrayConstants.STRING_EMPTY);

	private InitParams() {
	}
}
