package com.weicoder.websocket.common;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import com.weicoder.common.lang.Maps;

/**
 * 全局Selvert控制
 * @author WD
 */
public final class WebSocketCommons {
	/** WebSocket列表 */
	public final static Map<String, Object>			WEBSOCKES	= Maps.newMap();
	/** 回调方法处理 */
	public final static Map<String, Method>			METHODS		= Maps.newMap();
	/** 回调方法对应参数 */
	public final static Map<Method, Parameter[]>	PARAMES		= Maps.newMap();

	private WebSocketCommons() {
	}
}