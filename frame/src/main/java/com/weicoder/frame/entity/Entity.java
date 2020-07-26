package com.weicoder.frame.entity;

import java.io.Serializable;

/**
 * Entity接口
 * @author WD
 * 
 * @version 1.0 2009-11-23
 */
public interface Entity extends Comparable<Entity> {
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
