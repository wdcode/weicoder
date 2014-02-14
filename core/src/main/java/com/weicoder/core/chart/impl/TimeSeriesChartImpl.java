package com.weicoder.core.chart.impl;

import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot; 
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import com.weicoder.core.chart.TimeSeriesChart;
import com.weicoder.core.chart.base.BaseChart; 

/**
 * 时序图
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-08
 */
public final class TimeSeriesChartImpl extends BaseChart implements TimeSeriesChart {
	// X轴
	private String					xAxisLabel;
	// X轴
	private String					yAxisLabel;
	// 数据源
	private TimeSeriesCollection	dataset;

	/**
	 * 构造方法
	 */
	public TimeSeriesChartImpl() {
		dataset = new TimeSeriesCollection();
	}

	/**
	 * 添加线
	 * @param series 线名
	 * @param data 数据
	 */
	public void addSeries(String series, Map<String, Double> data) {
		// 创建时间线
		TimeSeries td = new TimeSeries(series);// 创建时间数据源，每一个//TimeSeries在图上是一条曲线
		// 循环设置数据
		for (Map.Entry<String, Double> e : data.entrySet()) {
			// 设置数据点
			td.add(Second.parseSecond(e.getKey()), e.getValue());
		}
		// 添加到数据源
		dataset.addSeries(td);
	}

	/**
	 * 获得X轴
	 * @return 设置X轴
	 */
	public String getxAxisLabel() {
		return xAxisLabel;
	}

	/**
	 * 设置X轴
	 * @param xAxisLabel X轴
	 */
	public void setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
	}

	/**
	 * 获得Y轴
	 * @return Y轴
	 */
	public String getyAxisLabel() {
		return yAxisLabel;
	}

	/**
	 * 设置Y轴
	 * @param yAxisLabel Y轴
	 */
	public void setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}

	/**
	 * 创建图表
	 */
	protected JFreeChart getChart() {
		return ChartFactory.createTimeSeriesChart(title, // title
			xAxisLabel, // x-axis label
			yAxisLabel, // y-axis label
			dataset, // data
			true, // create legend?
			true, // generate tooltips?
			false // generate URLs?
				);
	}

	/**
	 * 设置字体
	 */
	protected void setFont() {
		// 获得XYPlot
		XYPlot plot = (XYPlot) chart.getPlot();
		// 纵轴字体
		plot.getRangeAxis().setLabelFont(TITLE_FONT);
		plot.getRangeAxis().setTickLabelFont(TITLE_FONT);
		// 横轴框里的标题字体
		chart.getLegend().setItemFont(TITLE_FONT);
		// 横轴列表字体
		plot.getDomainAxis().setTickLabelFont(TITLE_FONT);
		// 横轴小标题字体
		plot.getDomainAxis().setLabelFont(TITLE_FONT);
	}
}
