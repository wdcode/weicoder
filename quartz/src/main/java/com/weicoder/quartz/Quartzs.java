package com.weicoder.quartz;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory; 
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.U;
import com.weicoder.quartz.annotation.Job;
import com.weicoder.quartz.annotation.Trigger;
import com.weicoder.quartz.params.QuartzParams;

/**
 * Quartz任务初始化类
 * 
 * @author WD
 */
public final class Quartzs {
	private final static Log LOG = LogFactory.getLog(Quartzs.class);

	/**
	 * 初始化
	 */
	public final static void init() {
		try {
			List<Class<Job>> jobs = ClassUtil.getAnnotationClass(CommonParams.getPackages("quartz"), Job.class);
			if (U.E.isNotEmpty(jobs)) {
				// 任务执行器
				Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
				// 循环处理任务类
				jobs.forEach(c -> {
					// 执行对象
					Object obj = ClassUtil.newInstance(c);
					// Trigger生成器
					TriggerBuilder<org.quartz.Trigger> builder = TriggerBuilder.newTrigger();
					// 处理所有方法
					ClassUtil.getPublicMethod(c).forEach(m -> {
						// 方法有执行时间注解
						Trigger t = m.getAnnotation(Trigger.class);
						if (t != null) {
							// 获得任务
							JobDetail job = JobBuilder.newJob(Jobs.class).build();
							// 设置对应方法和对象
							JobDataMap map = job.getJobDataMap();
							map.put("method", m);
							map.put("obj", obj);
							// 设置任务执行类
							try {
								scheduler.scheduleJob(job,
										builder.withIdentity(m.getName(), obj.getClass().getSimpleName())
												.withSchedule(CronScheduleBuilder
														.cronSchedule(QuartzParams.getTrigger(m.getName(), t.value())))
												.build());
							} catch (SchedulerException e) {
								LOG.error(e);
							}
							LOG.info("add quartz job={}", job);
						}
					});
				});
				// 执行任务
				scheduler.start();
			}
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	private Quartzs() {
	}
}
