package com.weicoder.core.xml.impl.jdom;

import com.weicoder.core.xml.Attribute;

/**
 * Attribute接口 JDom实现
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-23
 */
public final class AttributeJDom implements Attribute {
	// JDom Attribute对象
	private org.jdom.Attribute	attribute;

	/**
	 * 构造方法
	 * @param name 属性名
	 * @param value 属性值
	 */
	public AttributeJDom(String name, String value) {
		attribute = new org.jdom.Attribute(name, value);
	}

	/**
	 * 构造方法
	 * @param a 属性
	 */
	public AttributeJDom(org.jdom.Attribute a) {
		this.attribute = a;
	}

	/**
	 * 获得属性名
	 * @return name
	 */
	public String getName() {
		return attribute.getName();
	}

	/**
	 * 获得属性值
	 * @return value
	 */
	public String getValue() {
		return attribute.getValue();
	}

	/**
	 * 设置属性名
	 * @param name 名
	 */
	public void setName(String name) {
		attribute.setName(name);
	}

	/**
	 * 设置属性值
	 * @param value 值
	 */
	public void setValuel(String value) {
		attribute.setValue(value);
	}

	/**
	 * 设置JDom Attribute
	 * @param a org.jdom.Attribute
	 */
	public void setAttribute(org.jdom.Attribute a) {
		attribute = a;
	}

	/**
	 * 获得JDom Attribute
	 * @return org.jdom.Attribute
	 */
	public org.jdom.Attribute getAttribute() {
		return attribute;
	}
}
