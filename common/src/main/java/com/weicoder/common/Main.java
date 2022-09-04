package com.weicoder.common;

import com.weicoder.common.constants.C;
import com.weicoder.common.init.Inits;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.U.D;
import com.weicoder.common.util.U.T;

/**
 * 通用主入口
 * 
 * @author wudi
 */
public class Main {

	public static void main(String[] args) {
		// 执行初始化任务
		Logs.debug("{} start user.dir={} base.dir={} time={}", C.O.PROJECT_NAME, C.O.USER_DIR, C.O.BASE_DIR, D.dura());
		Inits.init();
		Logs.info("{} end user.dir={} base.dir={} time={}", C.O.PROJECT_NAME, C.O.USER_DIR, C.O.BASE_DIR, D.dura());
		// 是否驻留线程 为了保护驻留程序 因为本包线程是守护线程 主线程结束就会结束
		while (CommonParams.MAIN)
			// 因为驻留程序 大时间延迟
			T.sleep(C.D.WEEK);
//		// 是否驻留线程
//		boolean main = CommonParams.MAIN;
//		// 等待时间
//		int wait = 0;
//		if (E.isNotEmpty(args))
//			wait = W.C.toInt(args[0]);
//		if (wait == 0)
//			wait = C.D.DAY;
//		// 为了保留驻留程序 因为本包线程是守护线程 主线程结束就会结束
//		while (main) {
//			// 因为驻留程序 大时间延迟
//			T.sleep(wait);
//		}
	}
}
