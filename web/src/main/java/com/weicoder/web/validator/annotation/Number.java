package com.weicoder.web.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 验证必须是数字 并且验证最大值与最小值
 * @author WD
 */
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface Number {
	/**
	 * 数字最小值
	 * @return 最小值
	 */
	long min();

	/**
	 * 数字最大值
	 * @return 最大值
	 */
	long max();

	/**
	 * 验证不通过的错误码
	 * @return 错误码
	 */
	int error();
}
