package com.weicoder.xml.output;

import java.io.OutputStream;

import com.weicoder.xml.Document;

/**
 * XML文档输出 接口
 * @author WD 
 */
public interface XMLWrite {
	/**
	 * 设置输出格式
	 * @param format 输出格式
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

	/**
	 * 关闭资源
	 */
	void close();
}
