package com.weicoder.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务注解
 * 
 * @author wudi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) 
@Inherited
public @interface Service {
}
