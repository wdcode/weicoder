package com.weicoder.common.thread.schedule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 执行rate方法 按初始时间间隔
 * @author WD
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Rate {
	/**
	 * 启动时间 默认0 立即开始
	 * @return 启动时间
	 */
	long start() default 0;

	/**
	 * 间隔时间 默认1000
	 * @return 间隔时间
	 */
	long value() default 1000;

	/**
	 * 时间戳 默认毫秒
	 * @return 时间戳
	 */
	TimeUnit unit() default TimeUnit.MILLISECONDS;
}
