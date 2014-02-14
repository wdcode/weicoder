package com.weicoder.core.xml.impl.jdom.output;

import com.weicoder.core.xml.output.Format;

/**
 * Format接口 JDom实现
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-23
 */
public final class FormatJDom implements Format {
	// JDom Format
	private org.jdom.output.Format	format;

	/**
	 * 构造方法
	 * @param encoding 编码格式
	 */
	public FormatJDom(String encoding) {
		// 创建漂亮的打印格式
		format = org.jdom.output.Format.getPrettyFormat();
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
	 * @param format org.jdom.output.Format
	 */
	public void setFormat(org.jdom.output.Format format) {
		this.format = format;
	}

	/**
	 * 获得输出格式
	 * @return org.jdom.output.Format
	 */
	public org.jdom.output.Format getFormat() {
		return format;
	}
}
