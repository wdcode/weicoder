package com.weicoder.websocket.listener;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.StringUtil; 
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
				// 获得action名结尾为action去掉
				String cname = StringUtil.convert(StringUtil.subStringLastEnd(c.getSimpleName(), "Server"));
				Logs.debug("init websocket sname={},cname={}", c.getSimpleName(), cname);
				// 实例化Action并放在context中
				Object ws = BeanUtil.newInstance(c);
				WebSocketCommons.WEBSOCKES.put(cname, ws);
				if (ws != null) {
					// 循环判断方法
					for (Method m : c.getDeclaredMethods()) {
						// 判断是公有方法
						if (Modifier.isPublic(m.getModifiers())) {
							// 获得方法名
							String mname = m.getName();
							// 放入action里方法
							Map<String, Method> map = WebSocketCommons.WEBSOCKES_METHODS.get(cname);
							if (map == null)
								WebSocketCommons.WEBSOCKES_METHODS.put(cname, map = Maps.newMap());
							map.put(mname, m);
							Logs.debug("add method={} to websocket={}", mname, cname);
							// 放入总方法池
							if (WebSocketCommons.WS_METHODS.containsKey(mname))
								Logs.warn("method name exist! name={} websocket={}", mname, cname);
							WebSocketCommons.WS_METHODS.put(mname, m);
							// 方法对应action
							WebSocketCommons.METHODS_WEBSOCKES.put(mname, ws);
						}
					}
				}
			} catch (Exception ex) {
				Logs.error(ex);
			}
		});
	}
}
