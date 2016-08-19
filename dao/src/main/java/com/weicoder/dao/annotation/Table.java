package com.weicoder.dao.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 对应数据库表
 * @author WD
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {}
