package com.weicoder.common.init;

import com.weicoder.common.annotation.Asyn;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.U.C;
import com.weicoder.common.util.U.D;
import com.weicoder.common.util.U.ES;
import com.weicoder.common.util.U.S;

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
		Logs.debug("start init...");
		C.list(Init.class).forEach(c -> {
			// 获得接口类名称
			String name = S.convert(c.getSimpleName(), "Init");
			// 初始化开始
			D.dura();
			// 调用初始化方法
			if (CommonParams.power(name))
				if (c.isAnnotationPresent(Asyn.class))
					ES.execute(() -> C.newInstance(c).init());
				else
					C.newInstance(c).init();
			// 初始化结束
			Logs.info("init {} end time={}", name, D.dura());
		});
	}

	private Inits() {
	}
}
