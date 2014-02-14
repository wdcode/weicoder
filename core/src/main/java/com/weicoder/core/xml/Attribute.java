package com.weicoder.core.xml;

/**
 * XML节点属性接口 <h2>注: 本包功能需要jdom或dom4j依赖包</h2>
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-18
 */
public interface Attribute {
	/**
	 * 获得属性名
	 * @return name
	 */
	String getName();

	/**
	 * 设置属性名
	 * @param name 名
	 */
	void setName(String name);

	/**
	 * 获得属性值
	 * @return value
	 */
	String getValue();

	/**
	 * 设置属性值
	 * @param value 值
	 */
	void setValuel(String value);
}
