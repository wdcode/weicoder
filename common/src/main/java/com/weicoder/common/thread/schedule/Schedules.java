package com.weicoder.common.thread.schedule;

import com.weicoder.common.log.Logs;
import com.weicoder.common.params.P;
import com.weicoder.common.thread.ScheduledUtil;
import com.weicoder.common.util.U;

/**
 * 定时任务执行类初始化
 * 
 * @author WD
 */
public final class Schedules {
	/**
	 * 初始化
	 */
	public static void init() {
		// 循环处理任务类
		if (P.C.SCHEDULE)
			U.C.list(Schedule.class).forEach(c -> {
				// 处理所有方法
				U.C.getPublicMethod(c).forEach(m -> {
					// 处理delay
					Delay delay = m.getDeclaredAnnotation(Delay.class);
					if (delay == null) {
						// 处理Rate
						Rate rate = m.getDeclaredAnnotation(Rate.class);
						if (rate != null) {
							// 添加定时任务
							ScheduledUtil.rate(() -> U.B.invoke(U.C.newInstance(c), m), rate.start(), rate.value(), rate.unit());
							Logs.info("add schedule name={},rate={}", m.getName(), rate);
						}
					} else {
						ScheduledUtil.delay(() -> U.B.invoke(U.C.newInstance(c), m), delay.start(), delay.value(), delay.unit());
						Logs.info("add schedule name={},delay={}", m.getName(), delay);
					}
				});
			});
	}

	private Schedules() {
	}
}
