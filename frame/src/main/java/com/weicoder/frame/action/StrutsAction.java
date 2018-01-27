package com.weicoder.frame.action;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.MathUtil;
import com.weicoder.common.util.StringUtil;

/**
 * Struts2 Action
 * @author WD
 * @version 1.0
 */
public abstract class StrutsAction extends SuperAction {
	// 提交的url
	protected String	url;
	// 跨域方法
	protected String	callback;
	// 要回执消息的字段
	protected String	field;

	 /**
	 * 初始化Action
	 */
	 protected void init() {
//	 init(ServletActionContext.getRequest(), ServletActionContext.getResponse(),
//	 getActionName());
	 }

	/**
	 * 获得国际化值
	 */
	public String getText(String name) {
		// 获得数组
//		String[] val = name.split(StringConstants.COMMA);
		// 设置字符串缓存类
		StringBuilder sb = new StringBuilder();
//		// 获得栈值
//		ValueStack vs = ActionContext.getContext().getValueStack();
//		// 循环
//		for (int i = 0; i < val.length; i++) {
//			// 添加内容
//			sb.append(TextProviderHelper.getText(val[i], val[i], vs));
//		}
		return sb.toString();
	}

	/**
	 * 获得当前Action
	 * @return Action
	 */ 
	public <E> E getAction() {
		// // 获得值栈里的对象
		// Object action = ActionContext.getContext().getValueStack().peek();
		// // 判断对象是Action类型的
		// if (action instanceof Action) {
		// // 返回Action
		// return (E) action;
		// }
		// // 获得Action拦截器
		// ActionInvocation ai = ActionContext.getContext().getActionInvocation();
		// // 如果拦截器不为空
		// if (ai != null) {
		// return (E) ai.getAction();
		// }
		// 如果都不符合返回null
		return null;
	}

	/**
	 * 以ajax模式输出数据到客户端方法
	 * @param response
	 * @param json 对象
	 */
	public String ajax(HttpServletResponse response, Object obj) {
		return super.ajax(response, obj == null ? StringConstants.EMPTY
				: !EmptyUtil.isEmpty(field) ? BeanUtil.getFieldValue(obj, field) : obj);
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
	 * 截取字符串
	 * @param str 要截取的字符串
	 * @param len 截取长度
	 * @return 截取后字符串
	 */
	public String substring(String str, int len) {
		// 判断字符串为空
		if (EmptyUtil.isEmpty(str)) {
			return str;
		}
		// 判断长度大于指定截取长度
		if (str.length() > len) {
			return StringUtil.subString(str, 0, len) + "...";
		}
		// 返回原字符串
		return str;
	}

	/**
	 * 获得Action方法
	 * @return Action方法
	 */
	public List<Object> getFields(Collection<Object> list, String fieldName) {
		return BeanUtil.getFieldValues(list, fieldName);
	}

	/**
	 * 获得国际化值
	 */
	public String add(List<Object> values) {
		return MathUtil.add(values.toArray()).toPlainString();
	}

	/**
	 * 获得Action方法名
	 * @return Action方法名
	 */
	public String getActionName() {
		String path = request.getServletPath();
		// 分解提交action 去处开头的/和结尾的.htm
		path = StringUtil.subString(path, 1, path.length() - 4);
		// 去掉前缀的命名空间并返回
		return StringUtil.subString(path, StringConstants.BACKSLASH);
		// return ServletActionContext.getContext().getName();//.getActionMapping().getName();
	}

	/**
	 * 获得url
	 * @return 提交的URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置url
	 * @param url 提交的URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获得跨域方法
	 * @return 跨域方法
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * 设置跨域方法
	 * @param callback 跨域方法
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * 设置模式名
	 * @param mode 模式名
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 获得模式名
	 * @return
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * 获得错误信息列表
	 * @return
	 */
	public List<String> getError() {
		return error;
	}

	/**
	 * 获得信息列表
	 * @return
	 */
	public List<String> getMessage() {
		return message;
	}

	/**
	 * 获得要显示的字段
	 * @return 要显示的字段
	 */
	public String getField() {
		return field;
	}

	/**
	 * 设置要显示的字段
	 * @param field 要显示的字段
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * 设置模板名
	 * @param module 模板名
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * 获得模板名
	 * @return
	 */
	public String getModule() {
		return module;
	}

	/**
	 * 获得方法名
	 * @return
	 */
	public String getMethod() {
		return method;
	}
}
