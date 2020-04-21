package com.weicoder.web.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.weicoder.common.C.S;

/**
 * 标注AOP注解
 * 
 * @author WD
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Aop {
	/**
	 * 拦截的aop类
	 * 
	 * @return 拦截的aop类
	 */
	String value() default S.EMPTY;

//	/**
//	 * 前置方法
//	 * @return 前置方法
//	 */
//	String before();
//
//	/**
//	 * 后置方法
//	 * @return 后置方法
//	 */
//	String after();
//
//	/**
//	 * 异常方法
//	 * @return 异常方法
//	 */
//	String exception() default "";
}
