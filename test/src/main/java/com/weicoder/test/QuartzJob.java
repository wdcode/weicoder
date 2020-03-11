package com.weicoder.test;

import com.weicoder.common.log.Logs;
import com.weicoder.quartz.annotation.Job;
import com.weicoder.quartz.annotation.Trigger;

@Job
public class QuartzJob {
	/**
	 * 主播在麦检测
	 */
	@Trigger("0/1 * * * * ?")
	public void test() {
		Logs.info("test");
	}
}
