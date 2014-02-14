package com.weicoder.tag.struts2;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;
import org.apache.struts2.views.jsp.SetTag;
import org.apache.struts2.views.jsp.ui.AbstractUITag;
import org.apache.struts2.views.jsp.ui.CheckboxListTag;
import org.apache.struts2.views.jsp.ui.CheckboxTag;
import org.apache.struts2.views.jsp.ui.DivTag;
import org.apache.struts2.views.jsp.ui.FileTag;
import org.apache.struts2.views.jsp.ui.LabelTag;
import org.apache.struts2.views.jsp.ui.PasswordTag;
import org.apache.struts2.views.jsp.ui.SelectTag;
import org.apache.struts2.views.jsp.ui.SubmitTag;
import org.apache.struts2.views.jsp.ui.TextFieldTag;
import org.apache.struts2.views.jsp.ui.TextareaTag;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 控制显示标签
 * @author WD
 * @since JDK7
 * @version 1.0 2010-03-24
 */
public final class TypeTag extends AbstractUITag {
	// 序列化ID
	private static final long	serialVersionUID	= -7903585573826768779L;
	/* 类型标签使用 */
	private final static String	TYPE				= "type";
	private final static String	SELECT				= "select";
	private final static String	TYPE_TEXTFIELD		= "textfield";
	private final static String	TYPE_LABEL			= "label";
	private final static String	TYPE_PASSWORD		= "password";
	private final static String	TYPE_FILE			= "file";
	private final static String	TYPE_TEXTAREA		= "textarea";
	private final static String	TYPE_BUTTON			= "button";
	private final static String	TYPE_CHECKBOX		= "checkbox";
	private final static String	TYPE_CHECKBOX_LIST	= "checkboxlist";
	private final static String	TYPE_SUBMIT			= "submit";
	private final static String	TYPE_SET			= "set";
	private final static String	DIV					= "div";

	// 声明一个AbstractUITag
	private ComponentTagSupport	tag;
	// 声明DivTag
	private DivTag				divTag;

	/**
	 * 获得Bean
	 */
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return EmptyUtil.isEmpty(tag) ? new Component(stack) : tag.getBean(stack, req, res);
	}

	/**
	 * 标签结束
	 */
	public int doEndTag() throws JspException {
		// 声明执行标识
		int flag = EVAL_PAGE;
		// 标签不为空设置属性
		if (!EmptyUtil.isEmpty(tag)) {
			flag = tag.doEndTag();
		}
		// 标签不为空设置属性
		if (!EmptyUtil.isEmpty(divTag)) {
			divTag.setBodyContent(tag.getBodyContent());
			divTag.doEndTag();
		}
		return flag;
	}

	/**
	 * 标签开始
	 */
	public int doStartTag() throws JspException {
		// 声明执行标识
		int flag = SKIP_BODY;
		// 初始化
		init();
		// 标签不为空设置属性
		if (!EmptyUtil.isEmpty(divTag)) {
			divTag.setPageContext(pageContext);
			divTag.doStartTag();
		}
		// 标签不为空设置属性
		if (!EmptyUtil.isEmpty(tag)) {
			tag.setPageContext(pageContext);
			flag = tag.doStartTag();
		}
		// 标签开始
		return flag;
	}

	/**
	 * 初始化操作
	 */
	protected void init() {
		// 设置标签为空
		tag = null;
		divTag = null;
		// 获得映射Map
		Map<String, String> map = (Map<String, String>) getStack().findValue(value);
		// 获得类型
		String type = Conversion.toString(map.get(TYPE));
		// 判断是什么类型的标签
		if (SELECT.equals(type)) {
			tag = new SelectTag();
		} else if (TYPE_TEXTFIELD.equals(type)) {
			tag = new TextFieldTag();
		} else if (TYPE_LABEL.equals(type)) {
			tag = new LabelTag();
		} else if (TYPE_PASSWORD.equals(type)) {
			tag = new PasswordTag();
		} else if (TYPE_SUBMIT.equals(type)) {
			tag = new SubmitTag();
		} else if (TYPE_FILE.equals(type)) {
			tag = new FileTag();
		} else if (TYPE_TEXTAREA.equals(type)) {
			tag = new TextareaTag();
		} else if (TYPE_CHECKBOX_LIST.equals(type)) {
			tag = new CheckboxListTag();
		} else if (TYPE_CHECKBOX.equals(type)) {
			tag = new CheckboxTag();
		} else if (TYPE_BUTTON.equals(type)) {
			tag = new SubmitTag();
			((SubmitTag) tag).setType(TYPE_BUTTON);
		} else if (TYPE_SET.equals(type)) {
			tag = new SetTag();
		}
		// 是否有div
		String div = map.get(DIV);
		// 判断是否有div包围
		if (div != null) {
			divTag = new DivTag();
			divTag.setCssClass(div);
		}
		// 判断标签不为空
		if (!EmptyUtil.isEmpty(tag)) {
			// 对标签赋值
			BeanUtil.copyProperties(tag, map);
		}
	}
}