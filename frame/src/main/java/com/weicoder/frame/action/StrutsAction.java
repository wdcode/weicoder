package com.weicoder.frame.action;

import javax.servlet.ServletContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.util.TextProviderHelper;

import com.weicoder.common.constants.StringConstants;

/**
 * Struts2 Action
 * @author WD
 * @version 1.0
 */
public abstract class StrutsAction extends SuperAction {
	/**
	 * 获得国际化值
	 */
	public String getText(String name) {
		// 获得数组
		String[] val = name.split(StringConstants.COMMA);
		// 设置字符串缓存类
		StringBuilder sb = new StringBuilder();
		// 获得栈值
		ValueStack vs = ActionContext.getContext().getValueStack();
		// 循环
		for (int i = 0; i < val.length; i++) {
			// 添加内容
			sb.append(TextProviderHelper.getText(val[i], val[i], vs));
		}
		return sb.toString();
	}

	/**
	 * 获得ServletContext
	 * @return ServletContext
	 */
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	/**
	 * 获得程序路径
	 * @param name 文件名
	 * @return 程序路径
	 */
	public String getRealPath(String name) {
		return getServletContext().getRealPath(StringConstants.BACKSLASH) + name;
	}

	/**
	 * 获得Action方法名
	 * @return Action方法名
	 */
	public String getActionName() {
		return ((ActionMapping) getRequest().getAttribute(ServletActionContext.ACTION_MAPPING)).getName();
	}

	/**
	 * 获得当前Action
	 * @return Action
	 */
	@SuppressWarnings("unchecked")
	public <E extends Action> E getAction() {
		// 获得值栈里的对象
		Object action = ActionContext.getContext().getValueStack().peek();
		// 判断对象是Action类型的
		if (action instanceof Action) {
			// 返回Action
			return (E) action;
		}
		// 获得Action拦截器
		ActionInvocation ai = ActionContext.getContext().getActionInvocation();
		// 如果拦截器不为空
		if (ai != null) {
			return (E) ai.getAction();
		}
		// 如果都不符合返回null
		return null;
	}
}
