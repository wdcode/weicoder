package com.weicoder.core.engine;

import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.ClearUtil;
import com.weicoder.core.params.QuartzParams;

/**
 * Quartz 处理引擎
 * @author WD
 * @since JDK7
 * @version 1.0 2011-05-25
 */
public final class QuartzEngine {
	// 保存任务列表
	private final static Map<JobDetail, Set<? extends Trigger>>	MAP_JOB;
	// 任务执行器
	private static Scheduler									scheduler;

	static {
		MAP_JOB = Maps.getConcurrentMap();
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
		} catch (Exception e) {}
	}

	/**
	 * 初始化任务
	 */
	public static void init() {
		// 判断任务开启
		if (QuartzParams.POWER) {
			// 循环数组
			for (String name : QuartzParams.NAMES) {
				try {
					add((Class<? extends Job>) ClassUtil.forName(QuartzParams.getClass(name)), QuartzParams.getTrigger(name));
				} catch (Exception e) {
					throw e;
				}
			}
			// 执行
			start();
		}
	}

	/**
	 * 添加任务
	 * @param jobClass 任务类
	 * @param trigger 任务执行时间 正则
	 */
	public static void add(Class<? extends Job> jobClass, String... trigger) {
		// 获得任务
		JobDetail job = JobBuilder.newJob(jobClass).build();
		// 声明任务执行时间列表
		Set<Trigger> triggers = Sets.getSet(trigger.length);
		// 循环获得任务执行时间对象
		for (int i = 0; i < trigger.length; i++) {
			triggers.add(TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(trigger[i])).build());
		}
		// 添加到任务列表中
		MAP_JOB.put(job, triggers);
	}

	/**
	 * 执行任务
	 */
	public static void start() {
		try {
			// 添加任务
			scheduler.scheduleJobs(MAP_JOB, true);
			// 执行任务
			scheduler.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭资源
	 */
	public static void close() {
		// 关闭执行器
		try {
			scheduler.clear();
			scheduler.shutdown();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			scheduler = null;
		}
		// 清除列表
		ClearUtil.clear(MAP_JOB);
	}

	private QuartzEngine() {}
}
