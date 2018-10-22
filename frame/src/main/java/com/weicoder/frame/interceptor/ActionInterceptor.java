package com.weicoder.frame.interceptor;

/**
 * 拦截指定Action
 * @author WD 2013-9-22
 */
public class ActionInterceptor {
	// 实体module
	protected String module;

	/**
	 * 设置实体module
	 * @param module 实体module
	 */
	public void setModule(String module) {
		this.module = module;
	}

	protected boolean execute(Object invocation) {
		// // 如果实体模版或者方法名为空
		// if (EmptyUtil.isEmpty(module)) {
		// return false;
		// }
		// // 获得Action
		// StrutsAction action = (StrutsAction) invocation.getAction();
		// // 判断是否相同module
		// if (module.equals(action.getModule())) {
		// // 判断是否方法
		// return super.execute(invocation);
		// }
		// 返回false
		return false;
	}
}
