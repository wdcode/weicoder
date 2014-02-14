package com.weicoder.base.interceptor;

import com.weicoder.base.action.BasicAction;
import com.weicoder.common.util.EmptyUtil;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * 拦截指定Action
 * @author WD 2013-9-22
 */
public class ActionInterceptor<E extends BasicAction> extends MethodsInterceptor<E> {
	private static final long	serialVersionUID	= 7559495784335918181L;
	// 实体module
	protected String			module;

	/**
	 * 设置实体module
	 * @param module 实体module
	 */
	public void setModule(String module) {
		this.module = module;
	}

	@Override
	protected boolean execute(ActionInvocation invocation) {
		// 如果实体模版或者方法名为空
		if (EmptyUtil.isEmpty(module)) {
			return false;
		}
		// 获得Action
		BasicAction action = (BasicAction) invocation.getAction();
		// 判断是否相同module
		if (module.equals(action.getModule())) {
			// 判断是否方法
			return super.execute(invocation);
		}
		// 返回false
		return false;
	}
}
