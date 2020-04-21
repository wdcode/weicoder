package com.weicoder.frame.interceptor;

import java.util.List;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists; 
import com.weicoder.common.util.StringUtil; 
import com.weicoder.frame.action.SuperAction;
 

/**
 * 拦截指定Action
 * @author WD 2013-9-22
 */
public class MethodsInterceptor<E extends SuperAction> extends BasicInterceptor<E> { 
	// 方法
	protected List<String>		methods;

	/**
	 * 设置方法
	 * @param methods 方法
	 */
	public void setMethods(String methods) {
		this.methods = Lists.newList(StringUtil.split(methods, StringConstants.COMMA));
	}

	@Override
	protected boolean execute(Object invocation) {
//		// 如果方法名为空
//		if (EmptyUtil.isEmpty(methods)) {
//			return false;
//		}
//		// 获得Action
//		StrutsAction action = (StrutsAction) invocation.getAction();
//		// 判断是否方法
//		if (methods.contains(action.getMethod())) {
//			return true;
//		} else {
			return false;
//		}
	}
}
