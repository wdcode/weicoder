package com.weicoder.dao.hibernate.tx;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.weicoder.dao.Transactional;

/**
 * Hibernate 事务类
 * @author WD
 */
public final class HibernateTransactional implements Transactional {
	// 保存的Session
	private Session		session;
	// 保存事务
	private Transaction	tx;
	// 事务状态是否开始
	private boolean		begin;

	/**
	 * 构造方法
	 * @param session hibernate session
	 */
	public HibernateTransactional(Session session) {
		this.session = session;
	}

	@Override
	public void begin() {
		tx = session.beginTransaction();
		begin = true;
	}

	@Override
	public void commit() {
		tx.commit();
		begin = false;
	}

	@Override
	public void rollback() {
		tx.rollback();
		begin = false;
	}

	@Override
	public boolean isBegin() {
		return begin;
	}
}
