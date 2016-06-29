package com.weicoder.common.schedule;

import java.lang.reflect.Method;

import com.weicoder.common.init.Init;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;

/**
 * 定时任务执行类初始化
 * @author WD
 */
public final class ScheduleInit implements Init {

	@Override
	public void init() {
		// 循环处理任务类
		for (Class<?> c : ClassUtil.getAnnotationClass(Schedule.class)) {
			// 处理所有方法
			for (Method m : c.getDeclaredMethods()) {
				// 处理delay 
				Delay delay = m.getDeclaredAnnotation(Delay.class);// c.getDeclaredAnnotation(Delay.class);
				if (delay == null) {
					// 处理Rate
					Rate rate = m.getDeclaredAnnotation(Rate.class);
					if (rate != null) {
						// 添加定时任务
						ScheduledUtile.rate(() -> BeanUtil.invoke(BeanUtil.newInstance(c), m), rate.start(), rate.value(), rate.unit());
						Logs.info("add schedule name={},rate={}", m.getName(), rate);
					}
				} else {
					ScheduledUtile.delay(() -> BeanUtil.invoke(BeanUtil.newInstance(c), m), delay.start(), delay.value(), delay.unit());
					Logs.info("add schedule name={},delay={}", m.getName(), delay);
				}
			}
		}
	}
}
