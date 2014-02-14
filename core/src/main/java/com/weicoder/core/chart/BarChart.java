package com.weicoder.core.chart;

/**
 * 柱图
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-08
 */
public interface BarChart extends Chart {
	/**
	 * 添加值
	 * @param value 值
	 * @param rowKey 行键
	 * @param columnKey 列键
	 */
	void addValue(Number value, Comparable<?> rowKey, Comparable<?> columnKey);

	/**
	 * 获得目录轴的显示标签
	 * @return 目录轴的显示标签
	 */
	String getCategoryAxisLabel();

	/**
	 * 设置目录轴的显示标签
	 * @param categoryAxisLabel 目录轴的显示标签
	 */
	void setCategoryAxisLabel(String categoryAxisLabel);

	/**
	 * 获得数值轴的显示标签
	 * @return 数值轴的显示标签
	 */
	String getValueAxisLabel();

	/**
	 * 设置数值轴的显示标签
	 * @param valueAxisLabel 数值轴的显示标签
	 */
	void setValueAxisLabel(String valueAxisLabel);
}
