package com.weicoder.core.chart.base;

import java.awt.Font;
import java.io.OutputStream;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import com.weicoder.core.chart.Chart;
import com.weicoder.core.log.Logs;
import com.weicoder.common.params.Params;
import com.weicoder.common.util.EmptyUtil;

/**
 * 基础图表
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-08
 */
public abstract class BaseChart implements Chart {
	// 图表标题字体
	protected final static Font	TITLE_FONT		= new Font(Params.getString("font.title.name", "黑体"), Font.BOLD, Params.getInt("font.title.size", 15));
	// 图表内容字体
	protected final static Font	CONTENT_FONT	= new Font(Params.getString("font.content.name", "宋体"), Font.BOLD, Params.getInt("font.content.size", 12));
	// 标题
	protected String			title;
	// 图表
	protected JFreeChart		chart;

	/**
	 * 写成PNG格式图片
	 * @param out 输出流
	 * @param width 宽度
	 * @param height 高度
	 */
	public void writePNG(OutputStream out, int width, int height) {
		try {
			// 创建图表
			createChart();
			// 写PNG图片
			ChartUtilities.writeChartAsPNG(out, chart, width, height);
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	/**
	 * 写成JPG格式图片
	 * @param out 输出流
	 * @param width 宽度
	 * @param height 高度
	 */
	public void writeJPEG(OutputStream out, int width, int height) {
		try {
			// 创建图表
			createChart();
			// 写JPG图片
			ChartUtilities.writeChartAsJPEG(out, chart, width, height);
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	/**
	 * 获得标题
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * @param title 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 创建图表
	 */
	protected void createChart() {
		// 判断图表为空
		if (EmptyUtil.isEmpty(chart)) {
			this.chart = getChart();
		}
		// 设置标题字体
		chart.getTitle().setFont(TITLE_FONT);
		// 设置字体
		setFont();
	}

	/**
	 * 创建图表
	 */
	protected abstract JFreeChart getChart();

	/**
	 * 设置字体
	 */
	protected abstract void setFont();
}
