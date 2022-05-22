package com.weicoder.hibernate.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.weicoder.common.U;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.dao.Transactional;
import com.weicoder.dao.base.BaseDao;
import com.weicoder.hibernate.Callback;
import com.weicoder.hibernate.HibernateDao; 
import com.weicoder.hibernate.session.SessionFactorys;
import com.weicoder.hibernate.tx.HibernateTransactional;

/**
 * Hibernate的基础实现
 * 
 * @author wdcode
 *
 */
public abstract class BaseHibernateDao extends BaseDao {
	// 日志
	protected final static Log			LOG			= LogFactory.getLog(HibernateDao.class);
	// Session工厂
	protected SessionFactorys				factorys	= new SessionFactorys();
	// 事务保存列表
	protected Map<Session, Transactional>	txs			= Maps.newMap();

	@Override
	public Transactional getTransaction(Class<?> entityClass) {
		// 获得session
		Session session = getSession(entityClass);
		// 获得事务
		Transactional tx = txs.get(session);
		// 如果现有事务为空 生成新事务
		if (tx == null)
			txs.put(session, tx = new HibernateTransactional(session));
		// 返回事务
		return tx;
	}

	@Override
	public <E> List<E> insert(final List<E> entitys) {
		return U.E.isEmpty(entitys) ? entitys : execute(entitys.get(0).getClass(), session -> {
			// 循环添加
			entitys.forEach(e -> session.save(e));
			// 返回实体
			return entitys;
		});
	}

	@Override
	public <E> List<E> update(final List<E> entitys) {
		return execute(entitys.get(0).getClass(), session -> {
			// 循环更新
			entitys.forEach(e -> session.update(e));
			// 返回实体
			return entitys;
		});
	}

	@Override
	public <E> List<E> insertOrUpdate(final List<E> entitys) {
		return U.E.isEmpty(entitys) ? Lists.emptyList() : execute(entitys.get(0).getClass(), session -> {
			// 循环更新
			entitys.forEach(e -> session.saveOrUpdate(e));
			// 返回实体
			return entitys;
		});
	}

	@Override
	public boolean insertOrUpdates(Object... entitys) {
		return execute(entitys[0].getClass(), session -> {
			// 循环添加
			for (Object o : entitys) {
				session.saveOrUpdate(o);
			}
			// 返回实体
			return entitys;
		}) != null;
	}

	@Override
	public <E> List<E> delete(List<E> entitys) {
		return execute(entitys.get(0).getClass(), session -> {
			// 循环更新
			entitys.forEach(e -> session.delete(e));
			// 返回实体
			return entitys;
		});
	}

	@Override
	public <E> E get(final Class<E> entityClass, final Serializable pk) {
		// 验证pk是否为空
		if (U.E.isEmpty(pk))
			return null;
		// 查找对象
		return (E) execute(entityClass, session -> session.get(entityClass, pk));
	}

	@Override
	public <E> List<E> gets(final Class<E> entityClass, final Serializable... pks) {
		// 验证pk是否为空
		if (U.E.isEmpty(pks))
			return Lists.emptyList();
		// 查找对象
		return execute(entityClass, session -> {
			// 声明返回对象
			List<E> list = Lists.newList(pks.length);
			// 循环获得实体列表
			for (Serializable pk : pks) {
				list.add(session.get(entityClass, pk));
			}
			// 返回对象列表
			return list;
		});
	}
	
	@Override
	public int execute(Class<?> entityClass, final String sql, final Object... values) {
		return execute(entityClass, session -> setParameter(session.createNativeQuery(sql, entityClass), Lists.newList(values), -1, -1).executeUpdate());
	}

	@Override
	public <E> List<E> query(Class<E> entityClass, final String sql, final List<Object> values, final int firstResult, final int maxResults) {
		return execute(entityClass, session -> setParameter(session.createNativeQuery(sql, entityClass), values, firstResult, maxResults).getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object query(Class<?> entityClass, String sql, Object... values) {
		return execute(entityClass, session -> setParameter(session.createNativeQuery(sql), Lists.newList(values), -1, -1).getSingleResult());
	}

	@Override
	public Class<?> entity(String name) {
		return factorys.entity(name);
	}

	@Override
	public boolean inserts(Object... entitys) {
		return execute(entitys[0].getClass(), session -> {
			// 循环添加
			for (Object o : entitys)
				session.save(o);
			// 返回实体
			return entitys;
		}) != null;
	}

	@Override
	public boolean updates(Object... entitys) {
		return execute(entitys[0].getClass(), session -> {
			// 循环添加
			for (Object o : entitys)
				session.update(o);
			// 返回实体
			return entitys;
		}) != null;
	}

	@Override
	public boolean deletes(Object... entitys) {
		return execute(entitys[0].getClass(), session -> {
			// 循环添加
			for (Object o : entitys)
				session.delete(o);
			// 返回实体
			return entitys;
		}) != null;
	}

	/**
	 * 设置Query 参数与结果集数量
	 * 
	 * @param  query       Query查询器
	 * @param  values      参数列表
	 * @param  firstResult 第一条结果
	 * @param  maxResults  最大结果
	 * @param  <R>         泛型
	 * @return             Query
	 */
	protected <R> Query<R> setParameter(Query<R> query, List<Object> values, int firstResult, int maxResults) {
		// 是否有参数
		if (!U.E.isEmpty(values))
			// 循环参数
			for (int i = 0; i < values.size(); i++)
				// 设置参数
				query.setParameter(i, values.get(i));
		// 开始结果大于等于0
		if (firstResult >= 0)
			query.setFirstResult(firstResult);
		// 最大结果大于零
		if (maxResults > 0)
			query.setMaxResults(maxResults);
		// 返回Query
		return query;
	}
	
	/**
	 * 获得当前Session
	 * 
	 * @param  entityClass 实体类
	 * @return             Session
	 */
	protected Session getSession(Class<?> entity) {
		return factorys.getSession(entity);
	}

	/**
	 * 执行Hibernate操作
	 * 
	 * @param  entity   类
	 * @param  callback 回调方法
	 * @param  <T>      泛型
	 * @return          泛型对象
	 */
	protected <T> T execute(Class<?> entity, Callback<T> callback) {
		// 获得Session
		Session session = getSession(entity);
		// 声明事务
		Transaction tx = null;
		// 自定义事务
		Transactional txl = txs.get(session);
		// 是否自己控制事务
		boolean isSession = !session.getTransaction().getStatus().isOneOf(TransactionStatus.ACTIVE);
		// 返回结果
		T t = null;
		try {
			// 是否自己控制事务
			if (isSession && txl == null)
				// 开始事务
				tx = session.beginTransaction();
			// 执行
			t = callback.callback(session);
			// 是否自己控制事务
			if (U.E.isNotEmpty(tx))
				// 提交事务
				tx.commit();
			LOG.debug("hibernate dao callback res={}", t);
		} catch (Exception e) {
			LOG.error(e, "hibernate dao res={}", t);
			// 回滚事务
			if (U.E.isNotEmpty(tx))
				tx.rollback();
			if (U.E.isNotEmpty(txl))
				txl.rollback();
			t = null;
		} finally {
//			// 自己关闭session
//			if (isSession && session.isOpen() && session.isConnected())
//				session.close();
		}
		// 返回对象
		return t;
	} 
}
