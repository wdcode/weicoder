package com.weicoder.frame.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 使用实体缓存
 * @author WD 
 * @version 1.0 
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Cache {}
