package com.weicoder.web.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Set;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.token.TokenBean;
import com.weicoder.common.token.TokenEngine;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.RegexUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.json.JsonEngine;
import com.weicoder.web.common.WebCommons;
import com.weicoder.web.params.WebParams;
import com.weicoder.web.validator.annotation.Ip;
import com.weicoder.web.validator.annotation.Max;
import com.weicoder.web.validator.annotation.Min;
import com.weicoder.web.validator.annotation.NotEmpty;
import com.weicoder.web.validator.annotation.NotNull;
import com.weicoder.web.validator.annotation.Number;
import com.weicoder.web.validator.annotation.Regex;
import com.weicoder.web.validator.annotation.Token;
import com.weicoder.web.validator.annotation.Validator;
import com.weicoder.web.validator.annotation.ValidatorClass;

/**
 * 验证框架使用 根据条件验证
 * @author WD
 */
public final class Validators {
	/**
	 * 根据注解验证参数
	 * @param par 参数类型
	 * @param value 参数值
	 * @return 验证码
	 */
	public static int validator(Parameter par, Object value) {
		return validator(par.getAnnotations(), value);
	}

	/**
	 * 根据注解验证参数
	 * @param bean 验证bean
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

	/**
	 * 调用验证方法
	 * @param vali 验证类
	 * @param ps 提交参数
	 * @return 是否成功
	 */
	public static int validator(Validator vali, Map<String, String> ps) {
		try {
			// 获得验证类名
			String name = vali.name();
			// 获得验证方法
			String val = vali.value();
			// 获得验证类
			Object obj = EmptyUtil.isEmpty(name) ? WebCommons.METHOD_VALIDATOR.get(val)
					: WebCommons.VALIDATORS.get(name);
			// 获得验证方法
			Method method = EmptyUtil.isEmpty(name) ? WebCommons.METHODS_VALIDATORS.get(val)
					: WebCommons.VALIDATORS_METHODS.get(name).get(val);
			// 获得所有参数类型
			Parameter[] pars = WebCommons.VALIDATORS_METHODS_PARAMES.get(method);
			Object[] params = new Object[pars.length];
			for (int i = 0; i < pars.length; i++) {
				// 判断类型并设置
				Parameter p = pars[i];
				// 参数的类型
				Class<?> cs = p.getType();
				if (Map.class.equals(cs)) {
					params[i] = ps;
				} else if (ClassUtil.isBaseType(cs)) {
					// 获得参数
					params[i] = Conversion.to(ps.get(p.getName()), cs);
				} else {
					// 设置属性
					params[i] = BeanUtil.copy(ps, cs);
				}
				Logs.debug("validator Parameter index={},name={},type={},value={}", i, p.getName(), cs, params[i]);
			}
			// 调用并返回验证结果
			return Conversion.toInt(BeanUtil.invoke(obj, method, params));
		} catch (Exception e) {
			Logs.error(e);
			return -1;
		}
	}

	/**
	 * 验证方法与对象
	 * @param method 要验证方法
	 * @param action 要验证对象
	 * @param ps 参数
	 * @param ip 用户ip
	 * @return 验证码
	 */
	public static int validator(Method method, Object action, Map<String, String> ps, String ip) {
		// 获得是否验证Token注解
		Token t = method.getAnnotation(Token.class);
		// 方法上没有 检查类上
		if (t == null) {
			t = action.getClass().getAnnotation(Token.class);
		}
		// 验证token不为空
		if (t != null) {
			// 验证token 获得Token
			TokenBean token = TokenEngine.decrypt(ps.get(t.value()));
			Logs.debug("action validator token={} t={}", JsonEngine.toJson(token));
			// 判断token
			if (!token.isValid()) {
				// 无效
				return t.valid();
			} else if (token.isExpire()) {
				// 过期
				return t.expire();
			} else if (EmptyUtil.isNotEmpty(t.id()) && Conversion.toInt(ps.get(t.id())) != token.getId()) {
				// 不是用户
				return t.valid();
			}
			// 判断参数里没有id和uid
			String uid = Conversion.toString(token.getId());
			if (!ps.containsKey("uid")) {
				Logs.debug("action validator token add uid={} uid={}", ps.put("uid", uid), uid);
			}
			if (!ps.containsKey("id")) {
				Logs.debug("action validator token add id={} id={}", ps.put("id", uid), uid);
			}
		}

		// 验证ip
		Ip ipv = method.getAnnotation(Ip.class);
		// 方法上没有 检查类上
		if (ipv == null) {
			ipv = action.getClass().getAnnotation(Ip.class);
		}
		// ip验证不为空
		if (ipv != null) {
			// 获得验证ip
			Set<String> ips = Sets.newSet(StringUtil.split(ipv.value(), StringConstants.COMMA));
			Logs.debug("action validator ips={}", ips);
			// 判断是否在白名单
			if (!ips.contains(ip)) {
				Logs.debug("action validator ips not contains ip={}", ip);
				return ipv.error();
			}
		}

		// 验证类
		Validator vali = method.getAnnotation(Validator.class);
		if (vali != null) {
			return Validators.validator(vali, ps);
		}

		// 返回成功
		return WebParams.STATE_SUCCESS;
	}

	/**
	 * 初始化验证类
	 */
	public static void init() {
		// 循环所有验证类注解
		for (Class<?> c : ClassUtil.getAnnotationClass(CommonParams.getPackages("validator"), ValidatorClass.class)) {
			// 获得validator名结尾为validator去掉
			String cname = StringUtil.convert(StringUtil.subStringLastEnd(c.getSimpleName(), "Validator"));
			Logs.info("init validator sname={},cname={}", c.getSimpleName(), cname);
			// 实例化Action并放在context中
			Object validator = BeanUtil.newInstance(c);
			WebCommons.VALIDATORS.put(cname, validator);
			if (validator != null) {
				// 循环判断方法
				for (Method m : c.getDeclaredMethods()) {
					// 判断是公有方法
					if (Modifier.isPublic(m.getModifiers())) {
						// 获得方法名
						String mname = m.getName();
						// 放入validator里方法
						Map<String, Method> map = WebCommons.VALIDATORS_METHODS.get(cname);
						if (map == null) {
							WebCommons.VALIDATORS_METHODS.put(cname, map = Maps.newMap());
						}
						map.put(mname, m);
						Logs.info("validator add method={} to validator={}", mname, cname);
						// 放入总方法池
						if (WebCommons.METHODS_VALIDATORS.containsKey(mname)) {
							Logs.warn("validator method name exist! name={} action={}", mname, cname);
						}
						// 方法对应验证类
						WebCommons.METHODS_VALIDATORS.put(mname, m);
						// 方法对应METHOD_VALIDATOR
						WebCommons.METHOD_VALIDATOR.put(mname, validator);
						// 放入参数池
						WebCommons.VALIDATORS_METHODS_PARAMES.put(m, m.getParameters());
					}
				}
			}
		}
	}

	private Validators() {}
}
