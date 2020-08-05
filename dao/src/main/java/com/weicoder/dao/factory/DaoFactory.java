package com.weicoder.dao.factory;
 
import com.weicoder.common.factory.FactoryInterface;
import com.weicoder.dao.Dao; 

/**
 * Dao工厂
 * 
 * @author WD
 */
public final class DaoFactory extends FactoryInterface<Dao> {
	/** Dao工厂 */
	private final static DaoFactory FACTORY = new DaoFactory();

	/**
	 * 获得dao
	 * 
	 * @return
	 */
	public static Dao getDao() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得dao
	 * 
	 * @param  name
	 * @return
	 */
	public static Dao getDao(String name) {
		return FACTORY.getInstance(name);
	}

//	@Override
//	public Dao newInstance() {
//		return (Dao) (DaoParams.JDBC ? U.C.newInstance("com.weicoder.jdbc.JdbcDao")
//				: U.C.newInstance("com.weicoder.hibernate.HibernateDao"));
//	}

	private DaoFactory() {
	}
}
