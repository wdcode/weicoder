package com.weicoder.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.weicoder.common.constants.StringConstants;

/**
 * rpc客户端 默认注解的类名称映射为方法名
 * 
 * @author wudi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Rpc {
	/** rpc服务名称 */
	String value() default StringConstants.EMPTY;
}