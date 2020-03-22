package com.weicoder.common.init;

import com.weicoder.common.U.C;
import com.weicoder.common.U.ES;
import com.weicoder.common.U.S;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.DateUtil;

/**
 * 初始化工具
 * 
 * @author wudi
 */
public final class Inits {
	/**
	 * 初始化
	 */
	public static void init() {
		// 获得所有初始化接口
		C.getAssignedClass(Init.class).forEach(c -> {
			ES.execute(() -> {
				// 获得接口类名称
				String name = S.convert(S.replace(c.getSimpleName(), "Init", ""));
				// 初始化开始
				long curr = System.currentTimeMillis();
				Logs.info("init {} start...", name);
				// 调用初始化方法
				if (CommonParams.power(name))
					C.newInstance(c).init();
				// 初始化结束
				Logs.info("init {} end time={}", name, DateUtil.diff(curr));
			});
		});
	}

	private Inits() {
	}
}
