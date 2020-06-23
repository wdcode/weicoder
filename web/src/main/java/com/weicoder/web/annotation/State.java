package com.weicoder.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 返回状态码
 * 
 * @author WD
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
	/**
	 * 返回状态的字段名 默认state
	 * 
	 * @return 返回信息的字段名
	 */
	String code() default "code";

	/**
	 * 返回成功内容的字段名 默认content
	 * 
	 * @return 返回信息的字段名
	 */
	String content() default "content";

	/**
	 * 返回错误的字段名 默认msg
	 * 
	 * @return 返回错误的字段名
	 */
	String message() default "message";
}
