package com.weicoder.web.action;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Action 注解
 * @author WD
 * @since JDK7
 * @version 1.0 2015-10-23
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Action {}
