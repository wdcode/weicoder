package com.weicoder.web.listener;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.weicoder.common.init.Inits;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.annotation.Action;
import com.weicoder.web.context.Contexts;
import com.weicoder.web.params.ServletParams;
import com.weicoder.common.log.Logs;

/**
 * 初始化监听器
 * @author WD
 */
@WebListener
public class InitListener implements ServletContextListener {
	/**
	 * 初始化资源
	 */
	public void contextInitialized(ServletContextEvent event) {
		// 初始化任务
		Inits.init();
		// 判断是否开启Servlet
		if (ServletParams.POWER) {
			// 按包处理
			for (Class<?> c : ClassUtil.getAnnotationClass(Action.class)) {
				try {
					// 获得action名结尾为action去掉
					String cname = StringUtil.convert(StringUtil.subStringLastEnd(c.getSimpleName(), "Action"));
					// 实例化Action并放在context中
					Object action = BeanUtil.newInstance(c);
					Contexts.ACTIONS.put(cname, action);
					if (action != null) {
						// 循环判断方法
						for (Method m : c.getDeclaredMethods()) {
							// 判断是公有方法
							if (Modifier.isPublic(m.getModifiers())) {
								// 获得方法名
								String mname = m.getName();
								// 放入action里方法
								Map<String, Method> map = Contexts.ACTIONS_METHODS.get(cname);
								if (map == null) {
									Contexts.ACTIONS_METHODS.put(cname, map = Maps.getMap());
								}
								map.put(mname, m);
								// 放入总方法池
								if (Contexts.METHODS.containsKey(mname)) {
									Logs.warn("method name exist! name=" + mname + " action=" + cname);
								}
								Contexts.METHODS.put(mname, m);
								// 方法对应action
								Contexts.METHODS_ACTIONS.put(mname, action);
							}
						}
					}
				} catch (Exception ex) {
					Logs.error(ex);
				}
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
}
