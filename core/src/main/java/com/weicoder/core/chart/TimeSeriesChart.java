package com.weicoder.core.chart;
 
import java.util.Map;

/**
 * 时序图
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-08
 */
public interface TimeSeriesChart extends Chart {
	/**
	 * 添加线
	 * @param series 线名
	 * @param data 数据
	 */
	void addSeries(String series, Map<String, Double> data);

	/**
	 * 获得X轴
	 * @return 设置X轴
	 */
	String getxAxisLabel();

	/**
	 * 设置X轴
	 * @param xAxisLabel X轴
	 */
	void setxAxisLabel(String xAxisLabel);

	/**
	 * 获得Y轴
	 * @return Y轴
	 */
	String getyAxisLabel();

	/**
	 * 设置Y轴
	 * @param yAxisLabel Y轴
	 */
	void setyAxisLabel(String yAxisLabel);
}
