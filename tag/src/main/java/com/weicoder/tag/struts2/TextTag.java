package com.weicoder.tag.struts2;

import org.apache.struts2.util.TextProviderHelper;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;

/**
 * 扩展struts2 TextTag
 * @author WD
 * @since JDK7
 * @version 1.0 2010-03-17
 */
public final class TextTag extends org.apache.struts2.views.jsp.TextTag {
	// 序列化ID
	private static final long	serialVersionUID	= 5930626629414166733L;
	// 替换字符串
	private static final String	NAME;
	// 替换标识
	private static final String	FLAG;

	/**
	 * 静态初始化
	 */
	static {
		FLAG = "#name";
		NAME = "#attr.template.get('" + FLAG + "')";
	}

	/**
	 * 重写方法
	 */
	protected void populateParams() {
		// 解析名
		name = name.startsWith("#") || name.startsWith("%") ? name : NAME.replaceAll(FLAG, name);
		// 获得真实国际化字符串
		name = Conversion.toString(getStack().findString(name));
		// 判断去掉[]
		if (name.indexOf("[") > -1 || name.indexOf("]") > -1) {
			name = name.replaceAll("[\\[\\]]", "");
		}
		// 判断名不为空 并且有,分割
		if (name.indexOf(StringConstants.COMMA) > -1) {
			// 获得数组
			String[] val = name.split(StringConstants.COMMA);
			// 设置字符串缓存类
			StringBuilder sb = new StringBuilder();
			// 循环
			for (int i = 0; i < val.length; i++) {
				// 添加内容
				sb.append(TextProviderHelper.getText(val[i], StringConstants.EMPTY, getStack()));
			}
			// 设置属性
			name = sb.toString();
		}
		// 调用父方法
		super.populateParams();
	}
}
