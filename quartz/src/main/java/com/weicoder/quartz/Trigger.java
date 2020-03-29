package com.weicoder.quartz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 任务执行时间设定
 * @author WD
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Trigger {
	/**
	 * 时间表达式 0 * * * * ? （秒 分 时 日 月 年）
	 * @return 返回表达式
	 */
	String value() default "0/1 * * * * ?";
}
