package com.weicoder.websocket.listener;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.websocket.annotation.WebSocket;
import com.weicoder.websocket.common.WebSocketCommons;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;

/**
 * 初始化监听器
 * @author WD
 */
@WebListener
public class InitWebSocketListener implements ServletContextListener {
	/**
	 * 初始化资源
	 */
	public void contextInitialized(ServletContextEvent event) {
		// 按包处理WebSocket
		ClassUtil.getAnnotationClass(CommonParams.getPackages("websocket"), WebSocket.class).forEach(c -> {
			try {
				// 实例化Action并放在context中
				Object ws = BeanUtil.newInstance(c);
				if (ws != null) {
					// 循环判断方法
					for (Method m : c.getDeclaredMethods()) {
						String n = m.getName();
						WebSocketCommons.WEBSOCKES.put(n, ws);
						// 判断是公有方法
						if (Modifier.isPublic(m.getModifiers())) {
							// 放入方法列表
							WebSocketCommons.METHODS.put(n, m);
							// 放入参数池
							WebSocketCommons.PARAMES.put(m, m.getParameters());
							Logs.debug("add websocket method={}", n);
						}
					}
				}
			} catch (Exception ex) {
				Logs.error(ex);
			}
		});
	}
}
