package com.weicoder.web.init;
 
import com.weicoder.common.init.Init;
import com.weicoder.common.lang.W.M;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.U; 
import com.weicoder.common.util.U.C;
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
		C.list(Aops.class).forEach(c -> {
			// 不是接口
			if (!c.isInterface()) {
				// 获得action名结尾为aop去掉
				String cname = U.S.convert(U.S.subStringLastEnd(c.getSimpleName(), "Aop"));
				Logs.info("init aop sname={},cname={}", c.getSimpleName(), cname);
				// 实例化Action并放在context中
				if (AopAll.class.isAssignableFrom(c))
					WebCommons.AOP_ALL.add(U.C.newInstance(c));
				else
					WebCommons.AOPS.put(cname, U.C.newInstance(c));
//				Aops aop = U.C.newInstance(c);
//				if (aop != null)
//					WebCommons.AOPS.put(cname, aop);
			}
		});

//		// 处理aop全部拦截
//		U.C.getAssignedClass(P.C.getPackages("aop"), AopAll.class).forEach(c -> {
//			// 初始化aopall
//			Logs.info("init aopall name={}", c.getSimpleName());
//			// 放到列表中
//			
//		});
	}

	private void action() {
		// 按包处理Action
		C.list(Action.class).forEach(c -> {
			try {
				// 获得action名结尾为action去掉
				String cname = U.S.convert(U.S.subStringLastEnd(c.getSimpleName(), "Action"));
				Logs.info("init action sname={},cname={}", c.getSimpleName(), cname);
				// 实例化Action并放在context中
//				Object action = U.C.newInstance(c);
				Object action = C.ioc(c);
				if (action != null) {
					WebCommons.ACTIONS.put(cname, action);
					// 循环判断方法
					U.C.getPublicMethod(c).forEach(m -> {
						// 获得方法名
						String mname = m.getName();
						// 放入action里方法
						M.getMap(WebCommons.ACTIONS_METHODS, cname).put(mname, m);
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
		C.list(ValidatorClass.class).forEach(c -> {
			// 获得validator名结尾为validator去掉
			String cname = U.S.convert(U.S.subStringLastEnd(c.getSimpleName(), "Validator"));
			Logs.info("init validator sname={},cname={}", c.getSimpleName(), cname);
			// 实例化Action并放在context中
//			Object validator = U.C.newInstance(c);
			Object validator = C.ioc(c);
			WebCommons.VALIDATORS.put(cname, validator);
			if (validator != null) {
				// 循环判断方法
				U.C.getPublicMethod(c).forEach(m -> {
					// 获得方法名
					String mname = m.getName();
					// 放入validator里方法
					M.getMap(WebCommons.VALIDATORS_METHODS,cname).put(mname, m);
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
