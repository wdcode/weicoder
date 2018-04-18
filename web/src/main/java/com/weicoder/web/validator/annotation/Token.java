package com.weicoder.web.validator.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.weicoder.common.constants.StringConstants;

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
