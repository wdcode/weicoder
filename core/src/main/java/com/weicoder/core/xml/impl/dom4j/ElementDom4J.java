package com.weicoder.core.xml.impl.dom4j;

import java.util.List;

import org.dom4j.DocumentHelper;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.xml.Attribute;
import com.weicoder.core.xml.Document;
import com.weicoder.core.xml.Element;

/**
 * XML节点接口 Dom4J实现
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-19
 */
public final class ElementDom4J implements Element {
	// Document对象
	private Document			doc;
	// Dom4J节点对象
	private org.dom4j.Element	element;

	/**
	 * 构造方法
	 * @param name 根节点名
	 */
	public ElementDom4J(String name) {
		element = DocumentHelper.createElement(name);
	}

	/**
	 * 构造方法
	 * @param e
	 */
	public ElementDom4J(org.dom4j.Element e) {
		element = e;
	}

	/**
	 * 获得Document
	 * @return Document
	 */
	public Document getDocument() {
		return doc;
	}

	/**
	 * 设置Document
	 * @param doc Document
	 */
	public Element setDocument(Document doc) {
		this.doc = doc;
		return this;
	}

	/**
	 * 添加Element元素
	 * @param e Element元素对象
	 * @return this
	 */
	public Element add(Element e) {
		element.add(((ElementDom4J) e).getElement());
		return this;
	}

	/**
	 * 添加Element元素
	 * @param name 元素名
	 * @param text 元素内容
	 * @return this
	 */
	public Element addElement(String name, String text) {
		element.add(DocumentHelper.createElement(name).addText(text));
		return this;
	}

	/**
	 * 添加Element元素 空元素
	 * @param name 元素名
	 * @return this
	 */
	public Element addElement(String name) {
		return addElement(name, StringConstants.EMPTY);
	}

	/**
	 * 给元素添加属性
	 * @param a 属性
	 * @return this
	 */
	public Element add(Attribute a) {
		element.add(((AttributeDom4J) a).getAttribute());
		return this;
	}

	/**
	 * 给元素添加属性
	 * @param name 属性 name
	 * @param value 属性 value
	 * @return this
	 */
	public Element addAttribute(String name, String value) {
		element.addAttribute(name, value);
		return this;
	}

	/**
	 * 设置本元素名
	 * @param name 元素名
	 */
	public Element setName(String name) {
		element.setName(name);
		return this;
	}

	/**
	 * 获得属性列表
	 * @return 属性列表
	 */
	public List<Attribute> getAttributes() {
		// 获得所以属性
		List<?> list = element.attributes();
		// 列表不为空
		if (EmptyUtil.isEmpty(list)) {
			// 返回空列表
			return Lists.emptyList();
		}
		// 获得列表长度
		int size = list.size();
		// 声明属性列表
		List<Attribute> attributes = Lists.getList(size);
		// 循环属性
		for (int i = 0; i < size; i++) {
			// 添加到Element接口集合
			attributes.add(new AttributeDom4J((org.dom4j.Attribute) list.get(i)));
		}
		// 返回集合
		return attributes;
	}

	/**
	 * 获得本元素下的node的全部元素集合
	 * @param node 元素名
	 * @return 元素集合
	 */
	public List<Element> getElements(String node) {
		return getElementList(element.elements(node));
	}

	/**
	 * 获得本元素下的全部元素集合
	 * @return 元素集合
	 */
	public List<Element> getElements() {
		return getElementList(element.elements());
	}

	/**
	 * 获得本元素下的node子元素
	 * @param node 元素名
	 * @return 查找的元素
	 */
	public Element getElement(String node) {
		return new ElementDom4J(element.element(node));
	}

	/**
	 * 获得元素名
	 * @return name
	 */
	public String getName() {
		return element.getName();
	}

	/**
	 * 获得文本内容
	 * @return text
	 */
	public String getText() {
		return element.getTextTrim();
	}

	/**
	 * 判断是否空对象
	 * @return true 空对象 false 非空对象
	 */
	public boolean isEmpty() {
		return EmptyUtil.isEmpty(element);
	}

	/**
	 * 设置属性
	 * @param a 属性
	 */
	public Element setAttribute(Attribute a) {
		element.add(((AttributeDom4J) a).getAttribute());
		return this;
	}

	/**
	 * 设置节点属性
	 * @param name 属性name
	 * @param value 属性 value
	 */
	public Element setAttribute(String name, String value) {
		element.addAttribute(name, value);
		return this;
	}

	/**
	 * 获得属性值
	 * @param name 属性name
	 * @return 属性value
	 */
	public String getAttributeValue(String name) {
		return element.attributeValue(name);
	}

	/**
	 * 设置元素内容
	 * @param text 元素内容
	 * @return this
	 */
	public Element setText(String text) {
		// 设置文本节点内容
		element.setText(text);
		// 返回自己
		return this;
	}

	/**
	 * 获得Dom4J Element
	 * @return Element
	 */
	public org.dom4j.Element getElement() {
		return element;
	}

	/**
	 * 设置Dom4J Element
	 */
	public void setElement(org.dom4j.Element e) {
		element = e;
	}

	/**
	 * 获得属性值
	 * @param name 属性name
	 * @return 属性
	 */
	public Attribute getAttribute(String name) {
		return new AttributeDom4J(element.attribute(name));
	}

	/**
	 * 返回对象字符串
	 */
	public String toString() {
		return element.asXML();
	}

	/**
	 * 把Dom4J节点集合 变成Element接口集合
	 * @param list
	 * @return
	 */
	private List<Element> getElementList(List<?> list) {
		// 判断列表不为空
		if (EmptyUtil.isEmpty(list)) {
			return Lists.emptyList();
		}
		// 获得列表大小
		int size = list.size();
		// 声明节点集合
		List<Element> lsElement = Lists.getList(size);
		// 循环Dom4J节点集合
		for (int i = 0; i < size; i++) {
			// 添加到Element接口集合
			lsElement.add(new ElementDom4J((org.dom4j.Element) list.get(i)));
		}
		// 返回集合
		return lsElement;
	}
}
