package com.weicoder.ssh.interceptor;

import java.util.List;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.ssh.action.StrutsAction;
import com.weicoder.ssh.action.SuperAction;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * 拦截指定Action
 * @author WD
 * @version 1.0
 */
public class MethodsInterceptor<E extends SuperAction> extends BasicInterceptor<E> {
	private static final long	serialVersionUID	= 2547766715528276845L;
	// 方法
	protected List<String>		methods;

	/**
	 * 设置方法
	 * @param methods
	 */
	public void setMethods(String methods) {
		this.methods = Lists.newList(StringUtil.split(methods, StringConstants.COMMA));
	}

	@Override
	protected boolean execute(ActionInvocation invocation) {
		// 如果方法名为空
		if (EmptyUtil.isEmpty(methods)) { return false; }
		// 获得Action
		StrutsAction action = (StrutsAction) invocation.getAction();
		// 判断是否方法
		if (methods.contains(action.getMethod())) {
			return true;
		} else {
			return false;
		}
	}
}
