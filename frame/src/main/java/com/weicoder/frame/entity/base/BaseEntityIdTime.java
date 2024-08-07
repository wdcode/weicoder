package com.weicoder.frame.entity.base;

import jakarta.persistence.MappedSuperclass;

import com.weicoder.frame.entity.EntityTime;
import com.weicoder.common.constants.C; 
import com.weicoder.common.util.U;

/**
 * 标准实体实现
 * @author WD
 * 
 * @version 1.0 2012-03-29
 */
@MappedSuperclass
public abstract class BaseEntityIdTime extends BaseEntityId implements EntityTime {
	// 时间
	private Integer time;

	@Override
	public Integer getTime() {
		return time;
	}

	@Override
	public void setTime(Integer time) {
		this.time = time;
	}

	@Override
	public String getDate() {
		return U.E.isEmpty(time) ? C.S.EMPTY : U.D.toString(time);
	}
}
