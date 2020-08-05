package com.weicoder.ssh.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 使用实体缓存
 * @author WD 
 *  
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Cache {}
