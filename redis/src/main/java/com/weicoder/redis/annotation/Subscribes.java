package com.weicoder.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.weicoder.common.constants.StringConstants;

/**
 * redis订阅
 * @author WD
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribes {
	/**
	 * redis读取配置key 默认""
	 * @return 字符串
	 */
	String value() default StringConstants.EMPTY;
}
