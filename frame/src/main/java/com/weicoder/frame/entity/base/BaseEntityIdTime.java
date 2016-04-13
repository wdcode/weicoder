package com.weicoder.frame.entity.base;

import javax.persistence.MappedSuperclass;

import com.weicoder.frame.entity.EntityTime;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;

/**
 * 标准实体实现
 * @author WD 
 * @version 1.0 
 */
@MappedSuperclass
public abstract class BaseEntityIdTime extends BaseEntityId implements EntityTime {
	// 时间
	private Integer time;

	/**
	 * 获得时间
	 */
	public Integer getTime() {
		return time;
	}

	/**
	 * 时间
	 */
	public void setTime(Integer time) {
		this.time = time;
	}

	/**
	 * 获得日期
	 */
	public String getDate() {
		return EmptyUtil.isEmpty(time) ? StringConstants.EMPTY : DateUtil.toString(time);
	}
}
