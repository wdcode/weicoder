package com.weicoder.dao.factory;

import com.weicoder.common.factory.Factory;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.dao.Dao;
import com.weicoder.dao.jdbc.JdbcDao;
import com.weicoder.dao.params.DaoParams;

/**
 * Dao工厂
 * 
 * @author WD
 */
public final class DaoFactory extends Factory<Dao> {
	/** Dao工厂 */
	public final static DaoFactory FACTORY = new DaoFactory();

	@Override
	public Dao newInstance() {
		return DaoParams.JDBC ? new JdbcDao() : (Dao) ClassUtil.newInstance("com.weicoder.hibernate.HibernateDao");
	}

	private DaoFactory() {
	}
}
