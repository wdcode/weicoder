package com.weicoder.core.chart.impl;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import com.weicoder.core.chart.PieChart;
import com.weicoder.core.chart.base.BaseChart;

/**
 * 饼图实现
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-08
 */
public final class PieChartImpl extends BaseChart implements PieChart {
	// 数据源
	private DefaultPieDataset	dataset;

	/**
	 * 构造方法
	 */
	public PieChartImpl() {
		dataset = new DefaultPieDataset();
	}

	/**
	 * 设置值
	 * @param key 键
	 * @param value 值
	 */
	public void setValue(Comparable<?> key, Number value) {
		dataset.setValue(key, value);
	}

	/**
	 * 创建图表
	 */
	protected JFreeChart getChart() {
		return ChartFactory.createPieChart3D(title, dataset, true, false, false);
	}

	/**
	 * 设置字体
	 */
	protected void setFont() {
		// 获取图表区域对象
		PiePlot piePlot = (PiePlot) chart.getPlot();
		piePlot.setLabelFont(CONTENT_FONT);
		chart.getLegend().setItemFont(CONTENT_FONT);
	}
}
