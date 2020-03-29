package com.weicoder.common;

import com.weicoder.common.U.D;
import com.weicoder.common.U.T;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.init.Inits;
import com.weicoder.common.log.Logs; 

/**
 * 通用主入口
 * 
 * @author wudi
 */
public class Main {

	public static void main(String[] args) {
		D.dura();
		Inits.init();
		Logs.info("{} start end time={}", SystemConstants.PROJECT_NAME, D.dura());
		// 为了保留驻留程序 因为本包线程是守护线程 主线程结束就会结束
		while (true) {
			// 因为驻留程序 大时间延迟
			T.sleep(DateConstants.DAY);
		}
	}
}
