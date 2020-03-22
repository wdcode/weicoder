package com.weicoder.web.init;

import java.lang.reflect.Method;
import java.util.Map;

import com.weicoder.common.init.Init;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.annotation.Action;
import com.weicoder.web.aop.AopAll;
import com.weicoder.web.aop.Aops;
import com.weicoder.web.common.WebCommons;
import com.weicoder.web.validator.annotation.ValidatorClass;

/**
 * action 初始化工具类
 * 
 * @author wudi
 */
public class ActionInit implements Init {
	@Override
	public void init() {
		// 初始化验证类
		validator();
		// 处理action
		action();
		// 处理aop
		aop();
	}

	private void aop() {
		// 处理aop
		ClassUtil.getAssignedClass(CommonParams.getPackages("aop"), Aops.class).forEach(c -> {
			// 不是接口
			if (!c.isInterface()) {
				// 获得action名结尾为aop去掉
				String cname = StringUtil.convert(StringUtil.subStringLastEnd(c.getSimpleName(), "Aop"));
				Logs.info("init aop sname={},cname={}", c.getSimpleName(), cname);
				// 实例化Action并放在context中
				if (AopAll.class.isAssignableFrom(c))
					WebCommons.AOP_ALL.add(ClassUtil.newInstance(c));
				else 
					WebCommons.AOPS.put(cname, ClassUtil.newInstance(c));
//				Aops aop = ClassUtil.newInstance(c);
//				if (aop != null)
//					WebCommons.AOPS.put(cname, aop);
			}
		});

//		// 处理aop全部拦截
//		ClassUtil.getAssignedClass(CommonParams.getPackages("aop"), AopAll.class).forEach(c -> {
//			// 初始化aopall
//			Logs.info("init aopall name={}", c.getSimpleName());
//			// 放到列表中
//			
//		});
	}

	private void action() {
		// 按包处理Action
		ClassUtil.getAnnotationClass(CommonParams.getPackages("action"), Action.class).forEach(c -> {
			try {
				// 获得action名结尾为action去掉
				String cname = StringUtil.convert(StringUtil.subStringLastEnd(c.getSimpleName(), "Action"));
				Logs.info("init action sname={},cname={}", c.getSimpleName(), cname);
				// 实例化Action并放在context中
				Object action = ClassUtil.newInstance(c);
				if (action != null) {
					WebCommons.ACTIONS.put(cname, action);
					// 循环判断方法
					ClassUtil.getPublicMethod(c).forEach(m -> {
						// 获得方法名
						String mname = m.getName();
						// 放入action里方法
						Map<String, Method> map = WebCommons.ACTIONS_METHODS.get(cname);
						if (map == null)
							WebCommons.ACTIONS_METHODS.put(cname, map = Maps.newMap());
						map.put(mname, m);
						Logs.info("add method={} to action={}", mname, cname);
						// 放入总方法池
						if (WebCommons.METHODS.containsKey(mname))
							Logs.warn("method name exist! name={} action={}", mname, cname);
						WebCommons.METHODS.put(mname, m);
						// 方法对应action
						WebCommons.METHODS_ACTIONS.put(mname, action);
						// 放入参数池
						WebCommons.METHODS_PARAMES.put(m, m.getParameters());
					});
				}
			} catch (Exception ex) {
				Logs.error(ex);
			}
		});
	}

	private void validator() {
		// 循环所有验证类注解
		ClassUtil.getAnnotationClass(CommonParams.getPackages("validator"), ValidatorClass.class).forEach(c -> {
			// 获得validator名结尾为validator去掉
			String cname = StringUtil.convert(StringUtil.subStringLastEnd(c.getSimpleName(), "Validator"));
			Logs.info("init validator sname={},cname={}", c.getSimpleName(), cname);
			// 实例化Action并放在context中
			Object validator = ClassUtil.newInstance(c);
			WebCommons.VALIDATORS.put(cname, validator);
			if (validator != null) {
				// 循环判断方法
				ClassUtil.getPublicMethod(c).forEach(m -> {
					// 获得方法名
					String mname = m.getName();
					// 放入validator里方法
					Map<String, Method> map = WebCommons.VALIDATORS_METHODS.get(cname);
					if (map == null)
						WebCommons.VALIDATORS_METHODS.put(cname, map = Maps.newMap());
					map.put(mname, m);
					Logs.info("validator add method={} to validator={}", mname, cname);
					// 放入总方法池
					if (WebCommons.METHODS_VALIDATORS.containsKey(mname))
						Logs.warn("validator method name exist! name={} action={}", mname, cname);
					// 方法对应验证类
					WebCommons.METHODS_VALIDATORS.put(mname, m);
					// 方法对应METHOD_VALIDATOR
					WebCommons.METHOD_VALIDATOR.put(mname, validator);
					// 放入参数池
					WebCommons.VALIDATORS_METHODS_PARAMES.put(m, m.getParameters());
				});
			}
		});
	}
}
