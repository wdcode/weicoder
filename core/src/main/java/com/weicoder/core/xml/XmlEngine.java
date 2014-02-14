package com.weicoder.core.xml;

import com.weicoder.core.params.CoreParams;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import com.thoughtworks.xstream.io.xml.JDomDriver;

/**
 * XML处理引擎
 * @author WD
 * @since JDK7
 * @version 1.0 2012-06-24
 */
public final class XmlEngine {
	// XStream
	private final static XStream	STREAM;

	/**
	 * 静态初始化
	 */
	static {
		// 判断使用那种XML解析包
		if ("jdom".equals(CoreParams.XML_PARSE)) {
			// 使用jdom
			STREAM = new XStream(new JDomDriver());
		} else {
			// 使用dom4j
			STREAM = new XStream(new Dom4JDriver());
		}
	}

	/**
	 * 把实体对象转换成xml字符串
	 * @param obj 要转换的实体对象
	 * @return 转换后的字符串
	 */
	public static String toXML(Object obj) {
		return STREAM.toXML(obj);
	}

	/**
	 * 把xml字符串转换成实体对象
	 * @param xml xml字符串
	 * @return 实体对象
	 */
	public static Object toBean(String xml) {
		return STREAM.fromXML(xml);
	}

	/**
	 * 把xml字符串转换成特定实体对象
	 * @param xml xml字符串
	 * @param clazz 要转换的类型
	 * @return 特定实体对象
	 */
	public static <T> T toBean(String xml, Class<T> clazz) {
		return (T) toBean(xml);
	}

	/**
	 * 私有构造
	 */
	private XmlEngine() {}
}
