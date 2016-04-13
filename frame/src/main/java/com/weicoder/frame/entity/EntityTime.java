package com.weicoder.frame.entity;

/**
 * 有时间的实体接口
 * @author WD 
 * @version 1.0 
 */
public interface EntityTime {
	/**
	 * 获得创建时间
	 */
	Integer getTime();

	/**
	 * 设置创建时间
	 */
	void setTime(Integer time);

	/**
	 * 获得日期
	 */
	String getDate();
}
