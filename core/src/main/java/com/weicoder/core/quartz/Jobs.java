package com.weicoder.core.quartz;

import java.lang.reflect.Method;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import com.weicoder.common.log.Logs;
import com.weicoder.common.util.BeanUtil; 

/**
 * Quartz任务类
 * @author WD
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public final class Jobs implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 开始时间
		long time = System.currentTimeMillis();
		// 获得执行任务的对象 和方法
		JobDataMap map = context.getJobDetail().getJobDataMap();
		Object obj = map.get("obj");
		Method method = (Method) map.get("method");
		// 执行任务
		BeanUtil.invoke(obj, method);
		Logs.debug("job obj={} method={} end time={}", obj.getClass().getSimpleName(), method.getName(),
				System.currentTimeMillis() - time);
	}
}
