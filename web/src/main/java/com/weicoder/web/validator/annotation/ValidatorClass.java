package com.weicoder.web.validator.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * * 验证类注解类 此注解下所有公有方法都为验证方法 要求方法必须返回错误码 错误码为成功通过 否则读取错误配置
 * @author WD
 */
@Target({ TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorClass {}