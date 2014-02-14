package com.weicoder.base.interceptor;

import java.util.List;

import com.weicoder.base.action.BasicAction;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * 拦截指定Action
 * @author WD 2013-9-22
 */
public class MethodsInterceptor<E extends BasicAction> extends BasicInterceptor<E> {
	private static final long	serialVersionUID	= 2547766715528276845L;
	// 方法
	protected List<String>		methods;

	/**
	 * 设置方法
	 * @param methods
	 */
	public void setMethods(String methods) {
		this.methods = Lists.getList(StringUtil.split(methods, StringConstants.COMMA));
	}

	@Override
	protected boolean execute(ActionInvocation invocation) {
		// 如果方法名为空
		if (EmptyUtil.isEmpty(methods)) {
			return false;
		}
		// 获得Action
		BasicAction action = (BasicAction) invocation.getAction();
		// 判断是否方法
		if (methods.contains(action.getMethod())) {
			return true;
		} else {
			return false;
		}
	}
}
