package com.weicoder.core.quartz;

import java.lang.reflect.Method;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.weicoder.common.init.Init;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.ClassUtil;

/**
 * Quartz任务初始化类
 * @author WD
 */
public final class Quartz implements Init {
	@Override
	public void init() {
		try {
			// 任务执行器
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			// 循环处理任务类
			for (Class<?> c : ClassUtil.getAnnotationClass(Job.class)) {
				// Trigger生成器
				TriggerBuilder<org.quartz.Trigger> builder = TriggerBuilder.newTrigger();
				// 处理所有方法
				for (Method m : c.getMethods()) {
					// 方法有执行时间注解
					Trigger t = m.getAnnotation(Trigger.class);
					if (t != null) {
						// 获得任务
						JobDetail job = JobBuilder.newJob(Jobs.class).build();
						// 设置任务执行类
						scheduler.scheduleJob(job, builder.withSchedule(CronScheduleBuilder.cronSchedule(t.value())).build());
						// job.

					}
				}
			}
			// 执行任务
			scheduler.start();
		} catch (Exception e) {
			Logs.error(e);
		}
	}
}
