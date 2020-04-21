package com.weicoder.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.weicoder.common.C.S;

/**
 * 缓存注解
 * 
 * @author wudi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
	/** 缓存名称 默认空为注解类的名称 */
	String name() default S.EMPTY;

	/** 缓存的主键字段名 */
	String key();
}
