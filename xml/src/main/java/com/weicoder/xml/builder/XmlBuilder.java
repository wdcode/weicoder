package com.weicoder.xml.builder;

import java.io.File;
import java.io.InputStream;

import com.weicoder.xml.Attribute;
import com.weicoder.xml.Document;
import com.weicoder.xml.Element;
import com.weicoder.dom4j.AttributeDom4J;
import com.weicoder.dom4j.DocumentDom4J;
import com.weicoder.dom4j.ElementDom4J;
import com.weicoder.dom4j.input.XMLReadDom4J;
import com.weicoder.dom4j.output.FormatDom4J;
import com.weicoder.dom4j.output.XMLWriteDom4J;
import com.weicoder.xml.input.XMLRead;
import com.weicoder.xml.output.Format;
import com.weicoder.xml.output.XMLWrite;
import com.weicoder.xml.params.XmlParams;

/**
 * XMLDocument工厂,创建XML解析用对象
 * <h2>注: 本包功能需要jdom或dom4j依赖包</h2>
 * 
 * @author WD
 */
public final class XmlBuilder {
	/**
	 * 获得Format对象 使用UTF-8编码
	 * 
	 * @return Format
	 */
	public static Format createFormat() {
		return createFormat(XmlParams.ENCODING);
	}

	/**
	 * 获得Format对象
	 * 
	 * @param encoding 编码格式
	 * @return Format
	 */
	public static Format createFormat(String encoding) {
		return new FormatDom4J(encoding);
	}

	/**
	 * 创建一个空的Document对象
	 * 
	 * @return Document
	 */
	public static Document createDocument() {
		return new DocumentDom4J();
	}

	/**
	 * 创建一个名为root的Document对象
	 * 
	 * @param root 根节点名
	 * @return Document
	 */
	public static Document createDocument(String root) {
		return createDocument(createElement(root));
	}

	/**
	 * 创建一个e为根节点的Document对象
	 * 
	 * @param e 根节点
	 * @return Document
	 */
	public static Document createDocument(Element e) {
		return new DocumentDom4J(e);
	}

	/**
	 * 创建一个名为默认的根节点的Element对象
	 * 
	 * @return Element
	 */
	public static Element createElement() {
		return createElement(XmlParams.ROOT);
	}

	/**
	 * 创建Element对象
	 * 
	 * @param name 根节点名称
	 * @return Element
	 */
	public static Element createElement(String name) {
		return new ElementDom4J(name);
	}

	/**
	 * 创建Attribute对象
	 * 
	 * @param name  名
	 * @param value 值
	 * @return Attribute
	 */
	public static Attribute createAttribute(String name, String value) {
		return new AttributeDom4J(name, value);
	}

	/**
	 * 创建XMLOutput对象
	 * 
	 * @return XMLOutput
	 */
	public static XMLWrite createXMLOutput() {
		return createXMLOutput(createFormat());
	}

	/**
	 * 创建XMLOutput对象
	 * 
	 * @param format Format输出格式
	 * @return XMLOutput @ 没有解析包
	 */
	public static XMLWrite createXMLOutput(Format format) {
		return new XMLWriteDom4J(format);
	}

	/**
	 * 创建XMLRead对象
	 * 
	 * @return XMLRead
	 */
	public static XMLRead createXMLRead() {
		return new XMLReadDom4J();
	}

	/**
	 * 根据文件名生成一个Document
	 * 
	 * @param xml XML字符串
	 * @return Document
	 */
	public static Document readDocument(String xml) {
		return createXMLRead().build(xml);
	}

	/**
	 * 根据文件生成一个Document
	 * 
	 * @param file XML文件
	 * @return Document
	 */
	public static Document readDocument(File file) {
		return createXMLRead().build(file);
	}

	/**
	 * 根据输入流生成一个Document
	 * 
	 * @param in XML文件流
	 * @return Document
	 */
	public static Document readDocument(InputStream in) {
		return createXMLRead().build(in);
	}

	private XmlBuilder() {
	}
}
