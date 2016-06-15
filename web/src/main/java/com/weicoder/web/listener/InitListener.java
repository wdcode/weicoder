package com.weicoder.web.listener;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.annotation.Action;
import com.weicoder.web.context.Contexts; 
import com.weicoder.web.params.ServletParams; 
import com.weicoder.common.log.Logs;
import com.weicoder.core.params.QuartzParams;
import com.weicoder.core.params.SocketParams;
import com.weicoder.core.quartz.Quartzs;
import com.weicoder.core.socket.Sockets;

/**
 * 初始化监听器 
 * @author WD 
 * 
 */
@WebListener
public class InitListener implements ServletContextListener {
	/**
	 * 初始化资源
	 */
	public void contextInitialized(ServletContextEvent event) {
		// 获得Servlet上下文
		ServletContext context = event.getServletContext();
		// 设置路径
		setPath(context);

//		// 是否静态化
//		if (WebParams.STAICS_POWER) {
//			StaticsEngine.start();
//		}
		// 是否开启任务
		if (QuartzParams.POWER) {
			Quartzs.init();
		}
		// 是否开启socket
		if (SocketParams.POWER) {
			Sockets.init();
		}
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
								//方法对应action
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

	/**
	 * 销毁资源
	 */
	public void contextDestroyed(ServletContextEvent event) {
//		// 是否静态化
//		if (WebParams.STAICS_POWER) {
//			StaticsEngine.close();
//		}
		// 是否开启任务
		if (QuartzParams.POWER) {
			Quartzs.close();
		}
		// 是否开启socket
		if (SocketParams.POWER) {
			Sockets.close();
		}
	}

	/**
	 * 设置路径
	 */
	private void setPath(ServletContext context) {
		// 工程路径Key
		String path = "path";
		// 设置工程路径为path
		context.setAttribute(path, context.getContextPath());
		// 配置系统路径
		System.setProperty(path, context.getRealPath(StringConstants.EMPTY));
	}
}
