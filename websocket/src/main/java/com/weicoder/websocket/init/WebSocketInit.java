package com.weicoder.websocket.init;
  
import com.weicoder.common.util.ClassUtil;
import com.weicoder.websocket.annotation.WebSocket;
import com.weicoder.websocket.common.WebSocketCommons;
import com.weicoder.common.U.C;
import com.weicoder.common.init.Init;
import com.weicoder.common.log.Logs; 

/**
 * 初始化监听器
 * 
 * @author WD
 */
public class WebSocketInit implements Init {
	@Override
	public void init() {
		// 按包处理WebSocket
		C.from(WebSocket.class).forEach(c -> {
			try {
				// 实例化Action并放在context中
				Object ws = ClassUtil.newInstance(c);
				if (ws != null) {
					// 循环判断方法
					ClassUtil.getPublicMethod(c).forEach(m -> {
						String n = m.getName();
						WebSocketCommons.WEBSOCKES.put(n, ws);
						// 放入方法列表
						WebSocketCommons.METHODS.put(n, m);
						// 放入参数池
						WebSocketCommons.PARAMES.put(m, m.getParameters());
						Logs.debug("add websocket method={}", n);
					});
				}
			} catch (Exception ex) {
				Logs.error(ex);
			}
		});
	}
}
