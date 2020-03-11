package com.weicoder.test;

import com.weicoder.common.log.Logs;
import com.weicoder.quartz.Quartzs;

public class QuartzTest {

	public static void main(String[] args) {
		Logs.info("quartz init start.....");
		 Quartzs.init();
	} 
}
