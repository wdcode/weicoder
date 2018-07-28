package com.weicoder.dao.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.weicoder.dao.hibernate.session.SessionFactorys;
import com.weicoder.dao.hibernate.tx.HibernateTransactional;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.dao.Dao;
import com.weicoder.dao.Transactional;

/**
 * Hibernate接口
 * @author WD
 */
public final class HibernateDao implements Dao {
	// 日志
	private final static Log			LOG			= LogFactory.getLog(HibernateDao.class);
	// Session工厂
	private SessionFactorys				factorys	= new SessionFactorys();
	// 事务保存列表
	private Map<Session, Transactional>	txs			= Maps.newMap();

	@Override
	public Transactional getTransaction(Class<?> entityClass) {
		// 获得session
		Session session = getSession(entityClass);
		// 获得事务
		Transactional tx = txs.get(session);
		// 如果现有事务为空 生成新事务
		if (tx == null) {
			txs.put(session, tx = new HibernateTransactional(session));
		}
		// 返回事务
		return tx;
	}

	@Override
	public <E> E insert(final E entity) {
		return Lists.get(insert(Lists.newList(entity)), 0);
	}

	@Override
	public <E> List<E> insert(final List<E> entitys) {
		return EmptyUtil.isEmpty(entitys) ? entitys : execute(entitys.get(0).getClass(), (Session session) -> {
			// 循环添加
			for (E e : entitys) {
				session.save(e);
				// session.flush();
			}
			// 返回实体
			return entitys;
		});
	}

	public static void main(String[] args) {
		int num = 567;
		List<Integer> list = Lists.newList();
		for (int i = 0; i < num; i++) {
			list.add(i);
		}
		Lists.slice(list, 100).forEach(ls -> {
			System.out.println(ls.size());
		});
	}

	@Override
	public <E> List<E> insert(List<E> entitys, int step) {
		// 分步更新
		Lists.slice(entitys, step).forEach(ls -> {
			insert(ls);
		});
		return entitys;
	}

	@Override
	public <E> List<E> update(List<E> entitys, int step) {
		// 分步更新
		Lists.slice(entitys, step).forEach(ls -> {
			update(ls);
		});
		return entitys;
	}

	@Override
	public <E> E update(final E entity) {
		return update(Lists.newList(entity)).get(0);
	}

	@Override
	public <E> List<E> update(final List<E> entitys) {
		return execute(entitys.get(0).getClass(), (Session session) -> {
			// 循环更新
			for (E e : entitys) {
				session.update(e);
			}
			// 返回实体
			return entitys;
		});
	}

	@Override
	public <E> List<E> insertOrUpdate(List<E> entitys, int step) {
		// 分步更新
		Lists.slice(entitys, step).forEach(ls -> {
			insertOrUpdate(ls);
		});
		return entitys;
	}

	@Override
	public <E> E insertOrUpdate(E entity) {
		return insertOrUpdate(Lists.newList(entity)).get(0);
	}

	@Override
	public <E> List<E> insertOrUpdate(final List<E> entitys) {
		return EmptyUtil.isEmpty(entitys) ? Lists.emptyList()
				: execute(entitys.get(0).getClass(), (Session session) -> {
					// 循环更新
					for (E e : entitys) {
						session.saveOrUpdate(e);
					}
					// 返回实体
					return entitys;
				});
	}

	@Override
	public boolean insertOrUpdates(Object... entitys) {
		return execute(entitys[0].getClass(), (Session session) -> {
			// 循环添加
			for (Object o : entitys) {
				session.saveOrUpdate(o);
			}
			// 返回实体
			return entitys;
		}) != null;
	}

	@Override
	public <E> E delete(E entity) {
		return delete(Lists.newList(entity)).get(0);
	}

	@Override
	public <E> List<E> delete(final List<E> entitys) {
		return execute(entitys.get(0).getClass(), (Session session) -> {
			// 循环更新
			entitys.forEach((E e) -> session.delete(e));
			// 返回实体
			return entitys;
		});
	}

	@Override
	public <E> E get(final Class<E> entityClass, final Serializable pk) {
		// 验证pk是否为空
		if (EmptyUtil.isEmpty(pk)) {
			return null;
		}
		// 查找对象
		return execute(entityClass, (Session session) -> {
			return session.get(entityClass, pk);
		});
	}

	@Override
	public <E> List<E> gets(final Class<E> entityClass, final Serializable... pks) {
		// 验证pk是否为空
		if (EmptyUtil.isEmpty(pks)) {
			return Lists.emptyList();
		}
		// 查找对象
		return execute(entityClass, (Session session) -> {
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
	public <E> E get(final E entity) {
		// 获得结果
		List<E> list = list(entity, 0, 1);
		// 返回结果
		return EmptyUtil.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public <E> E get(Class<E> entity, String property, Object value) {
		return getCriteria(entity, DetachedCriteria.forClass(entity).add(Restrictions.eq(property, value)));
	}

	@Override
	public <E> E get(Class<E> entity, Map<String, Object> map) {
		return getCriteria(entity, DetachedCriteria.forClass(entity).add(Restrictions.allEq(map)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> list(final E entity, final int firstResult, final int maxResults) {
		return execute(entity.getClass(), (Session session) -> {
			// 获得Criteria
			Criteria criteria = DetachedCriteria.forClass(entity.getClass()).getExecutableCriteria(session);
			// 添加实体参数
			criteria.add(Example.create(entity));
			// 开始结果大于等于0
			if (firstResult >= 0) {
				criteria.setFirstResult(firstResult);
			}
			// 最大结果大于零
			if (maxResults > 0) {
				criteria.setMaxResults(maxResults);
			}
			// 返回查询结果
			return criteria.list();
		});
	}

	@Override
	public <E> List<E> list(Class<E> entityClass, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass), firstResult, maxResults);
	}

	@Override
	public <E> List<E> eq(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.eq(property, value)),
				firstResult, maxResults);
	}

	@Override
	public <E> List<E> gt(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.gt(property, value)),
				firstResult, maxResults);
	}

	@Override
	public <E> List<E> ge(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.ge(property, value)),
				firstResult, maxResults);
	}

	@Override
	public <E> List<E> lt(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.lt(property, value)),
				firstResult, maxResults);
	}

	@Override
	public <E> List<E> le(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.le(property, value)),
				firstResult, maxResults);
	}

	@Override
	public <E> List<E> eq(Class<E> entityClass, Map<String, Object> map, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.allEq(map)),
				firstResult, maxResults);
	}

	@Override
	public <E> List<E> like(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass,
				DetachedCriteria.forClass(entityClass).add(Restrictions.like(property, value)), firstResult,
				maxResults);
	}

	@Override
	public <E> List<E> order(E entity, Map<String, Object> orders, int firstResult, int maxResults) {
		return queryCriteria(entity.getClass(), getOrder(entity.getClass(), orders).add(Example.create(entity)),
				firstResult, maxResults);
	}

	@Override
	public <E> List<E> order(Class<E> entityClass, Map<String, Object> orders, int firstResult, int maxResults) {
		return queryCriteria(entityClass, getOrder(entityClass, orders), firstResult, maxResults);
	}

	@Override
	public <E> List<E> in(Class<E> entityClass, String property, List<Object> values, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.in(property, values)),
				firstResult, maxResults);
	}

	@Override
	public <E> List<E> in(Class<E> entityClass, String property, List<Object> values, Map<String, Object> orders,
			int firstResult, int maxResults) {
		return queryCriteria(entityClass, getOrder(entityClass, orders).add(Restrictions.in(property, values)),
				firstResult, maxResults);
	}

	@Override
	public <E> List<E> in(Class<E> entityClass, Map<String, List<Object>> parames, int firstResult, int maxResults) {
		// 获得Conjunction AND 条件
		Conjunction conj = Restrictions.conjunction();
		// 循环赋值in
		for (Map.Entry<String, List<Object>> e : parames.entrySet()) {
			conj.add(Restrictions.in(e.getKey(), e.getValue()));
		}
		// 查询结果
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(conj), firstResult, maxResults);
	}

	@Override
	public <E> List<E> between(E entity, String property, Object lo, Object hi, int firstResult, int maxResults) {
		return queryCriteria(entity.getClass(), getBetween(entity, property, lo, hi), firstResult, maxResults);
	}

	@Override
	public <E> List<E> between(Class<E> entity, String property, Object lo, Object hi, int firstResult,
			int maxResults) {
		return queryCriteria(entity, getBetween(entity, property, lo, hi), firstResult, maxResults);
	}

	@Override
	public int count(Class<?> entityClass) {
		return count(entityClass, null, null);
	}

	@Override
	public int count(final Class<?> entityClass, final String property, final Object value) {
		return execute(entityClass, (Session session) -> {
			// 创建查询条件
			Criteria criteria = DetachedCriteria.forClass(entityClass).getExecutableCriteria(session);
			// 设置参数
			if (!EmptyUtil.isEmpty(property) && !EmptyUtil.isEmpty(value)) {
				criteria.add(Restrictions.eq(property, value));
			}
			// 设置获得总行数
			criteria.setProjection(Projections.rowCount());
			// 返回结果
			return Conversion.toInt(criteria.uniqueResult());
		});
	}

	@Override
	public int count(final Class<?> entityClass, final Map<String, Object> map) {
		return execute(entityClass, (Session session) -> {
			// 创建查询条件
			Criteria criteria = DetachedCriteria.forClass(entityClass).getExecutableCriteria(session);
			// 判断属性名不为空
			if (!EmptyUtil.isEmpty(map)) {
				criteria.add(Restrictions.allEq(map));
			}
			// 设置获得总行数
			criteria.setProjection(Projections.rowCount());
			// 返回结果
			return Conversion.toInt(criteria.uniqueResult());
		});
	}

	@Override
	public int count(final Object entity) {
		return execute(entity.getClass(), (Session session) -> {
			// 创建查询条件
			Criteria criteria = DetachedCriteria.forClass(entity.getClass()).getExecutableCriteria(session);
			// 添加实体对象
			criteria.add(Example.create(entity));
			// 设置获得总行数
			criteria.setProjection(Projections.rowCount());
			// 返回结果
			return Conversion.toInt(criteria.uniqueResult());
		});
	}

	@Override
	public int count(Object entity, String property, Object lo, Object hi) {
		return count(entity.getClass(), getBetween(entity, property, lo, hi));
	}

	@Override
	public int execute(Class<?> entityClass, final String sql, final Object... values) {
		return execute(entityClass, (Session session) -> setParameter(session.createNativeQuery(sql, entityClass),
				Lists.newList(values), -1, -1).executeUpdate());
	}

	@Override
	public <E> List<E> query(Class<E> entityClass, final String sql, final List<Object> values, final int firstResult,
			final int maxResults) {
		// 查询结果
		return execute(entityClass, (Session session) -> setParameter(session.createNativeQuery(sql, entityClass),
				values, firstResult, maxResults).getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object query(Class<?> entityClass, String sql, Object... values) {
		return execute(entityClass,
				(Session session) -> setParameter(session.createNativeQuery(sql), Lists.newList(values), -1, -1)
						.getSingleResult());
	}

	@Override
	public boolean inserts(Object... entitys) {
		return execute(entitys[0].getClass(), (Session session) -> {
			// 循环添加
			for (Object o : entitys) {
				session.save(o);
			}
			// 返回实体
			return entitys;
		}) != null;
	}

	@Override
	public boolean updates(Object... entitys) {
		return execute(entitys[0].getClass(), (Session session) -> {
			// 循环添加
			for (Object o : entitys) {
				session.update(o);
			}
			// 返回实体
			return entitys;
		}) != null;
	}

	@Override
	public boolean deletes(Object... entitys) {
		return execute(entitys[0].getClass(), (Session session) -> {
			// 循环添加
			for (Object o : entitys) {
				session.delete(o);
			}
			// 返回实体
			return entitys;
		}) != null;
	}

	/**
	 * 设置Query 参数与结果集数量
	 * @param query Query查询器
	 * @param values 参数列表
	 * @param firstResult 第一条结果
	 * @param maxResults 最大结果
	 * @param <R> 泛型
	 * @return Query
	 */
	private <R> Query<R> setParameter(Query<R> query, List<Object> values, int firstResult, int maxResults) {
		// 是否有参数
		if (!EmptyUtil.isEmpty(values)) {
			// 循环参数
			for (int i = 0; i < values.size(); i++) {
				// 设置参数
				query.setParameter(i, values.get(i));
			}
		}
		// 开始结果大于等于0
		if (firstResult >= 0) {
			query.setFirstResult(firstResult);
		}
		// 最大结果大于零
		if (maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		// 返回Query
		return query;
	}

	/**
	 * 根据DetachedCriteria 查询条件 查询一个结果
	 * @param entityClass 类
	 * @param criteria 查询条件
	 * @param <E> 泛型
	 * @return 返回结果列表
	 */
	private <E> E getCriteria(Class<?> entityClass, final DetachedCriteria criteria) {
		// 获得结果
		List<E> list = queryCriteria(entityClass, criteria, 0, 1);
		// 返回结果
		return EmptyUtil.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 根据DetachedCriteria 查询条件 查询 支持分页
	 * @param entityClass 实体类
	 * @param criteria 查询条件
	 * @param firstResult 开始查询的条数
	 * @param maxResults 最多查询多少条
	 * @param <E> 泛型
	 * @return 返回结果列表
	 */
	@SuppressWarnings("unchecked")
	private <E> List<E> queryCriteria(Class<?> entityClass, final DetachedCriteria criteria, final int firstResult,
			final int maxResults) {
		return execute(entityClass, (Session session) -> {
			// 获得Criteria
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			// 判断开始结果
			if (firstResult >= 0) {
				executableCriteria.setFirstResult(firstResult);
			}
			// 判断最大结果
			if (maxResults > 0) {
				executableCriteria.setMaxResults(maxResults);
			}
			// 返回查询结果
			return executableCriteria.list();
		});
	}

	/**
	 * 根据DetachedCriteria 查询条件 查询总行数
	 * @param entityClass 实体类
	 * @param criteria 查询条件
	 * @return 返回结果列表 异常返回0
	 */
	private int count(Class<?> entityClass, final DetachedCriteria criteria) {
		return execute(entityClass, (Session session) -> Conversion
				.toInt(criteria.getExecutableCriteria(session).setProjection(Projections.rowCount()).uniqueResult()));
	}

	/**
	 * queryByBetween使用 返回DetachedCriteria
	 * @param entity 查询实体
	 * @param property 字段名
	 * @param lo 开始条件
	 * @param hi 结束条件
	 * @return DetachedCriteria
	 */
	private DetachedCriteria getBetween(Object entity, String property, Object lo, Object hi) {
		// 获得criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(entity.getClass());
		// 添加实体条件
		criteria.add(Example.create(entity));
		// 添加条件
		criteria.add(Restrictions.between(property, lo, hi));
		// 返回DetachedCriteria
		return criteria;
	}

	/**
	 * queryByBetween使用 返回DetachedCriteria
	 * @param entity 查询实体
	 * @param property 字段名
	 * @param lo 开始条件
	 * @param hi 结束条件
	 * @param <E> 泛型
	 * @return DetachedCriteria
	 */
	private <E> DetachedCriteria getBetween(Class<E> entity, String property, Object lo, Object hi) {
		// 获得criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(entity);
		// 添加条件
		criteria.add(Restrictions.between(property, lo, hi));
		// 返回DetachedCriteria
		return criteria;
	}

	/**
	 * 获得排序DetachedCriteria
	 * @param entityClass 实体类
	 * @param orders 排序参数
	 * @return DetachedCriteria
	 */
	private DetachedCriteria getOrder(Class<?> entityClass, Map<String, Object> orders) {
		// 获得DetachedCriteria
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		// 循环排序
		for (Map.Entry<String, Object> e : orders.entrySet()) {
			criteria.addOrder(Conversion.toBoolean(e.getValue()) ? Order.asc(e.getKey()) : Order.desc(e.getKey()));
		}
		// 返回DetachedCriteria
		return criteria;
	}

	/**
	 * 获得当前Session
	 * @param entityClass 实体类
	 * @return Session
	 */
	private Session getSession(Class<?> entity) {
		return factorys.getSession(entity);
	}

	/**
	 * 执行Hibernate操作
	 * @param entity 类
	 * @param callback 回调方法
	 * @param <T> 泛型
	 * @return 泛型对象
	 */
	private <T> T execute(Class<?> entity, Callback<T> callback) {
		// 获得Session
		Session session = getSession(entity);
		// 声明事务
		Transaction tx = null;
		// 自定义事务
		Transactional txl = txs.get(session);
		// 是否自己控制事务
		boolean isSession = !session.getTransaction().getStatus().isOneOf(TransactionStatus.ACTIVE);
		// boolean isSession = !session.getTransaction().isActive();
		// 返回结果
		T t = null;
		try {
			// 是否自己控制事务
			if (isSession && txl == null) {
				// 开始事务
				tx = session.beginTransaction();
			}
			// 执行
			t = callback.callback(session);
			// 是否自己控制事务
			if (!EmptyUtil.isEmpty(tx)) {
				// 提交事务
				tx.commit();
			}
			// toString() 为了使关联生效
			if (!EmptyUtil.isEmpty(t)) {
				t.toString();
			}
			LOG.debug("hibernate dao callback class={} res={}", entity, t);
		} catch (Exception e) {
			LOG.error(e, "hibernate dao class={} res={}", entity, t);
			// 回滚事务
			if (!EmptyUtil.isEmpty(tx)) {
				tx.rollback();
			}
			if (!EmptyUtil.isEmpty(txl)) {
				txl.rollback();
			}
			t = null;
		} finally {
			// 自己关闭session
			if (isSession && session.isOpen() && session.isConnected()) {
				session.close();
			}
		}
		// 返回对象
		return t;
	}

	/**
	 * Hibernate回调方法
	 * @author WD
	 */
	interface Callback<T> {
		/**
		 * 调用Hibernate执行操作
		 * @param session hibernate Session
		 * @return 指定的泛型
		 */
		T callback(Session session);
	}
}
