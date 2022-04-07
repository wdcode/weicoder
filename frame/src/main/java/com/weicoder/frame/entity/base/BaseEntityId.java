package com.weicoder.frame.entity.base;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import com.weicoder.common.lang.Conversion;

/**
 * 实体PO实现,封装id
 * @author WD
 * 
 * @version 1.0 2010-10-08
 */
@MappedSuperclass
public abstract class BaseEntityId extends BaseEntity {
	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 获得ID
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置ID
	 * @param id ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 获得Key
	 * @return Key
	 */
	public Serializable getKey() {
		return id;
	}

	/**
	 * 设置Key
	 * @param key Key
	 */
	public void setKey(Serializable key) {
		// 如果传进的是数组
		if (key.getClass().isArray()) {
			setKey(((Serializable[]) key)[0]);
		} else {
			this.id = Conversion.toInt(key);
		}
	}
}
