package com.weicoder.site.entity.base;

import javax.persistence.MappedSuperclass;

import com.weicoder.base.entity.EntityTime;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;

/**
 * 标准实体实现
 * @author WD
 * @since JDK7
 * @version 1.0 2012-03-29
 */
@MappedSuperclass
public abstract class BaseEntityIdTime extends BaseEntityId implements EntityTime {
	// 序列化ID
	private static final long	serialVersionUID	= 457244042060870415L;
	// 时间
	private Integer				time;

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
