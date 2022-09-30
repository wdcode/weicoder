package com.weicoder.web.validator.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.weicoder.common.constants.C;

/**
 * 验证类注解要求方法必须返回错误码 错误码为成功通过 否则读取错误配置
 * @author WD
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface Validator {
	/**
	 * 验证类的名称 如果默认不输 在全局里搜索
	 * @return 验证类的名称
	 */
	String name() default C.S.EMPTY;

	/**
	 * 验证类下的方法
	 * @return 验证类下的方法
	 */
	String[] value();
}
