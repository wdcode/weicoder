package com.weicoder.web.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.weicoder.common.log.Logs;

/**
 * aop实现类接口 拦截注解的方法
 * @author wudi
 */
public interface Aops {

	/**
	 * 前置拦截
	 * @param action action
	 * @param params 请求参数
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */
	void before(Object action, Object[] params, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 后置拦截
	 * @param action action
	 * @param params 请求参数
	 * @param result 执行结果
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */
	void after(Object action, Object[] params, Object result, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 异常拦截
	 * @param t 异常
	 * @param action action
	 * @param params 请求参数
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */
	default void exception(Throwable t, Object action, Object[] params, HttpServletRequest request, HttpServletResponse response) {
		Logs.error(t);
	}
}
