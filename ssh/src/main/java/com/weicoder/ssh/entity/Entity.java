package com.weicoder.ssh.entity;

import java.io.Serializable;

/**
 * Entity接口
 * 
 * @author  WD
 * @version
 */
public interface Entity extends Comparable<Entity> {
	/**
	 * 判断是否空对象
	 * 
	 * @return true 空对象 false 非空对象
	 */
	boolean isEmpty();

	/**
	 * 获得本实体的Key 确保本实体下是唯一的
	 * 
	 * @return Key
	 */
	Serializable getKey();

	/**
	 * 设置Key
	 * 
	 * @param key 键
	 */
	void setKey(Serializable key);
}
