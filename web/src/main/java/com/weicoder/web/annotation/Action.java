package com.weicoder.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Action 注解
 * @author WD
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
	/**
	 * 是否启用ip验证 只允许servlet.ips 配置的ip
	 * @return 是否验证
	 */
	boolean ips() default false;

//	/**
//	 * 是否启用token验证 需要输入参数token 使用common里加密
//	 * @return 是否验证
//	 */
//	boolean token() default false;
}