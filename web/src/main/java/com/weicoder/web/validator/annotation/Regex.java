package com.weicoder.web.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 根据RegexUtil判断正则表达式是否符合
 * @author WD
 */
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface Regex {
	/**
	 * 要验证的正则表达式
	 * @return 验证正则表达式
	 */
	String value();

	/**
	 * 验证不通过的错误码
	 * @return 错误码
	 */
	int error();
}
