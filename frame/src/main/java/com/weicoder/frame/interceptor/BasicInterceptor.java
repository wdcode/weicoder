package com.weicoder.frame.interceptor;

import jakarta.servlet.http.HttpServletRequest;

import com.weicoder.frame.action.SuperAction;

/**
 * 拦截指定Action
 * @author WD 2013-9-22
 */
public class BasicInterceptor<E extends SuperAction> {

	public String intercept(Object invocation) throws Exception {
		// // 验证是否拦截Action
		// if (execute(invocation)) {
		// // 获得Action
		// E action = (E) invocation.getAction();
		// // 前置方法
		// if (before(action, ServletActionContext.getRequest())) {
		// // 执行方法
		// try {
		// // 后置方法 返回结果
		// return after(action, invocation.invoke());
		// } catch (Exception e) {
		// return exception(action, e);
		// }
		// } else {
		// return Action.INPUT;
		// }
		//
		// } else {
		// return invocation.invoke();
		// }
		return "";
	}

	/**
	 * 是否执行下面拦截
	 * @param invocation 拦截器
	 * @return 是否成功
	 */
	protected boolean execute(Object invocation) {
		return true;
	}

	/**
	 * 前置通知方法
	 * @param action Action
	 * @param request HttpServletRequest
	 * @return 是否成功
	 */
	protected boolean before(Object action, HttpServletRequest request) {
		return true;
	}

	/**
	 * 异常处理
	 * @param action Action
	 * @param e 异常
	 * @return 字符串
	 */
	protected String exception(Object action, Throwable e) {
		// // 声明消息字符串
		// String mess = StringConstants.EMPTY;
		// // 异常不为空
		// while (e != null) {
		// mess = e.getMessage();
		// e = e.getCause();
		// }
		// // 返回错误信息
		// action.addError(mess);
		// // 返回错误
		// return Action.ERROR;
		return "";
	}

	/**
	 * 后置通知方法
	 * @param action Action
	 * @param result 结果
	 * @return 字符串
	 */
	protected String after(Object action, String result) {
		return result;
	}
}
