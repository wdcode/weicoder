package com.weicoder.web.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 验证非 null
 * @author WD
 */
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface NotNull {
	/**
	 * 验证不通过的错误码
	 * @return 错误码
	 */
	int error();
}
