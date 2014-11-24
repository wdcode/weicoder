package com.weicoder.frame.action;

import javax.annotation.PostConstruct;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.util.TextProviderHelper;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.StringUtil;

/**
 * Struts2 Action
 * @author WD
 * @version 1.0
 */
public abstract class StrutsAction extends SuperAction {

	/**
	 * 初始化Action
	 */
	@PostConstruct
	protected void init() {
		init(ServletActionContext.getRequest(), ServletActionContext.getResponse(), getActionName());
	}

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

	/**
	 * 获得Action方法名 只保留x_x
	 * @return Action方法名
	 */
	public String getLink() {
		// 获得提交Action地址
		String actionName = getActionName();
		// 分解名称
		String[] name = StringUtil.split(actionName, StringConstants.UNDERLINE);
		// 返回链接名
		return name.length > 2 ? name[0] + StringConstants.UNDERLINE + name[1] : actionName;
	}

	/**
	 * 获得Action方法名
	 * @return Action方法名
	 */
	public String getActionName() {
		return ((ActionMapping) request.getAttribute(ServletActionContext.ACTION_MAPPING)).getName();
	}
}
