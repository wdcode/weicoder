package com.weicoder.common.init;

import com.weicoder.common.U.C;
import com.weicoder.common.U.D;
import com.weicoder.common.U.ES;
import com.weicoder.common.U.S;
import com.weicoder.common.asyn.Asyn;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;

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
		C.from(Init.class).forEach(c -> {
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
