package com.weicoder.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import com.weicoder.common.util.U.B;

/**
 * XML处理引擎
 * 
 * @author WD
 */
public final class XmlEngine {
	// XStream
	private final static XStream STREAM = new XStream(new Dom4JDriver());

	/**
	 * 把实体对象转换成xml字符串
	 * 
	 * @param obj 要转换的实体对象
	 * @return 转换后的字符串
	 */
	public static String toXML(Object obj) {
		return STREAM.toXML(obj);
	}

	/**
	 * 把xml字符串转换成实体对象
	 * 
	 * @param xml xml字符串
	 * @return 实体对象
	 */
	public static Object toBean(String xml) {
		return STREAM.fromXML(xml);
	}

	/**
	 * 把xml字符串转换成特定实体对象
	 * 
	 * @param xml   xml字符串
	 * @param clazz 要转换的类型
	 * @param <T>   范型
	 * @return 特定实体对象
	 */
	public static <T> T toBean(String xml, Class<T> clazz) {
		STREAM.allowTypes(new Class[] { clazz });
		return B.copy(toBean(xml), clazz);
//		return (T)toBean(xml);
	}

	private XmlEngine() {
	}
}
