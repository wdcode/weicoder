package com.weicoder.common.schedule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 执行delay方法 按执行线程间隔
 * @author WD
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Delay {
	/**
	 * 启动时间 默认0 立即开始
	 * @return
	 */
	long start() default 0;

	/**
	 * 间隔时间 默认1000
	 * @return
	 */
	long value() default 1000;

	/**
	 * 时间戳 默认毫秒
	 * @return
	 */
	TimeUnit unit() default TimeUnit.MILLISECONDS;
}
