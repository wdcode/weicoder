package com.weicoder.core.xml.impl.dom4j.output;

import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.io.XMLWriter;

import com.weicoder.core.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.core.xml.Document;
import com.weicoder.core.xml.impl.dom4j.DocumentDom4J;
import com.weicoder.core.xml.output.Format;
import com.weicoder.core.xml.output.XMLWrite;

/**
 * XMLOutputter接口 Dom4J实现
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-23
 */
public final class XMLWriteDom4J implements XMLWrite {
	// Dom4J XMLWriter
	private XMLWriter	writer;
	// Format对象
	private Format		format;

	/**
	 * 输出XML文档
	 * @param doc Document对象
	 * @param out 输出流
	 */
	public void output(Document doc, OutputStream out) {
		try {
			// 实例化 XMLWriter
			writer = new XMLWriter(out, ((FormatDom4J) format).getFormat());
			// 写Document
			writer.write(((DocumentDom4J) doc).getDocument());
			// 刷新缓存
			writer.flush();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 输出XML文档
	 * @param doc Document对象
	 * @param os 输出流
	 * @param format 输出格式
	 */
	public void output(Document doc, OutputStream os, Format format) {
		// 设置格式
		this.format = format;
		// 调用自己方法
		output(doc, os);
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		// 关闭writer
		try {
			writer.close();
		} catch (IOException e) {
			Logs.error(e);
		} finally {
			writer = null;
		}
	}

	/**
	 * 设置输出格式
	 * @param format
	 */
	public void setFormat(Format format) {
		this.format = format;
	}

	/**
	 * 构造方法
	 */
	public XMLWriteDom4J() {
		// 获得Format实例
		format = new FormatDom4J(CommonParams.ENCODING);
	}

	/**
	 * 构造方法
	 * @param format 输出格式
	 */
	public XMLWriteDom4J(Format format) {
		// 设置Format实例
		this.format = format;
	}
}
