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
	 * @return token的参数名
	 */
	String value() default "token";

	/**
	 * 无效的Token错误码
	 * @return 错误码
	 */
	int valid() default 101;

	/**
	 * 过期的Token错误码
	 * @return 错误码
	 */
	int expire() default 102;

	/**
	 * 客户端ip不符的错误码
	 * @return 错误码
	 */
	int ip() default 0;

	/**
	 * 服务器分配sign不符的错误码
	 * @return 错误码
	 */
	int sign() default 0;

	/**
	 * 验证id是否相同 验证用户token与用户id是否相同 不为0验证
	 * @return 验证id
	 */
	String id() default StringConstants.EMPTY;
	
	/**
	 * 如果不为空 强制赋值不管是否有用户传入
	 * @return 强制参数
	 */
	String uid() default StringConstants.EMPTY;
}
