package com.weicoder.ssh.quartz;

import java.util.Map;

/**
 * Spring继承任务
 * @author WD 
 * @version 1.0  
 */
public interface Job {
	/**
	 * 获得任务执行时间
	 * @return key 执行方法 value 执行时间
	 */
	Map<String, String> getTriggers();
}
