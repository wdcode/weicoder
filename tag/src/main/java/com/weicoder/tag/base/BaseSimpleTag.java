package com.weicoder.tag.base;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.EmptyUtil;

/**
 * 基础标签实现
 * @author WD
 * @since JDK7
 * @version 1.0 2012-03-19
 */
public abstract class BaseSimpleTag extends SimpleTagSupport {
	// 工程路径
	private static String	path;
	// 键
	protected String		name;

	/**
	 * 标签开始
	 */
	public void doTag() throws JspException, IOException {
		// 打印到页面
		getJspContext().getOut().print(getInfo());
	}

	/**
	 * 获得工程路径
	 * @return 工程路径
	 */
	public String getPath() {
		return EmptyUtil.isEmpty(path) ? path = Conversion.toString(getJspContext().findAttribute("path")) : path;
	}

	/**
	 * 获得css名称
	 * @return css名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置css名称
	 * @param name css名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获得字符串信息
	 * @return 字符串信息
	 */
	protected abstract String getInfo();
}
