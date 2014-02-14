package com.weicoder.base.quartz;

import java.util.Map;

/**
 * Spring继承任务
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-15
 */
public interface Job {
	/**
	 * 获得任务执行时间
	 * @return key 执行方法 value 执行时间
	 */
	Map<String, String> getTriggers();
}
