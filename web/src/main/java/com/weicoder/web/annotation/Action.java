package com.weicoder.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.weicoder.common.annotation.Ico;

/**
 * Action 注解
 * @author WD
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Ico
public @interface Action {
	/**
	 * 是否启用ip验证 只允许servlet.ips 配置的ip
	 * @return 是否验证
	 */
	boolean ips() default false;

	/**
	 * 是否使用异步模式 为true时本注解下的所有方法都为异步
	 * @return 是否异步
	 */
	boolean async() default false;
}