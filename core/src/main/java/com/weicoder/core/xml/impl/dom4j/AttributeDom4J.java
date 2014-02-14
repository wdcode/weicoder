package com.weicoder.core.xml.impl.dom4j;

import org.dom4j.DocumentHelper;

import com.weicoder.core.xml.Attribute;

/**
 * Attribute接口 Dom4J实现
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-23
 */
public final class AttributeDom4J implements Attribute {
	// Dom4J Attribute对象
	private org.dom4j.Attribute	attribute;

	/**
	 * 构造方法
	 * @param name 元素名
	 * @param value 元素值
	 */
	public AttributeDom4J(String name, String value) {
		attribute = DocumentHelper.createAttribute(null, name, value);
	}

	/**
	 * 构造方法
	 * @param name 元素名
	 * @param value 元素值
	 */
	public AttributeDom4J(org.dom4j.Attribute a) {
		attribute = a;
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
	 * 设置Dom4J Attribute
	 * @param a Dom4J Attribute
	 */
	public void setAttribute(org.dom4j.Attribute a) {
		attribute = a;
	}

	/**
	 * 获得Dom4J Attribute
	 * @return Dom4J Attribute
	 */
	public org.dom4j.Attribute getAttribute() {
		return attribute;
	}
}
