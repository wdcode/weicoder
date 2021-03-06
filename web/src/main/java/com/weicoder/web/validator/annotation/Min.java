package com.weicoder.web.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 验证必须是数字类型 并且大于等于指定值
 * @author WD
 */
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface Min {
	/**
	 * 验证数字的最小值
	 * @return 最小值
	 */
	long value();

	/**
	 * 验证不通过的错误码
	 * @return 错误码
	 */
	int error();
}
