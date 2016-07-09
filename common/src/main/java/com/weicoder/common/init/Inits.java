package com.weicoder.common.init;

import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;

/**
 * 执行初始化任务
 * @author WD
 */
public final class Inits {
	/**
	 * 初始化任务
	 */
	public static void init() {
		for (String i : CommonParams.INIT_CLASSES) {
			((Init) BeanUtil.newInstance(i)).init();
		}
	}

	private Inits() {}
}
