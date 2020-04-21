package com.weicoder.nosql.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redis订阅通道
 * @author WD
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Channel {
	/**
	 * 订阅的channel
	 * @return 字符串
	 */
	String value();
}
