package com.weicoder.web.action;

import java.lang.reflect.Method;
import java.util.Map;

import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams; 
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.annotation.Action;
import com.weicoder.web.aop.AopAll;
import com.weicoder.web.aop.Aops;
import com.weicoder.web.common.WebCommons;
import com.weicoder.web.validator.Validators;

/**
 * action 初始化工具类
 * 
 * @author wudi
 */
public final class Actions {
	/**
	 * action 初始化
	 */
	public static void init() {
		// 初始化验证类
		Validators.init();
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

		// 处理aop
		ClassUtil.getAssignedClass(CommonParams.getPackages("aop"), Aops.class).forEach(c -> {
			// 获得action名结尾为aop去掉
			String cname = StringUtil.convert(StringUtil.subStringLastEnd(c.getSimpleName(), "Aop"));
			Logs.info("init aop sname={},cname={}", c.getSimpleName(), cname);
			// 实例化Action并放在context中
			Aops aop = ClassUtil.newInstance(c);
			if (aop != null)
				WebCommons.AOPS.put(cname, aop);
		});

		// 处理aop全部拦截
		ClassUtil.getAssignedClass(CommonParams.getPackages("aop"), AopAll.class).forEach(c -> {
			// 初始化aopall
			Logs.info("init aopall name={}", c.getSimpleName());
			// 放到列表中
			WebCommons.AOP_ALL.add(ClassUtil.newInstance(c));
		});
	}

	private Actions() {
	}
}
