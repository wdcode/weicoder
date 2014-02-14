package com.weicoder.base.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 使用实体缓存
 * @author WD
 * @since JDK7
 * @version 1.0 2014-1-4
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Cache {}
