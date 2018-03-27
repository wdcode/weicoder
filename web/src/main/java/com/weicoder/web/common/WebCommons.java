package com.weicoder.web.common;

import java.lang.reflect.Method;
import java.util.Map;

import com.weicoder.common.lang.Maps;

/**
 * 全局Selvert控制
 * @author WD
 */
public final class WebCommons {
	/** Action列表 */
	public final static Map<String, Object>					ACTIONS				= Maps.newMap();
	/** 回调方法处理 */
	public final static Map<String, Map<String, Method>>	ACTIONS_METHODS		= Maps.newMap();
	/** 回调方法处理 */
	public final static Map<String, Object>					METHODS_ACTIONS		= Maps.newMap();
	/** 回调方法处理 */
	public final static Map<String, Method>					METHODS				= Maps.newMap();
	/** WebSocket列表 */
	public final static Map<String, Object>					WEBSOCKES			= Maps.newMap();
	/** 回调方法处理 */
	public final static Map<String, Map<String, Method>>	WEBSOCKES_METHODS	= Maps.newMap();
	/** 回调方法处理 */
	public final static Map<String, Object>					METHODS_WEBSOCKES	= Maps.newMap();
	/** 回调方法处理 */
	public final static Map<String, Method>					WS_METHODS			= Maps.newMap();

	private WebCommons() {}
}