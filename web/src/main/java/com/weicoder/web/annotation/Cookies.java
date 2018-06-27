package com.weicoder.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 把返回结果按字段写入cookie
 * @author WD
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Cookies {
	/**
	 * 指定写入cookie的属性 默认为空 写全部
	 * @return 指定写入的属性
	 */
	String[] names() default {};

	/**
	 * 过期时间
	 * @return 过期时间
	 */
	int maxAge() default -1;
}