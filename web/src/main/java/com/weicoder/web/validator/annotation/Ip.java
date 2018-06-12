package com.weicoder.web.validator.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 验证ip
 * @author WD
 */
@Target({ METHOD, TYPE })
@Retention(RUNTIME)
public @interface Ip {
	/**
	 * 获得ip使用 支持*号
	 * @return 验证ip值
	 */
	String value();

	/**
	 * 验证不通过的错误码
	 * @return 错误码
	 */
	int error();
}
