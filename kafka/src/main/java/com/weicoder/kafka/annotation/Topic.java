package com.weicoder.kafka.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * kafka订阅的topic 放在方法上 读取到相应topic会调用该方法
 * @author WD
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Topic {
	/**
	 * 订阅的topic
	 * @return 字符串
	 */
	String value();
}
