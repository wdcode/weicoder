package com.weicoder.core.xml.impl.jdom.output;

import java.io.IOException;
import java.io.OutputStream;

import org.jdom.output.XMLOutputter;

import com.weicoder.core.log.Logs;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.core.xml.Document;
import com.weicoder.core.xml.impl.jdom.DocumentJDom;
import com.weicoder.core.xml.output.Format;
import com.weicoder.core.xml.output.XMLWrite;

/**
 * XMLOutput接口 JDom实现
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-23
 */
public final class XMLWriteJDom implements XMLWrite {
	// JDom XMLWriter
	private XMLOutputter	writer;
	// Format对象
	private Format			format;
	// OutputStream对象
	private OutputStream	out;

	/**
	 * 构造方法
	 * @param format 输出格式
	 */
	public XMLWriteJDom(Format format) {
		// 设置Format实例
		this.format = format;
	}

	/**
	 * 输出XML文档
	 * @param doc Document对象
	 * @param os 输出流
	 */
	public void output(Document doc, OutputStream out) {
		// 获得out
		this.out = out;
		// 实例化 XMLOutputter
		writer = new XMLOutputter(((FormatJDom) format).getFormat());
		// 写Document
		try {
			writer.output(((DocumentJDom) doc).getDocument(), out);
		} catch (IOException e) {
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
		// 关闭流
		CloseUtil.close(out);
		// format置null
		format = null;
		// writer置null
		writer = null;

	}

	/**
	 * 设置输出格式
	 * @param format
	 */
	public void setFormat(Format format) {
		this.format = format;
	}
}
