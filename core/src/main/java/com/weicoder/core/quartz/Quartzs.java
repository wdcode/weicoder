package com.weicoder.core.quartz;

import java.lang.reflect.Method;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.params.Params;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;

/**
 * Quartz任务初始化类
 * @author WD
 */
public final class Quartzs {
	/**
	 * 初始化
	 */
	public final static void init() {
		try {
			List<Class<Job>> jobs = ClassUtil.getAnnotationClass(CommonParams.getPackages("quartz"), Job.class);
			if (!EmptyUtil.isEmpty(jobs)) {
				// 任务执行器
				Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
				// 循环处理任务类
				for (Class<Job> c : jobs) {
					// 执行对象
					Object obj = BeanUtil.newInstance(c);
					// Trigger生成器
					TriggerBuilder<org.quartz.Trigger> builder = TriggerBuilder.newTrigger();
					// 处理所有方法
					for (Method m : c.getMethods()) {
						// 方法有执行时间注解
						Trigger t = m.getAnnotation(Trigger.class);
						if (t != null) {
							// 获得任务
							JobDetail job = JobBuilder.newJob(Jobs.class).build();
							job.getJobDataMap().put("method", m);
							job.getJobDataMap().put("obj", obj);
							// 设置任务执行类
							scheduler.scheduleJob(job,
									builder.withIdentity(m.getName(), obj.getClass().getSimpleName())
											.withSchedule(CronScheduleBuilder.cronSchedule(
													Params.getString("job.trigger." + m.getName(), t.value())))
											.build());
							Logs.info("add quartz job={}", job);
						}
					}
				}
				// 执行任务
				scheduler.start();
			}
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	private Quartzs() {}
}
