package com.weicoder.frame.entity.base;

import java.io.Serializable;

import jakarta.persistence.MappedSuperclass;

import com.weicoder.frame.entity.Entity;
import com.weicoder.common.lang.W;
import com.weicoder.common.util.U;
import com.weicoder.json.J;

/**
 * Entity接口基础实现
 * @author WD
 * 
 * @version 1.0 2010-12-28
 */
@MappedSuperclass
public abstract class BaseEntity implements Entity {
	/**
	 * 判断是否为空
	 * @return 是否为空
	 */
	public boolean isEmpty() {
		return U.E.isEmpty(getKey());
	}

	/**
	 * 重写toString 使用json输出属性
	 * @return 字符串
	 */
	public String toString() {
		return J.toJson(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (getKey() == null) {
			if (other.getKey() != null)
				return false;
		} else if (!getKey().equals(other.getKey()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Entity o) {
		// 获得主键
		Serializable key = o.getKey();
		// 判断类型
		if (key instanceof Integer) {
			return Integer.compare(W.C.toInt(getKey()), W.C.toInt(key));
		} else {
			return W.C.toString(getKey()).compareTo(W.C.toString(key));
		}
	}
}
