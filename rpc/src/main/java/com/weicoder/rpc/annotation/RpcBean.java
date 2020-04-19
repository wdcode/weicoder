package com.weicoder.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * rpc bean 注解
 * 
 * @author wudi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcBean {
	/**
	 * rpc 名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 调用方法
	 * 
	 * @return
	 */
	String method();
}
