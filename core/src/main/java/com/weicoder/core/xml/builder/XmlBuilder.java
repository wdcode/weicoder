package com.weicoder.core.xml.builder;

import java.io.File;
import java.io.InputStream;

import com.weicoder.core.params.CoreParams;
import com.weicoder.core.xml.Attribute;
import com.weicoder.core.xml.Document;
import com.weicoder.core.xml.Element;
import com.weicoder.core.xml.impl.dom4j.AttributeDom4J;
import com.weicoder.core.xml.impl.dom4j.DocumentDom4J;
import com.weicoder.core.xml.impl.dom4j.ElementDom4J;
import com.weicoder.core.xml.impl.dom4j.input.XMLReadDom4J;
import com.weicoder.core.xml.impl.dom4j.output.FormatDom4J;
import com.weicoder.core.xml.impl.dom4j.output.XMLWriteDom4J;
import com.weicoder.core.xml.impl.jdom.AttributeJDom;
import com.weicoder.core.xml.impl.jdom.DocumentJDom;
import com.weicoder.core.xml.impl.jdom.ElementJDom;
import com.weicoder.core.xml.impl.jdom.input.XMLReadJDom;
import com.weicoder.core.xml.impl.jdom.output.FormatJDom;
import com.weicoder.core.xml.impl.jdom.output.XMLWriteJDom;
import com.weicoder.core.xml.impl.jdom2.AttributeJDom2;
import com.weicoder.core.xml.impl.jdom2.DocumentJDom2;
import com.weicoder.core.xml.impl.jdom2.ElementJDom2;
import com.weicoder.core.xml.impl.jdom2.input.XMLReadJDom2;
import com.weicoder.core.xml.impl.jdom2.output.FormatJDom2;
import com.weicoder.core.xml.impl.jdom2.output.XMLWriteJDom2;
import com.weicoder.core.xml.input.XMLRead;
import com.weicoder.core.xml.output.Format;
import com.weicoder.core.xml.output.XMLWrite;

/**
 * XMLDocument工厂,创建XML解析用对象 <h2>注: 本包功能需要jdom或dom4j依赖包</h2>
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-18
 */
public final class XmlBuilder {
	/**
	 * 获得Format对象 使用UTF-8编码
	 * @return Format
	 */
	public static Format createFormat() {
		return createFormat(CoreParams.XML_ENCODING);
	}

	/**
	 * 获得Format对象
	 * @param encoding 编码格式
	 * @return Format
	 */
	public static Format createFormat(String encoding) {
		switch (CoreParams.XML_PARSE) {
			case "jdom2":
				return new FormatJDom2(encoding);
			case "jdom":
				return new FormatJDom(encoding);
			default:
				return new FormatDom4J(encoding);
		}
	}

	/**
	 * 创建一个空的Document对象
	 * @return Document
	 */
	public static Document createDocument() {
		switch (CoreParams.XML_PARSE) {
			case "jdom2":
				return new DocumentJDom2();
			case "jdom":
				return new DocumentJDom();
			default:
				return new DocumentDom4J();
		}
	}

	/**
	 * 创建一个名为root的Document对象
	 * @param root 根节点名
	 * @return Document
	 */
	public static Document createDocument(String root) {
		return createDocument(createElement(root));
	}

	/**
	 * 创建一个e为根节点的Document对象
	 * @param e 根节点
	 * @return Document
	 */
	public static Document createDocument(Element e) {
		switch (CoreParams.XML_PARSE) {
			case "jdom2":
				return new DocumentJDom2(e);
			case "jdom":
				return new DocumentJDom(e);
			default:
				return new DocumentDom4J(e);
		}
	}

	/**
	 * 创建一个名为默认的根节点的Element对象
	 * @return Element
	 */
	public static Element createElement() {
		return createElement(CoreParams.XML_ROOT);
	}

	/**
	 * 创建Element对象
	 * @param name 根节点名称
	 * @return Element
	 */
	public static Element createElement(String name) {
		switch (CoreParams.XML_PARSE) {
			case "jdom2":
				return new ElementJDom2(name);
			case "jdom":
				return new ElementJDom(name);
			default:
				return new ElementDom4J(name);
		}
	}

	/**
	 * 创建Attribute对象
	 * @param name 名
	 * @param value 值
	 * @return Attribute
	 */
	public static Attribute createAttribute(String name, String value) {
		switch (CoreParams.XML_PARSE) {
			case "jdom2":
				return new AttributeJDom2(name, value);
			case "jdom":
				return new AttributeJDom(name, value);
			default:
				return new AttributeDom4J(name, value);
		}
	}

	/**
	 * 创建XMLOutput对象
	 * @return XMLOutput
	 */
	public static XMLWrite createXMLOutput() {
		return createXMLOutput(createFormat());
	}

	/**
	 * 创建XMLOutput对象
	 * @param format Format输出格式
	 * @return XMLOutput @ 没有解析包
	 */
	public static XMLWrite createXMLOutput(Format format) {
		switch (CoreParams.XML_PARSE) {
			case "jdom2":
				return new XMLWriteJDom2(format);
			case "jdom":
				return new XMLWriteJDom(format);
			default:
				return new XMLWriteDom4J(format);
		}
	}

	/**
	 * 创建XMLRead对象
	 * @return XMLRead
	 */
	public static XMLRead createXMLRead() {
		switch (CoreParams.XML_PARSE) {
			case "jdom2":
				return new XMLReadJDom2();
			case "jdom":
				return new XMLReadJDom();
			default:
				return new XMLReadDom4J();
		}
	}

	/**
	 * 根据文件名生成一个Document
	 * @param xml XML字符串
	 * @return Document
	 */
	public static Document readDocument(String xml) {
		return createXMLRead().build(xml);
	}

	/**
	 * 根据文件生成一个Document
	 * @param file XML文件
	 * @return Document
	 */
	public static Document readDocument(File file) {
		return createXMLRead().build(file);
	}

	/**
	 * 根据输入流生成一个Document
	 * @param in XML文件流
	 * @return Document
	 */
	public static Document readDocument(InputStream in) {
		return createXMLRead().build(in);
	}

	/**
	 * 私有构造
	 */
	private XmlBuilder() {}
}
