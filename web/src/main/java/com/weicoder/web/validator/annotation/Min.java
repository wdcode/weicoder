package com.weicoder.web.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.weicoder.common.constants.StringConstants;

/**
 * 验证必须是数字类型 并且大于等于指定值
 * @author WD
 */
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface Min {
	/**
	 * 验证数字的最小值
	 * @return
	 */
	long value();

	/**
	 * 验证不通过的错误码
	 * @return 错误码
	 */
	int error();

	/**
	 * 验证不通过的错误信息 如果不写 根据error读取errorcode里的信息
	 * @return 错误信息
	 */
	String message() default StringConstants.EMPTY;
}
