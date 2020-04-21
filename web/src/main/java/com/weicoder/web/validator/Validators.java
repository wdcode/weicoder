package com.weicoder.web.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import com.weicoder.common.bean.StateCode;
import com.weicoder.common.exception.StateException;
import com.weicoder.common.U;
import com.weicoder.common.W;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.params.StateParams;
import com.weicoder.common.token.TokenBean;
import com.weicoder.common.token.TokenEngine;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.IpUtil;
import com.weicoder.common.util.RegexUtil;
import com.weicoder.json.JsonEngine;
import com.weicoder.web.common.WebCommons;
import com.weicoder.web.params.ValidatorParams;
import com.weicoder.web.validator.annotation.Ip;
import com.weicoder.web.validator.annotation.Max;
import com.weicoder.web.validator.annotation.Min;
import com.weicoder.web.validator.annotation.NotEmpty;
import com.weicoder.web.validator.annotation.NotNull;
import com.weicoder.web.validator.annotation.Number;
import com.weicoder.web.validator.annotation.Regex;
import com.weicoder.web.validator.annotation.Token;
import com.weicoder.web.validator.annotation.Validator;

/**
 * 验证框架使用 根据条件验证
 * 
 * @author WD
 */
public final class Validators {
	// 日志
	private final static Log LOG = LogFactory.getLog(Validators.class);

	/**
	 * 根据注解验证参数
	 * 
	 * @param  par   参数类型
	 * @param  value 参数值
	 * @return       验证码
	 */
	public static int validator(Parameter par, Object value) {
		return validator(par.getAnnotations(), value);
	}

	/**
	 * 根据注解验证参数
	 * 
	 * @param  bean 验证bean
	 * @return      验证码
	 */
	public static int validator(Object bean) {
		// 获得所有字段
		for (Field field : BeanUtil.getFields(bean.getClass())) {
			// 对字段走验证
			int code = validator(field.getAnnotations(), BeanUtil.getFieldValue(bean, field));
			if (code != StateParams.SUCCESS)
				return code;
		}
		// 返回成功码
		return StateParams.SUCCESS;
	}

	/**
	 * 根据注解验证参数
	 * 
	 * @param  as    注解
	 * @param  value 参数值
	 * @return       校验值
	 */
	private static int validator(Annotation[] as, Object value) {
		// 如果是基本类型
		for (Annotation a : as) {
			// 判断验证类型
			if (a instanceof Number) {
				// 是数字并且在可用范围内
				long i = W.C.toLong(value, Long.MIN_VALUE);
				if (i < ((Number) a).min() || i > ((Number) a).max())
					return ((Number) a).error();
			} else if (a instanceof Max) {
				// 是数字并且小于最大值
				long i = W.C.toLong(value, Long.MAX_VALUE);
				if (i > ((Max) a).value())
					return ((Max) a).error();
			} else if (a instanceof Min) {
				// 是数字并且大于最小值
				long i = W.C.toLong(value, Long.MIN_VALUE);
				if (i < ((Min) a).value())
					return ((Min) a).error();
			} else if (a instanceof NotEmpty) {
				// 不为空
				if (U.E.isEmpty(value))
					return ((NotEmpty) a).value();
			} else if (a instanceof NotNull) {
				// 不为null
				if (value == null)
					return ((NotNull) a).error();
			} else if (a instanceof Regex) {
				// 判断正则
				if (!RegexUtil.is(((Regex) a).regex(), W.C.toString(value)))
					return ((Regex) a).error();
			}
		}
		// 返回成功码
		return StateParams.SUCCESS;
	}

	/**
	 * 调用验证方法
	 * 
	 * @param  vali 验证类
	 * @param  ps   提交参数
	 * @return      是否成功
	 */
	public static int validator(Validator vali, Map<String, String> ps) {
		// 返回错误码
		int res = 0;
		try {
			// 获得验证类名
			String name = vali.name();
			// 获得验证方法
			for (String val : vali.value()) {
				// 获得验证类
				Object obj = U.E.isEmpty(name) ? WebCommons.METHOD_VALIDATOR.get(val) : WebCommons.VALIDATORS.get(name);
				// 获得验证方法
				Method method = U.E.isEmpty(name) ? WebCommons.METHODS_VALIDATORS.get(val)
						: WebCommons.VALIDATORS_METHODS.get(name).get(val);
				// 获得所有参数类型
				Parameter[] pars = WebCommons.VALIDATORS_METHODS_PARAMES.get(method);
				Object[] params = new Object[pars.length];
				for (int i = 0; i < pars.length; i++) {
					// 判断类型并设置
					Parameter p = pars[i];
					// 参数的类型
					Class<?> cs = p.getType();
					if (Map.class.equals(cs))
						params[i] = ps;
					else if (ClassUtil.isBaseType(cs))
						// 获得参数
						params[i] = W.C.to(ps.get(p.getName()), cs);
					else
						// 设置属性
						params[i] = BeanUtil.copy(ps, cs);
					LOG.debug("validator Parameter index={},name={},type={},value={}", i, p.getName(), cs, params[i]);
				}
				// 调用并返回验证结果
				Object rs = null;
				try {
					rs = method.invoke(obj, params); // BeanUtil.invoke(obj, method, params);
				} catch (StateException e) {
					rs = e.state();
				}
				// 如果不是正确结果
				// 判断状态码对象
				if (rs instanceof StateCode && (res = ((StateCode) rs).getCode()) != StateParams.SUCCESS)
					break;
				// 判断状态码 int 类型
				if (rs instanceof Integer && (res = W.C.toInt(rs)) != StateParams.SUCCESS)
					break;
			}
		} catch (Exception e) {
			LOG.error(e);
			res = StateParams.ERROR;
		}
		// 返回验证码
		return res;
	}

	/**
	 * 验证方法与对象
	 * 
	 * @param  method 要验证方法
	 * @param  action 要验证对象
	 * @param  ps     参数
	 * @param  ip     用户ip
	 * @return        验证码
	 */
	public static int validator(Method method, Object action, Map<String, String> ps, String ip) {
		// 获得是否验证Token注解
		Token t = method.getAnnotation(Token.class);
		// 方法上没有 检查类上
		if (t == null)
			t = action.getClass().getAnnotation(Token.class);
		// 验证token不为空
		if (t != null) {
			// 验证token 获得Token
			TokenBean token = TokenEngine.decrypt(ps.get(t.value()));
			LOG.debug("action validator token={} t={}", JsonEngine.toJson(token));
			// 判断token
			if (t.valid() > 0 && !token.isValid())
				// 无效
				return t.valid();
			if (t.expire() > 0 && token.isExpire())
				// 过期
				return t.expire();
			if (t.sign() > 0 && token.isSign())
				// 是否服务器签发sign
				return t.sign();
			if (t.ban() > 0 && token.isBan())
				// 封号
				return t.ban();
			if (t.ip() > 0 && IpUtil.equals(ip, token.getIp()) > ValidatorParams.TOKEN_IP)
				// 客户端IP不符
				return t.ip();
			// 校验token与传入的用户ID是否相同
			if (U.E.isNotEmpty(t.id()) && W.C.toLong(ps.get(t.id())) != token.getId())
				// 不是用户
				return t.valid();
			// 用户id
			String uid = W.C.toString(token.getId());
			// 是否强制赋值参数
			if (U.E.isNotEmpty(t.uid()))
				ps.put(t.uid(), uid);
			else {
				// 没有强制的话 替换空参数
				if (!ps.containsKey("uid"))
					ps.put("uid", uid);
				if (!ps.containsKey("id"))
					ps.put("id", uid);
			}
		}

		// 验证ip
		Ip ipv = method.getAnnotation(Ip.class);
		// 方法上没有 检查类上
		if (ipv == null)
			ipv = action.getClass().getAnnotation(Ip.class);
		// ip验证不为空
		if (ipv != null)
			// 获得验证ip 判断是否在白名单
			if (!IpUtil.contains(ipv.value(), ip)) {
				LOG.debug("action validator ips not contains ip={}", ip);
				return ipv.error();
			}
		// 验证类
		Validator vali = method.getAnnotation(Validator.class);
		if (vali != null)
			return Validators.validator(vali, ps);
		// 返回成功
		return StateParams.SUCCESS;
	}

	private Validators() {
	}
}
