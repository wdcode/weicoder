package com.weicoder.base.entity;

import java.io.Serializable;

import com.weicoder.common.interfaces.Empty;

/**
 * Entity接口
 * @author WD
 * @since JDK7
 * @version 1.0 2009-11-23
 */
public interface Entity extends Serializable, Empty, Comparable<Entity> {
	/**
	 * 获得本实体的Key 确保本实体下是唯一的
	 * @return Key
	 */
	Serializable getKey();

	/**
	 * 设置Key
	 * @param key 键
	 */
	void setKey(Serializable key);
}
