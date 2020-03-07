package com.weicoder.hibernate.tx;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.weicoder.dao.Transactional;

/**
 * Hibernate 事务类
 * 
 * @author WD
 */
public final class HibernateTransactional implements Transactional {
	// 保存的Session
	private Session session;
	// 保存事务
	private Transaction tx;
	// 事务状态是否开始
	private boolean begin;

	/**
	 * 构造方法
	 * 
	 * @param session hibernate session
	 */
	public HibernateTransactional(Session session) {
		this.session = session;
	}

	@Override
	public Transactional begin() {
		tx = session.beginTransaction();
		begin = true;
		return this;
	}

	@Override
	public Transactional commit() {
		tx.commit();
		begin = false;
		return this;
	}

	@Override
	public Transactional rollback() {
		tx.rollback();
		begin = false;
		return this;
	}

	@Override
	public boolean isBegin() {
		return begin;
	}
}
