package com.weicoder.nosql.kafka.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * kafka生产者标记
 * @author WD
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Consumer {
	/**
	 * kafka读取配置key 默认""
	 * @return 字符串
	 */
	String value() default "";
}
