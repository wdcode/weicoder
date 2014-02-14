package com.weicoder.core.chart;

import java.io.OutputStream;

/**
 * 图表接口
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-22
 */
public interface Chart {
	/**
	 * 写成PNG格式图片
	 * @param out 输出流
	 * @param width 宽度
	 * @param height 高度
	 */
	void writePNG(OutputStream out, int width, int height);

	/**
	 * 写成JPG格式图片
	 * @param out 输出流
	 * @param width 宽度
	 * @param height 高度
	 */
	void writeJPEG(OutputStream out, int width, int height);

	/**
	 * 获得标题
	 * @return 标题
	 */
	String getTitle();

	/**
	 * 设置标题
	 * @param title 标题
	 */
	void setTitle(String title);
}
