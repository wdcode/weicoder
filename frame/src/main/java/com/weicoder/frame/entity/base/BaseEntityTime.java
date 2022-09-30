package com.weicoder.frame.entity.base;

import jakarta.persistence.MappedSuperclass;

import com.weicoder.frame.entity.Entity;
import com.weicoder.frame.entity.EntityTime;
import com.weicoder.common.constants.C;
import com.weicoder.common.lang.W; 
import com.weicoder.common.util.U;

/**
 * 标准实体实现
 * @author WD
 * 
 * @version 1.0 2012-03-29
 */
@MappedSuperclass
public abstract class BaseEntityTime extends BaseEntity implements EntityTime {
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

	@Override
	public int compareTo(Entity o) {
		return o instanceof EntityTime && time != null
				? Integer.compare(W.C.toInt(time),
						W.C.toInt(((EntityTime) o).getTime()))
				: super.compareTo(o);
	}
}
