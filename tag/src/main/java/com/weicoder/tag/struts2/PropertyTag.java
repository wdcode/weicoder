package com.weicoder.tag.struts2;

import org.apache.struts2.components.Property;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.StringUtil;

/**
 * 封装struts2 PropertyTag
 * @author WD
 * @since JDK7
 * @version 1.0 2010-03-17
 */
public final class PropertyTag extends org.apache.struts2.views.jsp.PropertyTag {
	// 序列化ID
	private static final long	serialVersionUID	= -1446192418419252728L;
	// 属性值
	private String				value;

	/**
	 * 重写方法
	 */
	@Override
	protected void populateParams() {
		// 调用方法
		super.populateParams();
		// 获得属性实体
		Property tag = (Property) component;
		// 获得解析后的值
		value = getStack().findString(value);
		// 判断值里有提前需要解析的值
		if (value.indexOf(StringConstants.SEPA) > 0) {
			// 获得替换值
			String val = StringUtil.subString(value, StringConstants.SEPA, StringConstants.SEPA);
			// 从新设置值
			value = value.replaceAll(StringConstants.SEPA + val + StringConstants.SEPA, getStack().findString(val));
		}
		// 设置属性值
		tag.setValue(value);
	}

	/**
	 * 获得属性值
	 * @return 属性值
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置属性值
	 * @param value 属性值
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
