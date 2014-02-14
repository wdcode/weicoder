package com.weicoder.core.chart.impl;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import com.weicoder.core.chart.BarChart;
import com.weicoder.core.chart.base.BaseChart;

/**
 * 柱图实现
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-08
 */
public final class BarChartImpl extends BaseChart implements BarChart {
	// 数据源
	private DefaultCategoryDataset	dataset;
	// 目录轴的显示标签
	private String					categoryAxisLabel;
	// 数值轴的显示标签
	private String					valueAxisLabel;

	/**
	 * 构造方法
	 */
	public BarChartImpl() {
		dataset = new DefaultCategoryDataset();
	}

	/**
	 * 添加值
	 * @param value 值
	 * @param rowKey 行键
	 * @param columnKey 列键
	 */
	public void addValue(Number value, Comparable<?> rowKey, Comparable<?> columnKey) {
		dataset.addValue(value, rowKey, columnKey);
	}

	/**
	 * 获得目录轴的显示标签
	 * @return 目录轴的显示标签
	 */
	public String getCategoryAxisLabel() {
		return categoryAxisLabel;
	}

	/**
	 * 设置目录轴的显示标签
	 * @param categoryAxisLabel 目录轴的显示标签
	 */
	public void setCategoryAxisLabel(String categoryAxisLabel) {
		this.categoryAxisLabel = categoryAxisLabel;
	}

	/**
	 * 获得数值轴的显示标签
	 * @return 数值轴的显示标签
	 */
	public String getValueAxisLabel() {
		return valueAxisLabel;
	}

	/**
	 * 设置数值轴的显示标签
	 * @param valueAxisLabel 数值轴的显示标签
	 */
	public void setValueAxisLabel(String valueAxisLabel) {
		this.valueAxisLabel = valueAxisLabel;
	}

	/**
	 * 创建图表
	 */
	protected JFreeChart getChart() {
		return ChartFactory.createBarChart3D(title, categoryAxisLabel, valueAxisLabel, dataset, PlotOrientation.VERTICAL, // 图表方向：水平、垂直
			false, // 是否显示图例(对于简单的柱状图必须是 false)
			false, // 是否生成工具
			false // 是否生成 URL 链接
				);
	}

	/**
	 * 设置字体
	 */
	protected void setFont() {
		// 获取图表区域对象
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();
		// 水平底部列表
		domainAxis.setLabelFont(TITLE_FONT);
		// 水平底部标题
		domainAxis.setTickLabelFont(CONTENT_FONT);
		// 垂直标题
		ValueAxis rangeAxis = plot.getRangeAxis();// 获取柱状
		rangeAxis.setLabelFont(TITLE_FONT);
		// chart.getLegend().setItemFont(WdChartParams.getFontTitle());
	}
}
