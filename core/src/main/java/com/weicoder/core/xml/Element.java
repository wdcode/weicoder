package com.weicoder.core.xml;

import java.util.List;

import com.weicoder.common.interfaces.Empty;

/**
 * XML节点接口 <h2>注: 本包功能需要jdom或dom4j依赖包</h2>
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-18
 */
public interface Element extends Empty {

	/**
	 * 添加Element元素
	 * @param e Element元素对象
	 * @return this
	 */
	Element add(Element e);

	/**
	 * 给元素添加属性
	 * @param a 属性
	 * @return this
	 */
	Element add(Attribute a);

	/**
	 * 添加Element元素 空元素
	 * @param name 元素名
	 * @return this
	 */
	Element addElement(String name);

	/**
	 * 添加Element元素
	 * @param name 元素名
	 * @param text 元素内容
	 * @return this
	 */
	Element addElement(String name, String text);

	/**
	 * 给元素添加属性
	 * @param name 属性 name
	 * @param value 属性 value
	 * @return this
	 */
	Element addAttribute(String name, String value);

	/**
	 * 获得属性值
	 * @param name 属性name
	 * @return 属性
	 */
	Attribute getAttribute(String name);
	
	/**
	 * 获得属性列表
	 * @return 属性列表
	 */
	List<Attribute> getAttributes();

	/**
	 * 获得属性值
	 * @param name 属性name
	 * @return 属性值
	 */
	String getAttributeValue(String name);

	/**
	 * 设置元素内容
	 * @param text 元素内容
	 * @return this
	 */
	Element setText(String text);

	/**
	 * 获得本元素下的node子元素
	 * @param node 元素名
	 * @return 查找的元素
	 */
	Element getElement(String node);

	/**
	 * 获得本元素下的node的全部元素集合
	 * @param node 元素名
	 * @return 元素集合
	 */
	List<Element> getElements(String node);

	/**
	 * 获得本元素下的全部元素集合
	 * @return 元素集合
	 */
	List<Element> getElements();

	/**
	 * 获得元素名
	 * @return name
	 */
	String getName();

	/**
	 * 设置本元素名
	 * @param name 元素名
	 * @return this
	 */
	Element setName(String name);

	/**
	 * 获得文本内容
	 * @return text
	 */
	String getText();

	/**
	 * 获得本元素的Document
	 * @return Document
	 */
	Document getDocument();

	/**
	 * 设置本元素的Document
	 * @param doc Document对象
	 */
	Element setDocument(Document doc);
}
