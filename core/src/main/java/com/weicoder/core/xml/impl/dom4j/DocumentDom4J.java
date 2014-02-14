package com.weicoder.core.xml.impl.dom4j;

import org.dom4j.DocumentHelper;

import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.xml.Document;
import com.weicoder.core.xml.Element;

/**
 * Document接口 Dom4J实现
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-19
 */
public final class DocumentDom4J implements Document {
	// Dom4J Document对象
	private org.dom4j.Document	document;
	// Element接口对象 根节点
	private Element				root;

	/**
	 * 构造方法
	 */
	public DocumentDom4J() {
		document = DocumentHelper.createDocument();
		root = new ElementDom4J(document.getRootElement());
	}

	/**
	 * 构造方法
	 * @param document XML Document
	 */
	public DocumentDom4J(org.dom4j.Document document) {
		this.document = document;
		root = new ElementDom4J(document.getRootElement());
	}

	/**
	 * 构造方法
	 * @param root 根节点
	 */
	public DocumentDom4J(Element root) {
		// 创建Document
		document = DocumentHelper.createDocument();
		// 创建根节点
		this.root = root;
		// 设置根
		setRoot();
	}

	/**
	 * 获得根接点
	 * @return 根接点
	 */
	public Element getRootElement() {
		return root;
	}

	/**
	 * 设置根节点
	 * @param root 根
	 */
	public void setRootElement(Element root) {
		this.root = root;
		// 设置根
		setRoot();
	}

	/**
	 * 判断是否空对象
	 * @return true 空对象 false 非空对象
	 */
	public boolean isEmpty() {
		return EmptyUtil.isEmpty(document);
	}

	/**
	 * 获得Dom4j Document
	 * @return org.dom4j.Document
	 */
	public org.dom4j.Document getDocument() {
		return document;
	}

	/**
	 * 设置Dom4j Document
	 * @param doc org.dom4j.Document
	 */
	public void setDocument(org.dom4j.Document doc) {
		document = doc;
	}

	/**
	 * 设置根节点
	 */
	private void setRoot() {
		// 设置根节点
		document.setRootElement(((ElementDom4J) root).getElement());
		// 设置Document
		root.setDocument(this);
	}

	/**
	 * 返回对象字符串
	 */
	public String toString() {
		return document.asXML();
	}
}
