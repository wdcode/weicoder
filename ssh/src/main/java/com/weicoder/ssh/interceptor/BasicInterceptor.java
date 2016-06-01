package com.weicoder.ssh.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.ssh.action.SuperAction;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 拦截指定Action
 * @author WD
 * 
 */
public class BasicInterceptor<E extends SuperAction> extends AbstractInterceptor {
	private static final long serialVersionUID = 8518576935365577689L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 验证是否拦截Action
		if (execute(invocation)) {
			// 获得Action
			E action = (E) invocation.getAction();
			// 前置方法
			if (before(action, ServletActionContext.getRequest())) {
				// 执行方法
				try {
					// 后置方法 返回结果
					return after(action, invocation.invoke());
				} catch (Exception e) {
					return exception(action, e);
				}
			} else {
				return Action.INPUT;
			}

		} else {
			return invocation.invoke();
		}
	}

	/**
	 * 是否执行下面拦截
	 */
	protected boolean execute(ActionInvocation invocation) {
		return true;
	}

	/**
	 * 前置通知方法
	 * @param action
	 * @param request
	 */
	protected boolean before(E action, HttpServletRequest request) {
		return true;
	}

	/**
	 * 异常处理
	 * @param action
	 * @param e
	 * @return
	 */
	protected String exception(E action, Throwable e) {
		// 声明消息字符串
		String mess = StringConstants.EMPTY;
		// 异常不为空
		while (e != null) {
			mess = e.getMessage();
			e = e.getCause();
		}
		// 返回错误信息
		action.addError(mess);
		// 返回错误
		return Action.ERROR;
	}

	/**
	 * 后置通知方法
	 * @param action
	 */
	protected String after(E action, String result) {
		return result;
	}
}
