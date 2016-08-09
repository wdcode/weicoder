package com.weicoder.socket.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标明是socket的Handler
 * @author WD
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
	/**
	 * 名称 对应socket的名 一般为 server client
	 * @return
	 */
	String value() default "server";
}
