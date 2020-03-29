package com.weicoder.dao;

/**
 * 事务接口处理类
 * 
 * @author WD
 */
public interface Transactional {
	/**
	 * 开始事务
	 */
	Transactional begin();

	/**
	 * 提交事务
	 */
	Transactional commit();

	/**
	 * 回滚事务
	 */
	Transactional rollback();

	/**
	 * 是否开始事务
	 * 
	 * @return 是否成功
	 */
	boolean isBegin();
}
