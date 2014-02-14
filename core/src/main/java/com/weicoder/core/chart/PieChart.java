package com.weicoder.core.chart;

/**
 * 饼图
 * @author WD
 * @since JDK7
 * @version 1.0 2011-07-08
 */
public interface PieChart extends Chart {
	/**
	 * 设置值
	 * @param key 键
	 * @param value 值
	 */
	void setValue(Comparable<?> key, Number value);
}
