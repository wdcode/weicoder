package com.weicoder.dom4j.input;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.weicoder.common.log.Logs;
import com.weicoder.common.statics.Closes;
import com.weicoder.xml.Document;
import com.weicoder.dom4j.DocumentDom4J;
import com.weicoder.xml.input.XMLRead;

/**
 * SAXBuilder接口 Dom4J实现
 * @author WD 
 */
public final class XMLReadDom4J implements XMLRead {
	// Dom4J SAXReader 读取XML文件
	private SAXReader reader;

	/**
	 * 构造方法
	 */
	public XMLReadDom4J() {
		reader = new SAXReader();
	}

	/**
	 * 使用输入流构建 Document
	 * @param in 输入流
	 * @return Document
	 */
	public Document build(InputStream in) {
		try {
			// 读取输入流in,成为Document
			return new DocumentDom4J(reader.read(in));
		} catch (DocumentException e) {
			// 记录日志
			Logs.error(e);
			// 返回null
			return null;
		} finally {
			Closes.close(in);
		}
	}

	/**
	 * 使用输入流构建 Document
	 * @param file 文件
	 * @return Document
	 */
	public Document build(File file) {
		try {
			// 读取文件,成为Document
			return new DocumentDom4J(reader.read(file));
		} catch (DocumentException e) {
			// 记录日志
			Logs.error(e);
			// 返回null
			return null;
		}
	}

	/**
	 * 使用输入流构建 Document
	 * @param xml XML字符串
	 * @return Document
	 */
	public Document build(String xml) {
		try {
			// 读取文件,成为Document
			return new DocumentDom4J(reader.read(new StringReader(xml)));
		} catch (DocumentException e) {
			// 记录日志
			Logs.error(e);
			// 返回null
			return null;
		}
	}
}