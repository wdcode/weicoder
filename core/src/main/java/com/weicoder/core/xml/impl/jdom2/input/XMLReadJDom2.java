package com.weicoder.core.xml.impl.jdom2.input;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import org.jdom2.input.SAXBuilder;

import com.weicoder.core.xml.Document;
import com.weicoder.core.xml.impl.jdom2.DocumentJDom2;
import com.weicoder.core.xml.input.XMLRead;

import com.weicoder.core.log.Logs;
import com.weicoder.common.util.CloseUtil;

/**
 * SAXBuilder接口 JDom实现
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-23
 */
public final class XMLReadJDom2 implements XMLRead {
	// JDom SAXReader 读取XML文件
	private SAXBuilder	builder;

	/**
	 * 构造方法
	 */
	public XMLReadJDom2() {
		builder = new SAXBuilder();
	}

	/**
	 * 使用输入流构建 Document
	 * @param in 输入流
	 * @return Document
	 */
	public Document build(InputStream in) {
		try {
			return new DocumentJDom2(builder.build(in));
		} catch (Exception e) {
			// 记录日志
			Logs.error(e);
			// 返回null
			return null;
		} finally {
			CloseUtil.close(in);
		}
	}

	/**
	 * 使用输入流构建 Document
	 * @param file 文件
	 * @return Document
	 */
	public Document build(File file) {
		try {
			return new DocumentJDom2(builder.build(file));
		} catch (Exception e) {
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
			return new DocumentJDom2(builder.build(new StringReader(xml)));
		} catch (Exception e) {
			// 记录日志
			Logs.error(e);
			// 返回null
			return null;
		}
	}
}
