package com.weicoder.web.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.RegexUtil;
import com.weicoder.web.params.WebParams;
import com.weicoder.web.validator.annotation.Max;
import com.weicoder.web.validator.annotation.Min;
import com.weicoder.web.validator.annotation.NotEmpty;
import com.weicoder.web.validator.annotation.NotNull;
import com.weicoder.web.validator.annotation.Number;
import com.weicoder.web.validator.annotation.Regex;

/**
 * 验证框架使用 根据条件验证
 * @author WD
 */
public final class Validators {
	/**
	 * 根据注解验证参数
	 * @param pars 参数类型
	 * @param ps 参数值
	 * @return 验证码
	 */
	public static int validator(Parameter par, Object value) {
		return validator(par.getAnnotations(), value);
	}

	/**
	 * 根据注解验证参数
	 * @param pars 参数类型
	 * @param ps 参数值
	 * @return 验证码
	 */
	public static int validator(Object bean) {
		// 获得所有字段
		for (Field field : BeanUtil.getFields(bean.getClass())) {
			// 对字段走验证
			int code = validator(field.getAnnotations(), BeanUtil.getFieldValue(bean, field));
			if (code != WebParams.STATE_SUCCESS) {
				return code;
			}
		}
		// 返回成功码
		return WebParams.STATE_SUCCESS;
	}

	/**
	 * 根据注解验证参数
	 * @param as 注解
	 * @param value 参数值
	 * @return 校验值
	 */
	private static int validator(Annotation[] as, Object value) {
		// 如果是基本类型
		for (Annotation a : as) {
			// 判断验证类型
			if (a instanceof Number) {
				// 是数字并且在可用范围内
				long i = Conversion.toLong(value, Long.MIN_VALUE);
				if (i < ((Number) a).min() || i > ((Number) a).max()) {
					return ((Number) a).error();
				}
			} else if (a instanceof Max) {
				// 是数字并且小于最大值
				long i = Conversion.toLong(value, Long.MAX_VALUE);
				if (i > ((Max) a).value()) {
					return ((Max) a).error();
				}
			} else if (a instanceof Min) {
				// 是数字并且大于最小值
				long i = Conversion.toLong(value, Long.MIN_VALUE);
				if (i < ((Min) a).value()) {
					return ((Min) a).error();
				}
			} else if (a instanceof NotEmpty) {
				// 不为空
				if (EmptyUtil.isEmpty(value)) {
					return ((NotEmpty) a).error();
				}
			} else if (a instanceof NotNull) {
				// 不为null
				if (value == null) {
					return ((NotNull) a).error();
				}
			} else if (a instanceof Regex) {
				// 判断正则
				if (!RegexUtil.is(((Regex) a).value(), Conversion.toString(value))) {
					return ((Regex) a).error();
				}
			}
		}
		// 返回成功码
		return WebParams.STATE_SUCCESS;
	}

	private Validators() {}
}
