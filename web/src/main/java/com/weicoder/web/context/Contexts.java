package com.weicoder.web.context;

import java.lang.reflect.Method;
import java.util.Map;

import com.weicoder.common.lang.Maps;

/**
 * 全局Context控制
 * @author WD
 * @since JDK7
 * @version 1.0 2016-1-14
 */
public final class Contexts {
	// Action列表
	public final static Map<String, Object>					ACTIONS	= Maps.getMap();
	// 回调方法处理
	public final static Map<String, Map<String, Method>>	METHODS	= Maps.getMap();
}