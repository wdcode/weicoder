package com.weicoder.core.xml.impl.dom4j.output;

import org.dom4j.io.OutputFormat;

import com.weicoder.core.xml.output.Format;

/**
 * Format接口 Dom4J实现
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-23
 */
public final class FormatDom4J implements Format {
	// Dom4J OutputFormat
	private OutputFormat	format;

	/**
	 * 构造方法
	 * @param encoding 编码
	 */
	public FormatDom4J(String encoding) {
		// 创建漂亮的打印格式
		format = OutputFormat.createPrettyPrint();
		// 设置编码
		format.setEncoding(encoding);
	}

	/**
	 * 设置编码格式
	 * @param encoding 编码
	 */
	public void setEncoding(String encoding) {
		format.setEncoding(encoding);
	}

	/**
	 * 设置输出格式
	 * @param format OutputFormat
	 */
	public void setFormat(OutputFormat format) {
		this.format = format;
	}

	/**
	 * 获得输出格式
	 * @return OutputFormat
	 */
	public OutputFormat getFormat() {
		return format;
	}
}
