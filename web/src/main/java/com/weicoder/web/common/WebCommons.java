package com.weicoder.web.common;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import com.weicoder.common.lang.Maps;

/**
 * 全局Selvert控制
 * @author WD
 */
public final class WebCommons {
	/** Action列表 */
	public final static Map<String, Object>					ACTIONS						= Maps.newMap();
	/** 回调方法处理 */
	public final static Map<String, Map<String, Method>>	ACTIONS_METHODS				= Maps.newMap();
	/** 回调方法对应对象 */
	public final static Map<String, Object>					METHODS_ACTIONS				= Maps.newMap();
	/** 回调方法对应参数 */
	public final static Map<Method, Parameter[]>			METHODS_PARAMES				= Maps.newMap();
	/** 回调方法处理 */
	public final static Map<String, Method>					METHODS						= Maps.newMap(); 
	/** 验证类列表 */
	public final static Map<String, Object>					VALIDATORS					= Maps.newMap();
	/** 验证类方法处理 */
	public final static Map<String, Map<String, Method>>	VALIDATORS_METHODS			= Maps.newMap();
	/** 验证类方法处理 */
	public final static Map<String, Method>					METHODS_VALIDATORS			= Maps.newMap();
	/** 验证类方法对应对象 */
	public final static Map<String, Object>					METHOD_VALIDATOR			= Maps.newMap();
	/** 验证类方法对应参数 */
	public final static Map<Method, Parameter[]>			VALIDATORS_METHODS_PARAMES	= Maps.newMap();

	private WebCommons() {}
}