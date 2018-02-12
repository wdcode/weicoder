package com.weicoder.core.quartz;

import java.lang.reflect.Method;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

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
		BeanUtil.invoke(context.getJobDetail().getJobDataMap().get("obj"),
				(Method) context.getJobDetail().getJobDataMap().get("method"));
	}
}
