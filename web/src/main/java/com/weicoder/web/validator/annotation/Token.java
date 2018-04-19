package com.weicoder.web.validator.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 使用TokenEngine验证Token
 * @author WD
 */
@Target({ METHOD, TYPE })
@Retention(RUNTIME)
public @interface Token {
	/**
	 * 验证token的参数名 默认token
	 * @return
	 */
	String value() default "token";

	/**
	 * 无效的Token错误码
	 * @return 错误码
	 */
	int valid();

	/**
	 * 过期的Token错误码
	 * @return
	 */
	int expire();
}
