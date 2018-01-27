package com.weicoder.dao;

/**
 * 事务接口处理类
 * @author WD
 */
public interface Transactional {
	/**
	 * 开始事务
	 */
	void begin();

	/**
	 * 提交事务
	 */
	void commit();

	/**
	 * 回滚事务
	 */
	void rollback();

	/**
	 * 是否开始事务
	 * @return
	 */
	boolean isBegin();
}
