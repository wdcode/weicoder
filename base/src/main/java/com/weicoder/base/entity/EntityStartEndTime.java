package com.weicoder.base.entity;

/**
 * 开始时间 结束时间设置接口
 * @author WD
 * @since JDK7
 * @version 1.0 2012-07-18
 */
public interface EntityStartEndTime {
	/**
	 * 获得开始时间
	 */
	Integer getStartTime();

	/**
	 * 设置结束时间
	 */
	void setStartTime(Integer time);

	/**
	 * 获得结束时间
	 */
	Integer getEndTime();

	/**
	 * 设置结束时间
	 */
	void setEndTime(Integer time);
}
