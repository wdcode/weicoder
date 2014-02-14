package com.weicoder.core.xml.output;

import java.io.OutputStream;

import com.weicoder.common.interfaces.Close;
import com.weicoder.core.xml.Document;

/**
 * XML文档输出 接口
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-18
 */
public interface XMLWrite extends Close {
	/**
	 * 设置输出格式
	 * @param format
	 */
	void setFormat(Format format);

	/**
	 * 输出XML文档
	 * @param doc Document对象
	 * @param os 输出流
	 */
	void output(Document doc, OutputStream os);

	/**
	 * 输出XML文档
	 * @param doc Document对象
	 * @param os 输出流
	 * @param format 输出格式
	 */
	void output(Document doc, OutputStream os, Format format);
}
